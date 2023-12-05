package reward.service.command.product;

import org.springframework.stereotype.Service;

import lombok.*;
import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;
import reward.model.Product;
import reward.service.ProductService;

import java.util.List;

@Service
@Getter
@Setter
public class ProductReceiver {

    private Long productId;
    private String newName;
    private Integer newPrice;
    private String newImageUrl;
    private Boolean isPurchasable;
    private final ProductService productService;

    public ProductReceiver(ProductService productService) {
        this.productService = productService;
    }

    public void setProductId(long productId) {
        this.productId = productId;
        productService.setProductInfo(productId);
    }

    public void checkProductValue() throws RewardException {
        if (newName == null || newPrice == null || newImageUrl == null || isPurchasable == null) {
            throw new RewardException(ExceptionType.MISSINGPRODUCTINFO);
        }
    }

    public void createNewProduct() throws RewardException {
        // Check if any field is missing
        this.checkProductValue();
        productService.createProduct(newName, newPrice, newImageUrl, isPurchasable);

    }

    // Method to get product image url
    public Product getProduct() throws RewardException {
        productService.setProductInfo(productId);
        return productService.getProduct();
    }

    // Method to update product name
    public void updateProductName() throws RewardException {
        productService.setProductInfo(productId);
        productService.updateName(newName);
    }

    // Method to update product price
    public void updateProductPrice() throws RewardException {
        productService.setProductInfo(productId);
        productService.updatePrice(newPrice);
    }

    // Method to update product image url
    public void updateProductImageUrl() throws RewardException {
        productService.setProductInfo(productId);
        productService.updateImageUrl(newImageUrl);
    }

    // Method to update product image url
    public void updateProductPurchasable() throws RewardException {
        productService.setProductInfo(productId);
        productService.updateProductPurchasable(isPurchasable);
    }

    // Method to get all product
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
