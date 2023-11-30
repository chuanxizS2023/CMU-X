package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public class UpdateProductImageUrlCommand implements ProductCommand {
    private ProductReceiver receiver;

    public UpdateProductImageUrlCommand(ProductReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.updateProductImageUrl();
    }
}
