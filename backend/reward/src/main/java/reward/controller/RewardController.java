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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import reward.service.S3Service;
import reward.service.command.credit.CreditInvoker;
import reward.service.command.credit.CreditReceiver;
import reward.service.command.credit.DeductCoinsCommand;
import reward.service.command.credit.GetCreditInfoCommand;
import reward.service.command.Command;
import reward.service.command.product.CreateProductCommand;
import reward.service.command.product.GetAllProductCommand;
import reward.service.command.product.GetProductCommand;
import reward.service.command.product.ProductInvoker;
import reward.service.command.product.ProductReceiver;
import reward.service.command.product.UpdateProductCommand;
import reward.dto.CreateProductRequest;
import reward.dto.PurchaseProductRequest;
import reward.dto.UpdateProductRequest;
import reward.dto.PurchaseProductMessage;
import reward.exception.ErrorHandling.RewardException;
import reward.model.Credit;
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

    private final MQProducer messageProducer;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);

    private static final String LOGFORMAT = "\n{}\n";

    private void executeCommand(Command productCommand) throws RewardException {
        productInvoker.setCommand(productCommand);
        productInvoker.executeCommand();
    }

    private List<Product> getProducts() throws RewardException {
        executeCommand(new GetAllProductCommand(productReceiver));
        return productInvoker.getAllProducts();
    }

    @PostMapping(value = "/createProduct", consumes = "multipart/form-data")
    public ResponseEntity<String> createProduct(@RequestPart CreateProductRequest createProductRequest,
            @RequestPart("image") MultipartFile image) throws IOException {
        String name = createProductRequest.getName();
        int price = createProductRequest.getPrice();
        // Check if product exists
        try {
            List<Product> products = getProducts();
            if (products.stream().anyMatch(p -> p.getName().equals(name))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: Product already exists");
            }
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bad request: Need to provide an icon image");
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
            executeCommand(new CreateProductCommand(productReceiver));
            return ResponseEntity.ok("Successfully create product");
        } catch (RewardException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {

        try {
            List<Product> products = getProducts();
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
        // Check if product exists
        try {
            // Get the product command
            executeCommand(new GetProductCommand(productReceiver));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        String name = updateProductRequest.getName();
        Integer price = updateProductRequest.getPrice();
        Boolean isPurchasable = updateProductRequest.getIsPurchasable();
        String imageUrl = "";
        if (!image.isEmpty()) {
            // Upload image
            try {
                imageUrl = s3Service.uploadFile(image);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        // Set the target product
        productReceiver.setProductId(productId);
        productReceiver.setNewName(name);
        productReceiver.setNewPrice(price);
        productReceiver.setIsPurchasable(isPurchasable);
        if (!imageUrl.isEmpty()) {
            productReceiver.setNewImageUrl(imageUrl);
        }

        try {
            executeCommand(new UpdateProductCommand(productReceiver));
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
            executeCommand(new GetProductCommand(productReceiver));
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
            executeCommand(new GetCreditInfoCommand(creditReceiver));
            Credit creditInfo = creditInvoker.getCreditInfo();
            int coins = creditInfo.getCoins();

            // Get the product
            executeCommand(new GetProductCommand(productReceiver));
            Product product = productInvoker.getProduct();
            int price = product.getPrice();

            if (coins < price) {
                return ResponseEntity.badRequest().body("Cannot buy item because of not enough coins");
            }

            // Set deduct amount
            creditReceiver.setChangeCoinsAmount(price);
            // Deduct user coins command
            executeCommand(new DeductCoinsCommand(creditReceiver));

            // Return the image url
            String imageUrl = product.getImageUrl();

            coins = coins - price;

            // Message that user successfully buy the product
            PurchaseProductMessage purchaseProductMessage = new PurchaseProductMessage(userId, productId, imageUrl);
            String jsonString = mapper.writeValueAsString(purchaseProductMessage);
            messageProducer.sendImageToUser(jsonString);

            return ResponseEntity.ok("Purchase Successfully, imageUrl: " + imageUrl + ", remain coins: " + coins);
        } catch (RewardException | JsonProcessingException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
