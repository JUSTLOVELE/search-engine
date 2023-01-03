package com.hse.common.utils;

/**
 * @Description:SQL异常
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class SQLException extends RuntimeException{

    public SQLException() {
        super();
    }

    public SQLException(String message) {
        super(message);
    }
}
