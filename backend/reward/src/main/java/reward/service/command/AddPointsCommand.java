package reward.service.command;

public class AddPointsCommand implements Command {
    private Receiver receiver;

    public AddPointsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.addPoints();
    }
}