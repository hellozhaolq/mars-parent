/**
 * Copyright ©2020 Synjones. All rights reserved.
 */
package com.zhaolq.mars.common.valid;

import javax.validation.Valid;
import java.util.List;

/**
 * 集合参数的校验
 *
 * @Author zhaolq
 * @Date 2020年8月5日 下午5:13:06
 */
public class ValidateRequestBodyList<T> {

    @Valid
    private List<T> list;

    public ValidateRequestBodyList() {
    }

    public ValidateRequestBodyList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
