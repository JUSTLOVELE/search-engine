package com.hse.core;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.hse.common.utils.CommonConstant;
import com.hse.common.utils.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
public abstract class Base {

    private final static Log _logger = LogFactory.getLog(Base.class);

    public void renderJson(HttpServletResponse httpServletResponse, String json) {

        httpServletResponse.setContentType("application/json; charset=utf-8");

        try {

            _logger.debug(json);
            httpServletResponse.getWriter().print(json);
        } catch (Exception e) {

            _logger.debug(e.getMessage());
        }

        json = null;
    }

    public CommonResult<List> getResult() {

        CommonResult<List> result = new CommonResult<List>();
        result.setCode(CommonConstant.Status.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMessage(CommonConstant.SUCCESS_SAVE_MSG);
        return result;
    }


    public String covertEndTime(String endTime) {

        if(StringUtils.isEmpty(endTime)) {
            return null;
        }

        endTime = endTime.substring(0, 10);
        return endTime + " 23:59:59";
    }

    public void renderObject(HttpServletResponse httpServletResponse,Object result) {
        renderJson(httpServletResponse,getJSON(result));
    }

    @SuppressWarnings("rawtypes")
    public void clearMap(Map map){
        map.clear();
        map = null;
    }

    /**
     * 返回102前台打印错误
     * @param errorMsg
     * @return
     */
    public String renderPrintFailureList(String errorMsg) {
        return renderFailure(errorMsg, CommonConstant.Status.PRINT_CODE);
    }

    /**
     * 返回101失败
     * @param errorMsg
     * @return
     */
    public String renderFailureList(String errorMsg) {
        return renderFailure(errorMsg, CommonConstant.Status.FAILURE_CODE);
    }

    private String renderFailure(String errorMsg, int code) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonConstant.Key.CODE,code);
        map.put(CommonConstant.Key.SUCCESS, false);
        map.put(CommonConstant.Key.TOTAL, 0);
        map.put(CommonConstant.Key.DESC, errorMsg);

        return getJSON(map);
    }

    /**
     * 保存成功
     * @param total
     * @return
     */
    public String renderSaveSuccessList(int total) {
        return renderSuccessList(total, null, CommonConstant.SUCCESS_QUERY_MSG);
    }

    /**
     * 保存成功
     * @param total
     * @param datas:
     * @return
     */
    public String renderSaveSuccessList(int total, List<Map<String, Object>> datas) {
        return renderSuccessList(total, datas, CommonConstant.SUCCESS_QUERY_MSG);
    }

    /**
     * 操作成功
     * @param total
     * @return
     */
    public String renderOpSuccessList(int total) {
        return renderSuccessList(total, null, CommonConstant.SUCCESS_REQUEST_MSG);
    }

    public String renderLogOutSuccess() {
        return renderSuccessList(0, null, CommonConstant.SUCCESS_LOGOUT_MSG);
    }


    /**
     * 查询成功
     * @param total
     * @return
     */
    public String renderQuerySuccessList(int total) {
        return renderSuccessList(total, null, CommonConstant.SUCCESS_QUERY_MSG);
    }

    /**
     * 查询成功
     * @param total
     * @param datas:可以为null
     * @return
     */
    public String renderQuerySuccessList(int total, List<Map<String, Object>> datas) {
        return renderSuccessList(total, datas, CommonConstant.SUCCESS_QUERY_MSG);
    }

    public String renderSuccessList(int total, String desc) {
        return renderSuccessList(total, null, desc);
    }

    private String renderSuccessList(int total, List<Map<String, Object>> datas, String desc) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonConstant.Key.CODE, CommonConstant.Status.SUCCESS_CODE);
        map.put(CommonConstant.Key.SUCCESS, true);
        map.put(CommonConstant.Key.TOTAL, total);

        if(datas == null) {
            datas = new ArrayList<Map<String,Object>>();
        }

        map.put(CommonConstant.Key.DATAS, datas);
        map.put(CommonConstant.Key.DESC, desc);

        return getJSON(map);
    }

    public String renderSuccessList(int total, Object datas, String desc) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonConstant.Key.CODE, CommonConstant.Status.SUCCESS_CODE);
        map.put(CommonConstant.Key.SUCCESS, true);
        map.put(CommonConstant.Key.TOTAL, total);
        map.put(CommonConstant.Key.DATAS, datas);
        map.put(CommonConstant.Key.DESC, desc);

        return getJSON(map);
    }



    public String getJSON(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        String msg = "";
        try {
            msg = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            _logger.error("", e);
        }
        _logger.info(msg);
        return msg ;
    }
}
