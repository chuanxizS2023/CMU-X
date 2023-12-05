package reward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.format.DateTimeFormatter;
import reward.exception.ErrorHandling.RewardException;
import reward.service.command.product.CreateProductCommand;
import reward.service.command.product.ProductCommand;
import reward.service.command.product.ProductInvoker;
import reward.service.command.product.ProductReceiver;

@SpringBootApplication
public class Application {

	private final ProductInvoker invoker;

	private final ProductReceiver receiver;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public Application(ProductInvoker invoker, ProductReceiver receiver) {
		this.invoker = invoker;
		this.receiver = receiver;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void insertDefaultIcons() throws RewardException {
		// Create several default products

		receiver.setNewName("icon-advanced");
		receiver.setNewPrice(1);
		receiver.setNewImageUrl("https://cmux-reward.s3.us-east-2.amazonaws.com/advanced.png");
		receiver.setIsPurchasable(true);
		ProductCommand createProductCommand = new CreateProductCommand(receiver);
		invoker.setCommand(createProductCommand);
		invoker.executeCommand();

		receiver.setNewName("icon-tartan");
		receiver.setNewPrice(6);
		receiver.setNewImageUrl("https://cmux-reward.s3.us-east-2.amazonaws.com/tartan.png");
		receiver.setIsPurchasable(false);
		createProductCommand = new CreateProductCommand(receiver);
		invoker.setCommand(createProductCommand);
		invoker.executeCommand();

		receiver.setNewName("icon-mickey");
		receiver.setNewPrice(5);
		receiver.setNewImageUrl("https://cmux-reward.s3.us-east-2.amazonaws.com/mickey.png");
		receiver.setIsPurchasable(true);
		createProductCommand = new CreateProductCommand(receiver);
		invoker.setCommand(createProductCommand);
		invoker.executeCommand();

	}
}
