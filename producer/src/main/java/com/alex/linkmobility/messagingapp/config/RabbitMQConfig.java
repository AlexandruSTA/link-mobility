package com.alex.linkmobility.messagingapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.alex.linkmobility.messagingapp.commons.constant.RabbitMQConstants.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue queueRecipientsA() {
        return new Queue(QUEUE_RECIPIENTS_A_NAME.value(), false);
    }

    @Bean
    Queue queueRecipientsB() {
        return new Queue(QUEUE_RECIPIENTS_B_NAME.value(), false);
    }

    @Bean
    Queue queueRecipientsC() {
        return new Queue(QUEUE_RECIPIENTS_C_NAME.value(), false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME.value());
    }

    @Bean
    Binding bindingRecipientsA(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queueRecipientsA())
                .to(topicExchange)
                .with(MESSAGE_ROUTING_KEY_RECIPIENTS_A.value());
    }

    @Bean
    Binding bindingRecipientsB(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queueRecipientsB())
                .to(topicExchange)
                .with(MESSAGE_ROUTING_KEY_RECIPIENTS_B.value());
    }

    @Bean
    Binding bindingRecipientsC(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queueRecipientsC())
                .to(topicExchange)
                .with(MESSAGE_ROUTING_KEY_RECIPIENTS_C.value());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

}
