package com.vincent.teng.projectservice.daily.doReflect;

import java.lang.reflect.Method;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/10/1011:59
 */
public class Test {

    public static void target(int i){
        new Exception("#"+i).printStackTrace();
    }


    public static void main(String[] args)throws Exception {
      Class klass= Class.forName("Test");

      Method method= klass.getMethod("target",int.class);

      method.invoke(null,0);
    }
}
