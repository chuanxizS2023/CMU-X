package reward.service.command;

public class GetCoinsCommand implements GetCommand<Integer> {
    private Receiver receiver;
    private int coins;

    public GetCoinsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.coins = receiver.getCoins();
    }

    public Integer getValue() {
        return this.coins;
    }
}
