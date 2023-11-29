package reward.service.command;

import org.springframework.stereotype.Service;

import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;

@Service
public class Invoker {
    private Command command;

    private Object value;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        this.command.execute();

        if (command instanceof GetCommand) {
            value = ((GetCommand<?>) command).getValue();
        }
    }

    public Integer getValue() throws RewardException {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        else{
            throw new RewardException(ExceptionType.WRONGCOMMANDTYPE);
        }
    }
}
