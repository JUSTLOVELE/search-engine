package com.hse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-04-26
 * @version 1.00.00
 * @history:
 */
@EnableScheduling
@SpringBootApplication(exclude = SolrAutoConfiguration.class)
@ServletComponentScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
