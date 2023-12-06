package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class AddPointsCommand implements Command {
    private CreditReceiver receiver;

    public AddPointsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.addPoints();
    }
}