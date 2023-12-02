package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class DeductCoinsCommand implements CreditCommand {
    private CreditReceiver receiver;

    public DeductCoinsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.deductCoins();
    }
}