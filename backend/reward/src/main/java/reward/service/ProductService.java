package reward.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import reward.model.Product;
import reward.model.ProductRepository;
import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private Optional<Product> productInfo;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProductInfo(long id) {
        this.productInfo = productRepository.findById(id);
    }

    public void createProduct(String name, int price, String imageUrl) throws RewardException {
        Optional<Product> existProduct = this.productRepository.findByName(name);
        if (!existProduct.isPresent()) {
            Product product = new Product(name, price, imageUrl);
            this.productInfo = Optional.of(product);
            this.productRepository.save(product);
        } else {
            throw new RewardException(ExceptionType.PRODUCTEXISTS);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct() throws RewardException {
        // Check if product exists
        if (!productInfo.isPresent()) {
            throw new RewardException(ExceptionType.PRODUCTNOTFOUND);
        }
        return productInfo.get();
    }

    public void updatePrice(int amount) throws RewardException {
        // Check if product exists
        if (!productInfo.isPresent()) {
            throw new RewardException(ExceptionType.PRODUCTNOTFOUND);
        }

        // Update price
        Product product = productInfo.get();
        product.setPrice(amount);
        productInfo = Optional.of(product);
        this.productRepository.save(product);
    }

    public void updateName(String name) throws RewardException {
        // Check if product exists
        if (!productInfo.isPresent()) {
            throw new RewardException(ExceptionType.PRODUCTNOTFOUND);
        }
        // Check if product name exists
        Optional<Product> existProduct = this.productRepository.findByName(name);
        if (!existProduct.isPresent()) {
            // Update name
            Product product = productInfo.get();
            product.setName(name);
            productInfo = Optional.of(product);
            this.productRepository.save(product);
        } else {
            throw new RewardException(ExceptionType.INVALIDPRODUCTNAME);
        }
    }

    public void updateImageUrl(String imageUrl) throws RewardException {
        // Check if product exists
        if (!productInfo.isPresent()) {
            throw new RewardException(ExceptionType.PRODUCTNOTFOUND);
        }

        // Update image
        Product product = productInfo.get();
        product.setImageUrl(imageUrl);
        productInfo = Optional.of(product);
        this.productRepository.save(product);
    }

    public void deleteProduct() throws RewardException {
        // Check if product exists
        if (!productInfo.isPresent()) {
            throw new RewardException(ExceptionType.PRODUCTNOTFOUND);
        }

        // Delete product
        Product product = productInfo.get();
        productInfo = Optional.empty();
        this.productRepository.delete(product);
    }
}
