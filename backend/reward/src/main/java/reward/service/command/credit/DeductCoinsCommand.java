package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class DeductCoinsCommand implements Command {
    private CreditReceiver receiver;

    public DeductCoinsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.deductCoins();
    }
}