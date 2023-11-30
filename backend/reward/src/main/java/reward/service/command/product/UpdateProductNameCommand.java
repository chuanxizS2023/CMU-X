package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public class UpdateProductNameCommand implements ProductCommand {
    private ProductReceiver receiver;

    public UpdateProductNameCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.updateProductName();
    }
}
