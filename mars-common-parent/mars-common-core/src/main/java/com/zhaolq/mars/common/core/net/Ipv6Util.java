package com.zhaolq.mars.common.core.net;

import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Ipv6工具类
 *
 * @Author zhaolq
 * @Date 2023/6/8 20:20
 * @Since 1.0.0
 */
public class Ipv6Util {

    /**
     * 将IPv6地址字符串转为大整数
     *
     * @param IPv6Str 字符串
     * @return 大整数, 如发生异常返回 null
     */
    public static BigInteger ipv6ToBitInteger(String IPv6Str) {
        try {
            InetAddress address = InetAddress.getByName(IPv6Str);
            if (address instanceof Inet6Address) {
                return new BigInteger(1, address.getAddress());
            }
        } catch (UnknownHostException ignore) {
        }
        return null;
    }

    /**
     * 将大整数转换成ipv6字符串
     *
     * @param bigInteger 大整数
     * @return IPv6字符串, 如发生异常返回 null
     */
    public static String bigIntegerToIPv6(BigInteger bigInteger) {
        try {
            return InetAddress.getByAddress(bigInteger.toByteArray()).toString().substring(1);
        } catch (UnknownHostException ignore) {
            return null;
        }
    }
}
