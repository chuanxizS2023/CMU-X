package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;
import reward.model.Credit;

public class GetCreditInfoCommand implements CreditGetCommand<Credit> {
    private CreditReceiver receiver;
    private Credit credit;

    public GetCreditInfoCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        this.credit = receiver.getCreditInfo();
    }

    public Credit getValue() {
        return this.credit;
    }
}
