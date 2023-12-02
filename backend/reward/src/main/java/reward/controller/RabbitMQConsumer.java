package reward.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "cmu-x")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}

