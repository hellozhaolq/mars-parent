package com.zhaolq.mars.common.core.utils;

import java.util.regex.Pattern;

import com.zhaolq.mars.common.core.constant.RegexPool;
import com.zhaolq.mars.common.core.exception.ValidateException;

/**
 * IP工具类
 *
 * @author zhaolq
 * @date 2022/1/29 11:20
 */
public class Ipv4Utils {
    /**
     * 将ip字符串转换为long类型的数字
     * <p>
     * 为什么是long类型而不是int类型呢？google:如果要存ip地址，用什么数据类型比较好
     * 因为MySQL使用32位的无符号整数（UNSIGNED INT）来存储IP地址时效率更高，而且MySQL提供了ip转换函数 inet_aton、inet_ntoa(n取number，a取address)。
     *
     * <p>
     * 思路就是将ip的每一段数字转为8位二进制数，并将它们放在结果的适当位置上
     * 192.168.0.1 的二进制为 11000000 10101000 00000000 00000001，将二进制合在一起就是数字 3232235521。
     * 也可以将二进制反过来，即 00000001 00000000 10101000 11000000，同时需要long转ip字符串的解析过程对应。
     *
     * <p>
     * 一个技巧，就是 或运算。就是将每段的 long 值左移到恰当的位置后跟保存结果的 long 值进行或运算。
     * <p>
     * 以 192.168.0.1 地址为例，或运算过程如下：
     * 00000000 00000000 00000000 00000000      与二进制位全是0的数做或运算
     * 11000000 00000000 00000000 00000000      第一段long值192左移8*3位，然后做或运算
     * ---------------或运算---------------
     * 11000000 00000000 00000000 00000000
     * 00000000 10101000 00000000 00000000      第二段long值168左移8*2位，然后做或运算
     * ---------------或运算---------------
     * 11000000 10101000 00000000 00000000
     * 00000000 00000000 00000000 00000000      第三段long值0左移8*1位，然后做或运算
     * ---------------或运算---------------
     * 11000000 10101000 00000000 00000000
     * 00000000 00000000 00000000 00000001      第四段long值1左移8*0位，然后做或运算
     * ---------------最终结果--------------
     * 11000000 10101000 00000000 00000001
     *
     * @param ipString ipString
     * @return long
     */
    public static long ipv4ToLong(String ipString) {
        if (ipString == null || ipString.trim().equals("")) {
            throw new ValidateException("Invalid IPv4 address!");
        }

        Pattern pattern = Pattern.compile(RegexPool.IPV4);
        if (!pattern.matcher(ipString).matches()) {
            throw new ValidateException("Invalid IPv4 address!");
        }
        // 获取ip的各段
        String[] ipSlices = ipString.split("\\.");
        long result = 0;
        for (int i = 0; i < ipSlices.length; i++) {
            // 将ip的每一段解析为long，并根据位置左移8*n位
            long longSlice = Long.parseLong(ipSlices[i]) << 8 * (3 - i);
            // 或运算
            result = result | longSlice;
        }
        return result;
    }

    /**
     * 将long转换为ip字符串
     * <p>
     * 思路就是将long值的后四位二进制数用 "." 隔开即可。
     * 3232235521 的后四位二进制为 11000000 10101000 00000000 00000001，每8位分割就是ip字符串 192.168.0.1。
     * <p>
     * 一个技巧，就是 与运算。就是将 long 值无符号右移到恰当的位置后跟保存结果的 long 值进行与运算。
     * <p>
     * 以 3232235521(192.168.0.1) 为例，与运算过程如下：
     * 00000000 00000000 00000000 11111111      与255做与运算
     * 00000000 00000000 00000000 11000000      long值无符号右移8*3位，然后做与运算
     * ---------------与运算---------------
     * 00000000 00000000 00000000 11000000      第一段
     * <p>
     * 00000000 00000000 00000000 11111111      与255做与运算
     * 00000000 00000000 11000000 10101000      long值无符号右移8*2位，然后做与运算
     * ---------------与运算---------------
     * 00000000 00000000 00000000 10101000      第二段
     * <p>
     * 00000000 00000000 00000000 11111111      与255做与运算
     * 00000000 11000000 10101000 00000000      long值无符号右移8*1位，然后做与运算
     * ---------------与运算---------------
     * 00000000 00000000 00000000 00000000      第三段
     * <p>
     * 00000000 00000000 00000000 11111111      与255做与运算
     * 11000000 10101000 00000000 00000001      long值无符号右移8*0位，然后做与运算
     * ---------------与运算---------------
     * 00000000 00000000 00000000 00000001      第四段
     *
     * @param ipLong ipLong
     * @return java.lang.String
     */
    public static String longToIpv4(long ipLong) {
        String[] ipString = new String[4];
        for (int i = 0; i < ipString.length; i++) {
            // 无符号右移8*n位
            long longSlice = ipLong >>> 8 * (3 - i);
            // 与运算获取一段ip
            long ipSlice = 0xff & longSlice;
            ipString[i] = String.valueOf(ipSlice);
        }
        return String.join(".", ipString);
    }
}
