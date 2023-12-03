package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class GetCoinsCommand implements CreditGetCommand<Integer> {
    private CreditReceiver receiver;
    private Integer coins;

    public GetCoinsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        this.coins = receiver.getCoins();
    }

    public Integer getValue() {
        return this.coins;
    }
}
