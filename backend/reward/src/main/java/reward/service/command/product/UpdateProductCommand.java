package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class UpdateProductCommand implements Command {
    private ProductReceiver receiver;

    public UpdateProductCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.updateProduct();
    }
}
