package reward.service.command.credit;

import java.util.ArrayList;
import java.util.List;

import reward.exception.ErrorHandling.RewardException;
import reward.model.CreditHistory;

public class GetCreditHistoryCommand implements CreditGetCommand<List<CreditHistory>> {
    private CreditReceiver receiver;
    private List<CreditHistory> creditHistories;

    public GetCreditHistoryCommand(CreditReceiver receiver) {
        this.receiver = receiver;
        this.creditHistories = new ArrayList<>();
    }

    public void execute() throws RewardException {
        this.creditHistories.clear();
        List<CreditHistory> history = receiver.getCreditHistory();
        for (CreditHistory ch : history) {
            this.creditHistories.add(ch);
        }
    }

    public List<CreditHistory> getValue() {
        return this.creditHistories;
    }
}
