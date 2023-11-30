package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class AddPointsCommand implements CreditCommand {
    private CreditReceiver receiver;

    public AddPointsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.addPoints();
    }
}