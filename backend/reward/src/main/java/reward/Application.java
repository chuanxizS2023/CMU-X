package reward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reward.exception.ErrorHandling.RewardException;
import reward.service.command.*;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Invoker invoker;

	@Autowired
    private Receiver receiver;
	

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
		
    }

    @EventListener(ApplicationReadyEvent.class)
    public void executeCommands() throws RewardException {
        receiver.setChangePointsAmount(10);
        receiver.setChangeCoinsAmount(10);
        receiver.setUserId(001L); // Assuming the user ID is an integer

        // CreateCreditCommand
        Command createCreditCommand = new CreateCreditCommand(receiver);
        invoker.setCommand(createCreditCommand);
        invoker.executeCommand();

        // GetPointsCommand
        Command getPointsCommand = new GetPointsCommand(receiver);
        invoker.setCommand(getPointsCommand);
        invoker.executeCommand();
        
        Integer value = (Integer) invoker.getValue();
        LOGGER.info("%ncnm: {}%n", value);
    }
}

