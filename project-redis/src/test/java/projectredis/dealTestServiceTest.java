package projectredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import projectredis.service.DealTestService;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/1314:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootVincentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class dealTestServiceTest {
    @Autowired
    private DealTestService dealTestService;

    @Test
    public void doTestIt(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                dealTestService.doTryLock("orderIdIs009",Thread.currentThread().getName());
            }
        };
        for (int i = 0; i <20 ; i++) {
            new Thread(runnable).start();
        }
    }


}
