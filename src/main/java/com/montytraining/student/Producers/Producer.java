package com.montytraining.student.Producers;


import com.montytraining.student.Entities.Student;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class Producer {

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendJsonMessage(Student student) {
        log.info(String.format("Json message sent -> %s", student));
        amqpTemplate.convertAndSend(exchange, routingKey, student);
    }




}

