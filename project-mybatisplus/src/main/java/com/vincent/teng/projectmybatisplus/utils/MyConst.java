package com.vincent.teng.projectmybatisplus.utils;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/2417:52
 */
public class MyConst {
    /**
     * redis锁 锁 超时时间
     */
    public static final int LOCK_KEY_EXPIRE_TIME=3;

    /**
     * redis锁  获取锁超时时间 单位毫秒
     */
    public static final long  GET_LOCK_KEY_WAIT_TIME=1000*60*5;

}
