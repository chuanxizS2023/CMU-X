package reward.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQConsumer {

    @RabbitListener(queues = "cmu-x-reward")
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
