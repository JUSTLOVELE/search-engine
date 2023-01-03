package com.hse.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-07-10
 * @version 1.00.00
 * @history:
 */
public class ThreadPoolUtil {
	//计算CPU核数*2作为线程池数量
	private final static int threadCount = Runtime.getRuntime().availableProcessors() * 2;
    
    private final static ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
