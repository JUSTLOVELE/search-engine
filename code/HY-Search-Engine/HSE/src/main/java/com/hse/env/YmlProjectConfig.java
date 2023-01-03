package com.hse.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:YmlProjectConfig 读取projectconfig配置信息
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-06 
 * @version 1.00.00
 * @history:
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "projectconfig")    
public class YmlProjectConfig {

    private String indexpath;

    public String getIndexpath() {
        return indexpath;
    }

    public void setIndexpath(String indexpath) {
        this.indexpath = indexpath;
    }
}
