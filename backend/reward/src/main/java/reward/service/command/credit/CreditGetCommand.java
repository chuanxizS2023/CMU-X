package reward.service.command.credit;

public interface CreditGetCommand<T> extends CreditCommand {
    T getValue();
}
