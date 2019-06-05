package projectrabbitmq.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author tengxiao
 * @Description: 定制化RabbitTemplate
 * @date 2019/6/414:58
 */
@Configuration
public class RabbitConfig {
    Logger log = LoggerFactory.getLogger(RabbitTemplate.class);

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 定制化amqp模版      可根据需要定制多个
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * 此处为模版类定义 Jackson消息转换器
     * ReturnCallback 接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     *
     * @return rabbitTemplate     RabbitTemplate 实现了 AmqpTemplate
     */
    @Bean
    public AmqpTemplate amqpTemplate(){
        //使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");

        //confirmCallback 确认模式保证producer成功发送到broker        yml 需要配置publisher-returns: true
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
                    log.error("消息发送失败!" + cause + correlationData.toString());
                } else {
                    log.info("消息发送到broker成功,消息ID：" + (correlationData != null ? correlationData.getId() : null));
                }
            }
        });

        //开启mandatory 保证exchange至少将消息route到一个queue中
        rabbitTemplate.setMandatory(true);
        //开启returncallback     yml 需要 配置 publisher-returns: true
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
              String correlationId=message.getMessageProperties().getCorrelationId();
                log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
            }
        });

        return rabbitTemplate;
    }



}
