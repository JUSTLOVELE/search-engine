package com.hse.core;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
public abstract class BaseService extends Base {

    private final static Log _logger = LogFactory.getLog(BaseService.class);

    public void clearJSON(JSONObject json) {
        json.clear();
        json = null;
    }

	/*@Autowired
	@Qualifier("reverseDependencyInjectionHospitalFactoryImp")
	protected BaseReverseDependencyInjectionHospitalFactory _factory;*/

    /**
     * 获取抽象实现
     * @param <T>
     * @param user
     * @return
     *//*
	public <T> T getAbstractImp(String orgId,String type){

		String beanName = _factory.getHospitalConfig(orgId, type);
		if(beanName == null || "".equals(beanName)){
			return null;
		}
		T o = (T) SpringContextUtil.getBean(beanName);
		if(o == null){
			return null;
		}
		return o;
	}*/

    /**
     * 消除JSONNULL对象
     * @param obj
     * @return
     */
    public String eliminateJsonnull(Object obj){

        if(obj instanceof JSONNull){
            return "";
        }else{
            return (String) obj;
        }
    }

    public String renderResult(boolean status, String key, String value){

        Map<String, Object> displayMap = new HashMap<String, Object>();
        displayMap.put("status", status);
        displayMap.put(key, value);
        String json = getJSON(displayMap);
        clearMap(displayMap);
        return json;
    }
}
