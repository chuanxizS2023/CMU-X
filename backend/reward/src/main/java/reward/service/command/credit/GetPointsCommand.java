package reward.service.command.credit;

import reward.exception.ErrorHandling.RewardException;

public class GetPointsCommand implements CreditGetCommand<Integer> {
    private CreditReceiver receiver;
    private int points;

    public GetPointsCommand(CreditReceiver receiver) {
        this.receiver = receiver;
    }

    public void execute() throws RewardException {
        this.points = receiver.getPoints();
    }

    public Integer getValue() {
        return this.points;
    }
}
