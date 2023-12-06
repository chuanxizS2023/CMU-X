package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;
import reward.service.command.Command;

public class CreateCreditCommand implements Command {
    private CreditReceiver receiver;

    public CreateCreditCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.createUserCreditInfo();
    }
}
