package reward.service.command.product;

import reward.exception.ErrorHandling.RewardException;

public interface ProductCommand {
    void execute() throws RewardException;
}
