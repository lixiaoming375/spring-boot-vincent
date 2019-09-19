package com.vincent.utils;

/**
 * @author tengxiao
 * @description 会员上下文
 * @date 2019/9/1917:42
 */
public class MUOContext {
    static  ThreadLocal<String> uidThreadLocal=new ThreadLocal<>();

    public static  void setUid(String uid){
        uidThreadLocal.set(uid);
    }

    public static String getUid(){
        return uidThreadLocal.get();
    }

    public static void clean() {
        uidThreadLocal.remove();
    }
}
