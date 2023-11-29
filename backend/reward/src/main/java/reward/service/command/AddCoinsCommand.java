package reward.service.command;

public class AddCoinsCommand implements Command {
    private Receiver receiver;

    public AddCoinsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.addCoins();
    }
}