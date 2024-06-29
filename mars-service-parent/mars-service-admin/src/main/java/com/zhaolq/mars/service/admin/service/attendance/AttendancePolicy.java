package com.zhaolq.mars.service.admin.service.attendance;

import lombok.Data;

/**
 * 考勤策略
 *
 * @Author zhaolq
 * @Date 2023/4/7 9:35
 */
@Data
public class AttendancePolicy {
    // 日考勤边界线
    private String boundaryLine;
    // 弹性时间
    private String flexiTimeStart;
    private String flexiTimeEnd;
    // 午休时间
    private String lunchBreakStart;
    private String lunchBreakEnd;
    // 晚餐时间
    private String dinnerTimeStart;
    private String dinnerTimeEnd;
}
