package com.alex.linkmobility.messagingapp;

import com.alex.linkmobility.messagingapp.service.ConsumerService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.alex.linkmobility.messagingapp.constant.RabbitMQConstants.*;


@SpringBootApplication
public class MessagingAppApplication {
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME.value(), false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME.value());
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(MESSAGE_KEY.value());
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME.value());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService, "consumeMessage");
    }

    @Bean
    public MessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("com.alex.linkmobility.*", "java.util.*"));
        return converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(MessagingAppApplication.class, args).close();
    }

}
