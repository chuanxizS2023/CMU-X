package reward.service.command;

public class GetPointsCommand implements GetCommand<Integer> {
    private Receiver receiver;
    private int points;

    public GetPointsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.points = receiver.getPoints();
    }

    public Integer getValue() {
        return this.points;
    }
}
