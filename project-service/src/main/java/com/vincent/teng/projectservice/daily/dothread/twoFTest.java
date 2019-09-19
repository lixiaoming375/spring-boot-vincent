package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/2013:50
 */
public class twoFTest {

    public static  int getIndex(int[] arr,int num){
        int left=0;
        int right=arr.length-1;
        while (left<=right){
            int mid=(left+right)/2;
            if(arr[mid]>num){
                right=mid-1;
            }else if(arr[mid]<num){
                left=mid+1;
            }else {
                return mid;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arrs={1,2,3,4,5,6};
        System.out.println(twoFTest.getIndex(arrs,4) );

        new ConcurrentHashMap<>();
    }




}
