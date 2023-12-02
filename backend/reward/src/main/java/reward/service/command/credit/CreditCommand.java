package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public interface CreditCommand {
    void execute() throws RewardException;
}
