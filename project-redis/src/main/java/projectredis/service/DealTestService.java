package projectredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectredis.utils.CacheService;

import java.util.UUID;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/1314:05
 */
@Service
public class DealTestService {

    @Autowired
    private CacheService cacheService;

    public void doTryLock(String orderId,String threadName){
       String requestId=UUID.randomUUID().toString();
       if( cacheService.tryLock(orderId, requestId,30,900000)){
           for (int i = 0; i <10; i++) {
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("testtest--"+threadName+"--"+i);
           }
           cacheService.releaseLock(orderId,requestId);
       }
    }
}
