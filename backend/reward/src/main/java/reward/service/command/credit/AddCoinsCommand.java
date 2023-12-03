package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class AddCoinsCommand implements CreditCommand {
    private CreditReceiver receiver;

    public AddCoinsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.addCoins();
    }
}