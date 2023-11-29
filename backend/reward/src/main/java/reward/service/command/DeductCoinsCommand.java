package reward.service.command;

public class DeductCoinsCommand implements Command {
    private Receiver receiver;

    public DeductCoinsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.deductCoins();
    }
}