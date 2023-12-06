package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class CreateProductCommand implements Command {
    private ProductReceiver receiver;

    public CreateProductCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.createNewProduct();
    }
}
