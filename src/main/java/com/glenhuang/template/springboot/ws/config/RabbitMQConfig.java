package com.glenhuang.template.springboot.ws.config;

import com.glenhuang.template.springboot.ws.util.RabbitMQReceiver;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * define a "hello" queue
     * Queue has four fields
     *      1. queue name
     *      2.durable       durable message queue ,rabbitmq if restart, no need to create new queue, default true
     *      3.auto-delete   if not use will delete auto, default set to false
     *      4.exclusive     this queue is only available in this connection, default set to false
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("queue-test");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);

        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // message failure will return to queue, yml need to config "publisher-returns: true"
        rabbitTemplate.setMandatory(true);

        // message return call back, yml need to config "publisher-returns: true"
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("message：{} sent failed, replyCode：{} replyText：{} exchange: {} routingKey: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // message confirm, yml need to config "publisher-confirms: true"
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
//                log.debug("message send to exchange success,id: {}", correlationData.getId());
            } else {
                log.debug("message send to exchange failed, reason: {}", cause);
            }
        });

        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("queue-test");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
