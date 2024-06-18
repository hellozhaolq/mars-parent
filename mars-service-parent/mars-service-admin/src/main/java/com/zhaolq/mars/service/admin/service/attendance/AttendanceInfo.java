package com.zhaolq.mars.service.admin.service.attendance;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 考勤信息
 *
 * @Author zhaolq
 * @Date 2023/4/7 9:39
 * @Since 1.0.0
 */
@Data
public class AttendanceInfo {
    // 员工id
    private String employeeId;
    // 员工姓名
    private String employeeName;
    // 考勤时间：2023-01-01 08:00:00
    private String attendanceTime;

    //考勤日期。决定该条考勤数据归属哪一天，注意，5点之前算前一天的考勤。或者说，两天的考勤临界点是每日5点
    private String attendanceDate;
    // 开始考勤时间
    private String startWorkDatetime;
    // 开始考勤时间
    private String endWorkDatetime;

    // 早晨工时
    private BigDecimal morningWorkingHours;
    // 上午工时
    private BigDecimal amWorkingHours;
    // 下午工时
    private BigDecimal pmWorkingHours;
    // 夜间工时
    private BigDecimal eveningWorkingHours;

    // 将当天的出勤折算成出勤天数，不会大于1，(上午工时+下午工时)/8
    private BigDecimal attendanceDay;

}
