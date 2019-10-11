package com.vincent.teng.projectservice.daily.doGuava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:  漏桶算法和令牌桶的比较
 * @date 2019/9/2411:48
 */
public class GuavaTokenDemo {
    private int qps;
    private int countOfReq;

    private RateLimiter rateLimiter;

    public GuavaTokenDemo(int qps,int countOfReq){
        this.qps=qps;
        this.countOfReq=countOfReq;
    }

    //初始化一个令牌桶
    public GuavaTokenDemo processWithTokenBucket(){
        rateLimiter=RateLimiter.create(qps);
        return this;
    }

    //初始化一个漏桶
    public GuavaTokenDemo processWithLeakyBucket(){
        rateLimiter=RateLimiter.create(qps,0, TimeUnit.MILLISECONDS);
        return this;
    }

    private void processRequest(){
        System.out.println("RateLimiter:"+rateLimiter.getClass());
        long start=System.currentTimeMillis();
        for(int i=0;i<countOfReq;i++){
           rateLimiter.acquire();
        }
        long end=System.currentTimeMillis()-start;
        System.out.println("处理的请求数量："+countOfReq+","+"耗时："+end+",qps:"+rateLimiter.getRate()
                +"，实际qps:"+Math.ceil(countOfReq/(end/1000.00)));
    }

    public void doProcess() throws InterruptedException{
        for(int i=0;i<20;i=i+5){
            TimeUnit.SECONDS.sleep(i);
            processRequest();
        }
    }

    public static void main(String[] args) throws InterruptedException{
//        new GuavaTokenDemo(50,100).processWithLeakyBucket().doProcess();
        new GuavaTokenDemo(50,100).processWithTokenBucket().doProcess();
    }


}
