package com.cmux.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class MQConfig {

    @Value("${rabbitmq.queue.name.newuser}")
    private String newUserqueue;

    @Value("${rabbitmq.queue.name.update.coins}")
    private String updateCoinsQueue;

    @Value("${rabbitmq.queue.name.update.points}")
    private String updatePointsQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.reward.routing.key.newuser}")
    private String newUserRoutingKey;

    @Value("${rabbitmq.reward.routing.key.newfollower}")
    private String newFollowerRoutingKey;

    @Value("${rabbitmq.reward.routing.key.newlike}")
    private String newLikeRoutingKey;

    @Bean
    Queue newUserQueue() {
        return new Queue(newUserqueue);
    }

    @Bean
    Queue updateCoinsQueue() {
        return new Queue(updateCoinsQueue);
    }

    @Bean
    Queue updatePointsQueue() {
        return new Queue(updatePointsQueue);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Binding newUserBinding() {
        return BindingBuilder.bind(newUserQueue()).to(exchange()).with(newUserRoutingKey);
    }

    @Bean
    Binding updateCoinsBinding() {
        return BindingBuilder.bind(updateCoinsQueue()).to(exchange()).with(newFollowerRoutingKey);
    }

    @Bean
    Binding updatePointsBinding() {
        return BindingBuilder.bind(updatePointsQueue()).to(exchange()).with(newLikeRoutingKey);
    }


    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

