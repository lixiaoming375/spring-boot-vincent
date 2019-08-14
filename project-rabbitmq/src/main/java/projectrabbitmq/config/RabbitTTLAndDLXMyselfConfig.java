package projectrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tengxiao
 * @Description:   在  RabbitTTLAndDLXConfig的基础上进行优化
 *
 * @date 2019/6/511:16
 */
@Configuration
@AutoConfigureAfter(RabbitConfig.class)
public class RabbitTTLAndDLXMyselfConfig {

    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    // 定义邮件Exchange
    @Bean("emailExchange")
    public Exchange emailExchange(){
        return ExchangeBuilder.directExchange("EMAIL_EXCHANGE").durable(true).build();
    }


    // 定义邮件Queue
    @Bean("eamilQueue")
    public Queue eamilQueue(){
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange 声明 死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, "DL_EXCHANGE");
        // x-dead-letter-routing-key    声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, "DL_ROUTING_KEY");
//        args.put("x-message-ttl",5000);
        return QueueBuilder.durable("EMAIL_QUEUE").withArguments(args).build();
    }


    /**
     * 绑定邮件队列到邮件交换机
     * @return the binding
     */
    @Bean
    public Binding bindingExchangeEamil() {
        return new Binding("EMAIL_QUEUE", Binding.DestinationType.QUEUE, "EMAIL_EXCHANGE", "EMAIL_ROUTING_KEY", null);
    }



    /*
     * 创建死信交换机
     */
    @Bean("deadExchange")
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange("DL_EXCHANGE").durable(true).build();
    }

    /**
     * 定义死信队列(死信将转发到这个队列上).
     */
    @Bean("deadQueue")
    public Queue deadQueue() {
        return QueueBuilder.durable("DL_QUEUE").build();
    }


    /**
     * 死信路由通过 KEY_R 绑定键绑定 死信交换机到死信队列上.
     * @return the binding
     */
    @Bean
    public Binding bindingExchangeDL() {
        return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "DL_ROUTING_KEY", null);
    }


}
