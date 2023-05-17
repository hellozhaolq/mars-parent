package com.zhaolq.mars.service.admin.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import com.zhaolq.mars.service.admin.service.attendance.AttendanceCalc;
import com.zhaolq.mars.service.admin.service.attendance.AttendanceInfo;
import com.zhaolq.mars.service.admin.service.attendance.AttendancePolicy;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 考勤
 *
 * @author zhaolq
 * @date 2023/5/17 11:03:14
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/attendance", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class AttendanceController {
    @RequestMapping(value = "/offWork", method = RequestMethod.POST)
    public R<Object> offWork(@RequestBody String jsonString) throws IOException {
        try {
            System.getProperties().load(Files.newInputStream(Paths.get("./time.properties")));
        } catch (IOException e) {
            // 项目目录没找到文件，从资源配置中获取
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config/time.properties"));
        }

        // 获取策略
        AttendancePolicy policy = new AttendancePolicy();
        policy.setBoundaryLine(" " + System.getProperty("boundaryLine"));
        policy.setFlexiTimeStart(" " + System.getProperty("flexiTimeStart"));
        policy.setFlexiTimeEnd(" " + System.getProperty("flexiTimeEnd"));
        policy.setLunchBreakStart(" " + System.getProperty("lunchBreakStart"));
        policy.setLunchBreakEnd(" " + System.getProperty("lunchBreakEnd"));
        policy.setDinnerTimeStart(" " + System.getProperty("dinnerTimeStart"));
        policy.setDinnerTimeEnd(" " + System.getProperty("dinnerTimeEnd"));

        JSONObject jsonObject = JSON.parseObject(jsonString);
        // json中获取考勤数据list，重新放入实体
        List<Map<String, String>> listJson = (ArrayList<Map<String, String>>) jsonObject.getJSONObject("result")
                .getJSONObject("data").getJSONObject("page").get("items");
        List<AttendanceInfo> attendanceInfoList = new ArrayList<>();
        listJson.forEach(ele -> {
            AttendanceInfo entity = new AttendanceInfo();
            entity.setEmployeeName(ele.get("empName"));
            entity.setEmployeeId(ele.get("empId"));
            entity.setAttendanceTime(ele.get("checktime"));
            attendanceInfoList.add(entity);
        });

        R<Object> result = null;
        try {
            AttendanceCalc attendanceCalc = new AttendanceCalc(attendanceInfoList, policy);
            result = attendanceCalc.calc();
        } catch (Exception e) {
            result = R.failure(ResultCode.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/print", method = RequestMethod.POST)
    public void print(@RequestBody String str) {
        log.info(str);
    }
}
