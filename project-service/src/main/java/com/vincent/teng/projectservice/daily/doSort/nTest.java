package com.vincent.teng.projectservice.daily.doSort;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/2013:50
 */
public class nTest {

   static int goadd(int x) {
        if (x == 1){
            return 1;
        }
        else if (x == 2){
            return 2;
        }
        else if (x == 3){
            return 4;
        }
        else{
            return goadd(x - 1) + goadd(x-2)+goadd(x-3);
        }

    }

    public static void main(String[] args) {
        System.out.println( nTest.goadd(100));
    }

}
