package reward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reward.exception.ErrorHandling.RewardException;
import reward.model.CreditHistory;
import reward.service.command.credit.*;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private final CreditInvoker invoker;

	private final CreditReceiver receiver;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public Application(CreditInvoker invoker, CreditReceiver receiver) {
		this.invoker = invoker;
		this.receiver = receiver;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	public void executeCommands() throws RewardException {
		receiver.setChangePointsAmount(10);
		receiver.setChangeCoinsAmount(10);
		receiver.setUserId(001L); // Assuming the user ID is an integer

		// CreateCreditCommand
		CreditCommand createCreditCommand = new CreateCreditCommand(receiver);
		invoker.setCommand(createCreditCommand);
		invoker.executeCommand();

		// GetPointsCommand
		CreditCommand getPointsCommand = new GetPointsCommand(receiver);
		invoker.setCommand(getPointsCommand);
		invoker.executeCommand();

		// GetPointsCommand
		CreditCommand addPointsCommand = new AddPointsCommand(receiver);
		invoker.setCommand(addPointsCommand);
		invoker.executeCommand();

		// GetHistoryCommand
		CreditCommand getCreditHistoryCommand = new GetCreditHistoryCommand(receiver);
		invoker.setCommand(getCreditHistoryCommand);
		invoker.executeCommand();

		List<CreditHistory> value = invoker.getHistory();
		for (CreditHistory history : value) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("\nid: {}\n", history.getId());
				LOGGER.info("\nuserId: {}\n", history.getUserId());
				LOGGER.info("\ncoins: {}\n", history.getCoins());
				LOGGER.info("\npoints: {}\n", history.getPoints());
				LOGGER.info("\ntime: {}\n", history.getTimestamp().format(formatter));
			}
		}
	}
}
