package projectredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectredis.service.DealTestService;
import projectredis.utils.CacheService;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/1311:36
 */

@RestController
public class DealTest1 {
    @Autowired
    private CacheService cacheService;

    @Autowired
    private DealTestService dealTestService;


    @GetMapping("/setValToRedis")
    public void setValToRedis(String key){
        key="hehe";
        cacheService.set(key,"xiaxiaxia");
    }

    @GetMapping("/getVal")
    public Object getVal(String key){
        key="hehe";
        return cacheService.get(key);
    }

    @GetMapping("/doLock")
    public void doLock(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                dealTestService.doTryLock("orderIdwwwwa",Thread.currentThread().getName());
            }
        };
        for (int i = 0; i <10 ; i++) {
            new Thread(runnable).start();
        }
    }
}
