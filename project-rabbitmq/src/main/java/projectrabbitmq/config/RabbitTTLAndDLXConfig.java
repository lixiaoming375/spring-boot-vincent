package projectrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/6/511:16
 */
@Configuration
@AutoConfigureAfter(RabbitConfig.class)
public class RabbitTTLAndDLXConfig {

    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 定义死信交换机 DEADLETTER_EXCHANGE，DLX也是一个正常的Exchange，和一般的Exchange没有区别
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     * @return
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange(){
        return ExchangeBuilder.directExchange("DEADLETTER_EXCHANGE").durable(true).build();
    }

    /**
     * 定义队列  DEADLETTER_QUEUE 设置队列的属性  x-dead-letter-exchange 和 x-dead-letter-routing-key
     * 当这个队列中有死信时，RabbitMQ就会自动的将这个消息重新发布到设置的Exchange上去
     * @return
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 声明 死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, "DEADLETTER_EXCHANGE");
        // x-dead-letter-routing-key    声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, "KEY_R");
        return QueueBuilder.durable("DEADLETTER_QUEUE").withArguments(args).build();
    }


    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("DEADLETTER_QUEUE", Binding.DestinationType.QUEUE, "DEADLETTER_EXCHANGE", "DL_KEY", null);
    }


    /**
     * 定义死信队列转发队列.
     * @return the queue
     */
    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable("REDIRECT_QUEUE").build();
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "DEADLETTER_EXCHANGE", "KEY_R", null);
    }


}
