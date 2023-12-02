package reward.service.command.product;

import java.util.ArrayList;
import java.util.List;

import reward.exception.ErrorHandling.RewardException;
import reward.model.Product;

public class GetAllProductCommand implements ProductGetCommand<List<Product>> {
    private ProductReceiver receiver;
    private List<Product> allProducts;

    public GetAllProductCommand(ProductReceiver receiver) {
        this.receiver = receiver;
        allProducts = new ArrayList<>();
    }

    public void execute() throws RewardException {
        this.allProducts.clear();
        List<Product> products = receiver.getAllProducts();
        for (Product p : products) {
            this.allProducts.add(p);
        }
    }

    public List<Product> getValue() {
        return this.allProducts;
    }
}
