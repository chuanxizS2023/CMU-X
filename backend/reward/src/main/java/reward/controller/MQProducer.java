package reward.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MQProducer {

    private final RabbitTemplate rabbitTemplate;

    public MQProducer (RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String exchange, String routingKey, String data) {
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
    }
}


