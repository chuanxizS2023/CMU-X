package reward.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import reward.service.S3Service;
import reward.service.command.credit.CreditCommand;
import reward.service.command.credit.CreditInvoker;
import reward.service.command.credit.CreditReceiver;
import reward.service.command.credit.DeductCoinsCommand;
import reward.service.command.credit.GetCoinsCommand;
import reward.service.command.product.ProductCommand;
import reward.service.command.product.CreateProductCommand;
import reward.service.command.product.GetAllProductCommand;
import reward.service.command.product.GetProductCommand;
import reward.service.command.product.ProductInvoker;
import reward.service.command.product.ProductReceiver;
import reward.service.command.product.UpdateProductImageUrlCommand;
import reward.service.command.product.UpdateProductNameCommand;
import reward.service.command.product.UpdateProductPriceCommand;
import reward.dto.CreateProductRequest;
import reward.dto.PurchaseProductRequest;
import reward.dto.UpdateProductRequest;
import reward.exception.ErrorHandling.RewardException;
import reward.model.Product;

@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class RewardController {

    private final ProductInvoker productInvoker;

    private final ProductReceiver productReceiver;

    private final CreditInvoker creditInvoker;

    private final CreditReceiver creditReceiver;

    private final S3Service s3Service;

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);

    private static final String LOGFORMAT = "\n{}\n";

    @PostMapping(value = "/createProduct", consumes = "multipart/form-data")
    public ResponseEntity<String> createProduct(@RequestPart CreateProductRequest createProductRequest,
            @RequestPart("image") MultipartFile image) throws IOException {
        String name = createProductRequest.getName();
        int price = createProductRequest.getPrice();
        // Check if product exists
        try {
            // Get all product command
            ProductCommand getAllProductCommand = new GetAllProductCommand(productReceiver);
            productInvoker.setCommand(getAllProductCommand);
            productInvoker.executeCommand();

            List<Product> products = productInvoker.getAllProducts();

            for (Product p : products) {
                if (p.getName().equals(name)) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error: Product already exists");
                }
            }
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Upload image
        String imageUrl = "";
        try {
            imageUrl = s3Service.uploadFile(image);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Setup product
        productReceiver.setNewName(name);
        productReceiver.setNewPrice(price);
        productReceiver.setNewImageUrl(imageUrl);

        try {
            // Create product command
            ProductCommand createProductCommand = new CreateProductCommand(productReceiver);
            productInvoker.setCommand(createProductCommand);
            productInvoker.executeCommand();
            return ResponseEntity.ok("Successfully create product");
        } catch (RewardException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {

        try {
            // Get all product command
            ProductCommand getAllProductCommand = new GetAllProductCommand(productReceiver);
            productInvoker.setCommand(getAllProductCommand);
            productInvoker.executeCommand();

            List<Product> products = productInvoker.getAllProducts();

            return ResponseEntity.ok(products);
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/update/{productId}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProduct(@PathVariable long productId,
            @RequestPart UpdateProductRequest updateProductRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        // Set the target product id
        productReceiver.setProductId(productId);

        // Check if product exists
        try {
            // Get the product command
            ProductCommand getProductCommand = new GetProductCommand(productReceiver);
            productInvoker.setCommand(getProductCommand);
            productInvoker.executeCommand();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Upload image
        String name = updateProductRequest.getName();
        Integer price = updateProductRequest.getPrice();
        String imageUrl = "";
        try {
            imageUrl = s3Service.uploadFile(image);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        try {
            if (!imageUrl.equals("")) {
                // Set attribute and run command
                productReceiver.setNewImageUrl(imageUrl);
                ProductCommand updateImageUrlCommand = new UpdateProductImageUrlCommand(productReceiver);
                productInvoker.setCommand(updateImageUrlCommand);
                productInvoker.executeCommand();
            }
            if (name != null) {
                // Set attribute and run command
                productReceiver.setNewName(name);
                ProductCommand updateNameCommand = new UpdateProductNameCommand(productReceiver);
                productInvoker.setCommand(updateNameCommand);
                productInvoker.executeCommand();
            }
            if (price != null) {
                // Set attribute and run command
                productReceiver.setNewPrice(price);
                ProductCommand updatePriceCommand = new UpdateProductPriceCommand(productReceiver);
                productInvoker.setCommand(updatePriceCommand);
                productInvoker.executeCommand();
            }
            return ResponseEntity.ok("Successfully updated");
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable long productId) {
        try {
            // Set the target product id
            productReceiver.setProductId(productId);

            // Get the product command
            ProductCommand getProductCommand = new GetProductCommand(productReceiver);
            productInvoker.setCommand(getProductCommand);
            productInvoker.executeCommand();
            Product product = productInvoker.getProduct();
            return ResponseEntity.ok(product);
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/purchaseProduct")
    public ResponseEntity<String> purchaseProduct(@RequestBody PurchaseProductRequest purchaseProductRequest) {
        long userId = purchaseProductRequest.getUserId();
        long productId = purchaseProductRequest.getProductId();
        // Target user id
        creditReceiver.setUserId(userId);
        // Target product id
        productReceiver.setProductId(productId);

        try {
            // Get user coins
            CreditCommand getCoinsCommand = new GetCoinsCommand(creditReceiver);
            creditInvoker.setCommand(getCoinsCommand);
            creditInvoker.executeCommand();
            int coins = creditInvoker.getValue();

            // Get the product command
            ProductCommand getProductCommand = new GetProductCommand(productReceiver);
            productInvoker.setCommand(getProductCommand);
            productInvoker.executeCommand();
            Product product = productInvoker.getProduct();
            int price = product.getPrice();

            if (coins < price) {
                return ResponseEntity.badRequest().body("Cannot buy item because of not enough coins");
            }

            // Deduct user coins command
            CreditCommand deductCoinsCommand = new DeductCoinsCommand(creditReceiver);
            // Set deduct amount
            creditReceiver.setChangeCoinsAmount(price);
            creditInvoker.setCommand(deductCoinsCommand);
            creditInvoker.executeCommand();

            // Return the image url
            String imageUrl = product.getImageUrl();

            coins = coins - price;

            return ResponseEntity.ok("Purchase Successfully, imageUrl: " + imageUrl + ", remain coins: " + coins);
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
