package com.hse.common.utils;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2020-07-08
 * @version 1.00.00
 * @Description:YmlProjectConfig 读取projectconfig配置信息
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class CommonResult<T> {

    private Integer code;

    private String message;

    private Boolean success;

    private String desc;

    private T datas;

    private Long total;

    public CommonResult(){

    }

    public static CommonResult success(String message, Object data, Long total) {
        return new CommonResult(CommonConstant.Status.SUCCESS_CODE, message, true, data, total);
    }

    public static CommonResult fail() {

        return new CommonResult<List<Map<String, Object>>>(CommonConstant.Status.FAILURE_CODE, null, false, null, 0L);
    }

    public static CommonResult fail(String message) {

        return new CommonResult<List<Map<String, Object>>>(CommonConstant.Status.FAILURE_CODE, message, false, null, 0L);
    }

    public CommonResult(Integer code, String message, Boolean success, T data, Long total) {
        this.code = code;
        this.message = message;
        this.desc = message;
        this.success = success;
        this.datas = data;
        this.total = total;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
