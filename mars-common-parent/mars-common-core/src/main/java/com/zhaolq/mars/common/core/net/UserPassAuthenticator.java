package com.zhaolq.mars.common.core.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * 账号密码形式的Authenticator
 *
 * @Author zhaolq
 * @Date 2023/6/9 0:18
 */
public class UserPassAuthenticator extends Authenticator {

    private final String user;
    private final char[] pass;

    /**
     * 构造
     *
     * @param user 用户名
     * @param pass 密码
     */
    public UserPassAuthenticator(String user, char[] pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.pass);
    }

}
