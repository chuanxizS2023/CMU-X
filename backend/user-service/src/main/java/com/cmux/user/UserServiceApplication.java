package com.cmux.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cmux.user.controller.MQProducer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.amqp.core.Message;
import com.cmux.user.dto.NewUserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	public void sendMessage() throws JsonProcessingException {
		NewUserMessage newUserMessage = new NewUserMessage(1L, "aaa");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(newUserMessage);
		messageProducer.sendNewUserMessage(jsonString);
	}
}
