package reward.service.command.product;

public interface ProductGetCommand<T> extends ProductCommand {
    T getValue();
}
