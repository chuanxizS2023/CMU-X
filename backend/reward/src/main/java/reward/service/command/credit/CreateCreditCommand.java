package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class CreateCreditCommand implements CreditCommand {
    private CreditReceiver receiver;

    public CreateCreditCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        receiver.createUserCreditInfo();
    }
}
