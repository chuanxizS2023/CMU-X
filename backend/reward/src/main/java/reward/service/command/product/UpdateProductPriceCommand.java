package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public class UpdateProductPriceCommand implements ProductCommand {
    private ProductReceiver receiver;

    public UpdateProductPriceCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.updateProductPrice();
    }
}
