package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public class UpdateProductPurchasableCommand implements ProductCommand {
    private ProductReceiver receiver;

    public UpdateProductPurchasableCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.updateProductPurchasable();
    }
}
