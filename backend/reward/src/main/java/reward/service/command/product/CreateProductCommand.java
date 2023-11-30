package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public class CreateProductCommand implements ProductCommand {
    private ProductReceiver receiver;

    public CreateProductCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.createNewProduct();
    }
}
