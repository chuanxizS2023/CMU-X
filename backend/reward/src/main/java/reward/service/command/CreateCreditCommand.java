package reward.service.command;

public class CreateCreditCommand implements Command {
    private Receiver receiver;

    public CreateCreditCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.createUserCreditInfo();
    }
}
