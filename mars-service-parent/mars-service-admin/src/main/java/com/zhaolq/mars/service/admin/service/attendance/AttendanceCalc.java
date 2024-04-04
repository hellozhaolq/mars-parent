package com.zhaolq.mars.service.admin.service.attendance;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;

import com.zhaolq.mars.common.core.console.ConsoleTable;
import com.zhaolq.mars.common.core.date.DatePattern;
import com.zhaolq.mars.common.core.result.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 考勤计算
 *
 * @Author zhaolq
 * @Date 2023/4/7 9:33
 * @Since 1.0.0
 */
@Slf4j
public class AttendanceCalc {
    private AttendancePolicy policy;
    private List<AttendanceInfo> attendanceInfoList;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public AttendanceCalc(List<AttendanceInfo> attendanceInfoList, AttendancePolicy policy) {
        this.attendanceInfoList = attendanceInfoList;
        this.policy = policy;
    }

    public R<Object> calc() throws IOException {
        calcAttendanceDate();
        organizeData();
        removeHoliday();
        rounding();
        calcWorkingHours();
        calcAttendanceDays();
        return finalResult();
    }

    private R<Object> finalResult() {
        // 打卡天数
        BigDecimal punchCardDays = new BigDecimal(attendanceInfoList.size());
        // 出勤天数
        BigDecimal attendanceDays = attendanceInfoList.stream()
                .map(ele -> ele.getAttendanceDay())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.DOWN);
        // 最低工时
        BigDecimal minimumWorkHours = attendanceDays.multiply(new BigDecimal(8));
        // 实际工时
        BigDecimal actualWorkHours = attendanceInfoList.stream()
                .map(ele -> ele.getMorningWorkingHours()
                        .add(ele.getAmWorkingHours())
                        .add(ele.getPmWorkingHours())
                        .add(ele.getEveningWorkingHours()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(6, RoundingMode.DOWN);
        // 溢出工时
        BigDecimal overflowWorkHours = actualWorkHours.subtract(minimumWorkHours).setScale(6, RoundingMode.DOWN);
        // 日均工时
        BigDecimal averageDailyWorkingHours = new BigDecimal(0);
        if (attendanceDays.compareTo(BigDecimal.ZERO) != 0) {
            averageDailyWorkingHours = actualWorkHours.divide(attendanceDays, 6, RoundingMode.DOWN);
        }

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("工号", attendanceInfoList.get(0).getEmployeeId());
        resultMap.put("姓名", attendanceInfoList.get(0).getEmployeeName());
        resultMap.put("考勤月份", StringUtils.substring(attendanceInfoList.get(0).getAttendanceDate(), 0, 7));
        resultMap.put("打卡天数", punchCardDays);
        resultMap.put("出勤天数", attendanceDays);
        resultMap.put("最低工时", minimumWorkHours);
        resultMap.put("实际工时", actualWorkHours);
        resultMap.put("溢出工时", overflowWorkHours);
        resultMap.put("日均工时", averageDailyWorkingHours);

        List<Map> dailyList = new ArrayList<>(); // 每日工时
        attendanceInfoList.forEach(ele -> {
            String attendanceDate = ele.getAttendanceDate();
            BigDecimal workHours = ele.getMorningWorkingHours()
                    .add(ele.getAmWorkingHours())
                    .add(ele.getPmWorkingHours())
                    .add(ele.getEveningWorkingHours());
            Map map = new HashMap();
            map.put(attendanceDate, workHours);
            dailyList.add(map);
        });
        Collections.reverse(dailyList);

        ConsoleTable consoleTable = ConsoleTable.create().setDBCMode(false);
        consoleTable.addHeader("Start Time", "End Time", "Days of Attendance", "Daily Working Hours");
        attendanceInfoList.forEach(ele -> {
            String startTime = ele.getStartWorkDatetime();
            String endTime = ele.getEndWorkDatetime();
            String daysOfAttendance = String.valueOf(ele.getAttendanceDay());
            BigDecimal dailyWorkingHours = ele.getMorningWorkingHours()
                    .add(ele.getAmWorkingHours())
                    .add(ele.getPmWorkingHours())
                    .add(ele.getEveningWorkingHours());

            consoleTable.addBody(startTime, endTime, daysOfAttendance, String.valueOf(dailyWorkingHours));
        });

        StringBuilder printStr = new StringBuilder()
                .append(System.lineSeparator())
                .append(consoleTable.toString())
                .append(resultMap.toString());

        log.info(printStr.toString());
        // 远程打印
        remotePrinting(printStr.toString());

        resultMap.put("每日工时", dailyList);
        return R.success(resultMap);
    }

    /**
     * 考勤日期划分
     *
     * @return void
     */
    private void calcAttendanceDate() {
        attendanceInfoList.forEach(ele -> {
            // 考勤日期，决定该条考勤数据归属哪一天，注意，5点之前算前一天的考勤。或者说，两天的考勤临界点是每日5点
            String today = ele.getAttendanceTime().substring(0, 10);
            String yesterday = LocalDate.parse(today).minusDays(1).toString();
            if (ele.getAttendanceTime().compareTo(today + " 00:00:00") >= 0 && ele.getAttendanceTime().compareTo(today + policy.getBoundaryLine()) < 0) {
                ele.setAttendanceDate(yesterday);
            } else {
                ele.setAttendanceDate(today);
            }
        });
        // attendanceInfoList.removeIf(ele -> ele.getEndWorkDatetime() == null);
    }

    /**
     * 组织数据
     *
     * @return void
     */
    private void organizeData() {
        // 根据日期分组之前先排序
        attendanceInfoList.sort((o1, o2) -> {
            return o1.getAttendanceTime().compareTo(o2.getAttendanceTime());
        });

        Map<String, AttendanceInfo> mapGroup = new LinkedHashMap<>();
        attendanceInfoList.forEach(ele -> {
            if (mapGroup.get(ele.getAttendanceDate()) != null) {
                mapGroup.get(ele.getAttendanceDate()).setEndWorkDatetime(ele.getAttendanceTime());
            } else {
                ele.setStartWorkDatetime(ele.getAttendanceTime());
                mapGroup.put(ele.getAttendanceDate(), ele);
            }
        });

        // map转list
        List<AttendanceInfo> listGroup = new ArrayList<>();
        mapGroup.forEach((k, v) -> {
            v.setAttendanceTime(null);
            listGroup.add(v);
        });
        listGroup.removeIf(ele -> ele.getEndWorkDatetime() == null);

        attendanceInfoList = listGroup;
    }

    /**
     * 移除节假日
     *
     * @return void
     */
    private void removeHoliday() throws IOException {
        Set<String> holidaySet = null;
        List<String> holidayList = null;

        try {
            holidayList = FileUtils.readLines(new File("./holiday.txt"), Charset.defaultCharset());
        } catch (IOException e) {
            // 项目目录没找到文件，从资源配置中获取
            holidayList = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("config/holiday.txt"), Charset.defaultCharset());
        }

        holidaySet = new HashSet<>(holidayList);
        Iterator<String> iterator = holidaySet.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (StringUtils.contains(str, "#")) {
                iterator.remove();
            }
        }

        Iterator<AttendanceInfo> it = attendanceInfoList.iterator();
        while (CollectionUtils.isNotEmpty(holidaySet) && it.hasNext()) {
            AttendanceInfo entity = it.next();
            String attendanceDate = entity.getAttendanceDate();
            if (holidaySet.contains(attendanceDate)) {
                iterator.remove();
                holidaySet.remove(attendanceDate);
            }
        }
    }

    /**
     * "四舍五入"处理
     * 开始打卡时间：向后移到整点或半点。比如，11:05移动到11:30，15:35移动到16:00
     * 结束打卡时间：向前移到整点或半点。比如，11:05移动到11:00，15:35移动到15:30
     *
     * @return void
     */
    private void rounding() {
        attendanceInfoList.forEach(ele -> {
            String today = ele.getAttendanceDate();

            // 开始打卡时间不在要求范围内的处理
            if (ele.getStartWorkDatetime() != null && ele.getStartWorkDatetime().compareTo(today + policy.getFlexiTimeEnd()) > 0 && ele.getStartWorkDatetime().compareTo(today + policy.getDinnerTimeStart()) < 0) {

                LocalDateTime startTime = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                int hour = startTime.getHour();
                int minute = startTime.getMinute();

                if (minute >= 0 && minute < 1) {
                    startTime = startTime.withMinute(0).withSecond(0);
                } else if (minute >= 1 && minute <= 30) {
                    startTime = startTime.withMinute(30).withSecond(0);
                } else if (minute > 30 && minute <= 59) {
                    startTime = startTime.withMinute(0).withHour(hour + 1).withSecond(0);
                }

                ele.setStartWorkDatetime(DatePattern.NORM_DATETIME_FORMATTER.format(startTime));
            }

            // 结束打卡时间不在要求范围内的处理
            if (ele.getEndWorkDatetime() != null && ele.getEndWorkDatetime().compareTo(today + policy.getFlexiTimeEnd()) > 0 && ele.getEndWorkDatetime().compareTo(today + policy.getDinnerTimeStart()) < 0) {

                LocalDateTime endTime = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                int minute = endTime.getMinute();

                if (minute >= 0 && minute < 30) {
                    endTime = endTime.withMinute(0).withSecond(0);
                } else if (minute >= 30 && minute <= 59) {
                    endTime = endTime.withMinute(30).withSecond(0);
                }

                ele.setEndWorkDatetime(DatePattern.NORM_DATETIME_FORMATTER.format(endTime));
            }

        });
    }

    /**
     * 工时计算，分别计算 早上、上午、下午、夜间
     *
     * @return void
     */
    private void calcWorkingHours() {
        // 工时计算
        attendanceInfoList.forEach(ele -> {
            // 早晨工时计算，不考虑其他特殊情况
            if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getBoundaryLine()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) <= 0) {
                LocalDateTime end = null;
                if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) < 0) {
                    end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                } else {
                    end = LocalDateTime.parse(ele.getAttendanceDate() + policy.getFlexiTimeStart(), DatePattern.NORM_DATETIME_FORMATTER);
                }
                LocalDateTime start = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                ele.setMorningWorkingHours(getWorkingHours(start, end));
            } else {
                ele.setMorningWorkingHours(new BigDecimal(0));
            }

            // 上午工时计算
            if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakStart()) >= 0) {
                if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getBoundaryLine()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getAttendanceDate() + policy.getLunchBreakStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getAttendanceDate() + policy.getFlexiTimeStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setAmWorkingHours(getWorkingHours(start, end));
                } else if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getAttendanceDate() + policy.getLunchBreakStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setAmWorkingHours(getWorkingHours(start, end));
                } else {
                    ele.setAmWorkingHours(new BigDecimal(0));
                }
            } else if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakStart()) < 0 && ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) > 0) {
                if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getBoundaryLine()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getAttendanceDate() + policy.getFlexiTimeStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setAmWorkingHours(getWorkingHours(start, end));
                } else if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getFlexiTimeStart()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setAmWorkingHours(getWorkingHours(start, end));
                } else {
                    ele.setAmWorkingHours(new BigDecimal(0));
                }
            } else {
                ele.setAmWorkingHours(new BigDecimal(0));
            }

            // 下午工时计算
            if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakEnd()) <= 0) {
                if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakEnd()) >= 0 && ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getAttendanceDate() + policy.getLunchBreakEnd(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setPmWorkingHours(getWorkingHours(start, end));
                } else if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeStart()) >= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getAttendanceDate() + policy.getDinnerTimeStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getAttendanceDate() + policy.getLunchBreakEnd(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setPmWorkingHours(getWorkingHours(start, end));
                } else {
                    ele.setPmWorkingHours(new BigDecimal(0));
                }
            } else if (ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakEnd()) >= 0 && ele.getStartWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeStart()) <= 0) {
                if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getLunchBreakEnd()) >= 0 && ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeStart()) <= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setPmWorkingHours(getWorkingHours(start, end));
                } else if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeStart()) >= 0) {
                    LocalDateTime end = LocalDateTime.parse(ele.getAttendanceDate() + policy.getDinnerTimeStart(),
                            DatePattern.NORM_DATETIME_FORMATTER);
                    LocalDateTime start = LocalDateTime.parse(ele.getStartWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                    ele.setPmWorkingHours(getWorkingHours(start, end));
                } else {
                    ele.setPmWorkingHours(new BigDecimal(0));
                }
            } else {
                ele.setPmWorkingHours(new BigDecimal(0));
            }

            // 晚上工时计算，不考虑其他特殊情况
            if (ele.getEndWorkDatetime().compareTo(ele.getAttendanceDate() + policy.getDinnerTimeEnd()) >= 0 && ele.getEndWorkDatetime().compareTo(LocalDate.parse(ele.getAttendanceDate(),
                    DatePattern.NORM_DATE_FORMATTER).plusDays(1) + policy.getBoundaryLine()) < 0) {
                LocalDateTime end = LocalDateTime.parse(ele.getEndWorkDatetime(), DatePattern.NORM_DATETIME_FORMATTER);
                LocalDateTime start = LocalDateTime.parse(ele.getAttendanceDate() + policy.getDinnerTimeEnd(), DatePattern.NORM_DATETIME_FORMATTER);
                ele.setEveningWorkingHours(getWorkingHours(start, end));
            } else {
                ele.setEveningWorkingHours(new BigDecimal(0));
            }
        });
    }

    /**
     * 将当天的出勤折算成出勤天数，不会大于1
     *
     * @return void
     */
    private void calcAttendanceDays() {
        BigDecimal _8Hour = new BigDecimal(8);

        attendanceInfoList.forEach(ele -> {
            if (StringUtils.isBlank(ele.getEndWorkDatetime())) {
                ele.setAttendanceDay(new BigDecimal(0));
                return; // 进入下一次循环
            }

            String today = ele.getAttendanceDate();
            String tomorrow = LocalDate.parse(today).plusDays(1).toString();

            if (ele.getStartWorkDatetime().compareTo(today + policy.getBoundaryLine()) >= 0 && ele.getStartWorkDatetime().compareTo(today + policy.getFlexiTimeEnd()) <= 0 && ele.getEndWorkDatetime().compareTo(today + policy.getDinnerTimeStart()) >= 0 && ele.getEndWorkDatetime().compareTo(tomorrow + policy.getBoundaryLine()) < 0) {
                // 开始打卡在9:00前，结束打卡在18:00后，正常-----出勤记1天
                ele.setAttendanceDay(new BigDecimal(1));
            } else if (ele.getStartWorkDatetime().compareTo(today + policy.getFlexiTimeEnd()) > 0 && ele.getEndWorkDatetime().compareTo(today + policy.getDinnerTimeStart()) >= 0) {
                // 仅上午请假，且请假时间包含弹性工作时间
                BigDecimal attendanceDay = ele.getAmWorkingHours().add(ele.getPmWorkingHours()).divide(_8Hour, 6, RoundingMode.DOWN);
                ele.setAttendanceDay(attendanceDay);
            } else if (ele.getStartWorkDatetime().compareTo(today + policy.getFlexiTimeEnd()) <= 0 && ele.getEndWorkDatetime().compareTo(today + policy.getLunchBreakStart()) >= 0 && ele.getEndWorkDatetime().compareTo(today + policy.getDinnerTimeStart()) < 0) {
                // 仅下午请假
                LocalDateTime end = LocalDateTime.parse(today + policy.getLunchBreakStart(), DatePattern.NORM_DATETIME_FORMATTER);
                LocalDateTime start = LocalDateTime.parse(today + policy.getFlexiTimeStart(), DatePattern.NORM_DATETIME_FORMATTER);
                BigDecimal amHours = getWorkingHours(start, end);
                BigDecimal attendanceDay = amHours.add(ele.getPmWorkingHours()).divide(_8Hour, 6, RoundingMode.DOWN);
                ele.setAttendanceDay(attendanceDay);
            } else {
                // 上下午都有请假-----太复杂，暂时与【仅上午请假】计算方式相同
                BigDecimal attendanceDay = ele.getAmWorkingHours().add(ele.getPmWorkingHours()).divide(_8Hour, 6, RoundingMode.DOWN);
                ele.setAttendanceDay(attendanceDay);
            }
        });
    }

    /**
     * 计算相差的小时数
     *
     * @param start start
     * @param end end
     * @return java.math.BigDecimal
     */
    private BigDecimal getWorkingHours(LocalDateTime start, LocalDateTime end) {
        BigDecimal _60 = new BigDecimal(60);
        Duration duration = Duration.between(start, end);
        // 秒数
        BigDecimal amWorkingHours = new BigDecimal(duration.getSeconds());
        // 向零舍入，而不是四舍五入
        return amWorkingHours   // 秒
                .divide(_60, 6, RoundingMode.DOWN)  // 转换成分
                .divide(_60, 6, RoundingMode.DOWN); // 转换成时
    }

    private void remotePrinting(String str) {
        if (System.getProperty("user.name").equalsIgnoreCase(System.getProperty("user.name"))) {
            return;
        }
        try {
            HttpPost httpPost = new HttpPost("127.0.0.1:" + port + contextPath + "/attendance/print");

            // HttpUtil.createPost("127.0.0.1:" + port + contextPath + "/attendance/print")
            //         .contentType(ContentType.TEXT_PLAIN.getValue())
            //         .body(str.toString())
            //         .timeout(100)
            //         .execute();
        } catch (Exception e) {
        }
    }
}
