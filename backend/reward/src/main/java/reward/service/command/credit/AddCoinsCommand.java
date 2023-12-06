package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class AddCoinsCommand implements Command {
    private CreditReceiver receiver;

    public AddCoinsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.addCoins();
    }
}