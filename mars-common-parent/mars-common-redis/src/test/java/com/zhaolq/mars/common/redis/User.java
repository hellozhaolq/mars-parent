package com.zhaolq.mars.common.redis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/15 20:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private String id;
    private String account;
    private String password;
    private String name;

}
