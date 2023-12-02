package com.cmux.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cmux.user.controller.MQProducer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class UserServiceApplication {

	private final MQProducer messageProducer;


	public UserServiceApplication(MQProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendMessage(){
		messageProducer.sendMessage("topicExchange", "cmux.reward", "hahaha from user");
	}
}
