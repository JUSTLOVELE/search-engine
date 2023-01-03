package com.hse.common.utils.enums;

public enum CacheTimeUnit {

    SECONDS(1, "秒"),
    MINUTES(60, "分"),
    HOURS(60*60, "时"),
    DAYS(60*60*24, "天")
    ;

    private int second;

    private String desc;

    CacheTimeUnit(int second, String desc) {
        this.second = second;
        this.desc = desc;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
