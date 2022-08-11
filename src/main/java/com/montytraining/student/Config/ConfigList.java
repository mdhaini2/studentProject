package com.montytraining.student.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigList {
    @Value("${studentList.queue.name}")
    private String queueName;

    @Value("${studentList.exchange.name}")
    private String exchange;

    @Value("${studentList.routing.key}")
    private String routingKey;

    @Bean("student-list-queue")
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean("student-list-exchange")
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }


    @Bean("studentList-queue-binding")
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

}
