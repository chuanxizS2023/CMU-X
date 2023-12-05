package reward.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reward.dto.NewCreditMessage;
import reward.dto.NewUserMessage;
import reward.dto.PurchaseProductMessage;
import reward.exception.ErrorHandling.RewardException;
import reward.model.Product;
import reward.service.command.credit.AddCoinsCommand;
import reward.service.command.credit.AddPointsCommand;
import reward.service.command.credit.CreateCreditCommand;
import reward.service.command.credit.CreditInvoker;
import reward.service.command.credit.CreditReceiver;
import reward.service.command.credit.CreditCommand;
import reward.service.command.credit.GetCreditInfoCommand;
import reward.service.command.product.ProductCommand;
import reward.service.command.product.GetAllProductCommand;
import reward.service.command.product.ProductInvoker;
import reward.service.command.product.ProductReceiver;

@Component
public class MQConsumer {

    private final ObjectMapper mapper = new ObjectMapper();

    private final CreditInvoker creditInvoker;

    private final CreditReceiver creditReceiver;

    private final ProductInvoker productInvoker;

    private final ProductReceiver productReceiver;

    private final MQProducer messageProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumer.class);

    private static final String LOGFORMAT = "\n{}\n";

    public MQConsumer(CreditInvoker creditInvoker, CreditReceiver creditReceiver, ProductInvoker productInvoker,
            ProductReceiver productReceiver, MQProducer messageProducer) {
        this.creditInvoker = creditInvoker;
        this.creditReceiver = creditReceiver;
        this.productInvoker = productInvoker;
        this.productReceiver = productReceiver;
        this.messageProducer = messageProducer;
    }

    @RabbitListener(queues = { "${rabbitmq.queue.name.newuser}" })
    public void receiveNewUserMessage(String message) throws JsonProcessingException {
        NewUserMessage m = mapper.readValue(message, NewUserMessage.class);
        String username = m.getUsername();
        Long userId = m.getUserId();
        // Set up credit receiver info
        creditReceiver.setUserId(userId);
        creditReceiver.setUsername(username);
        try {

            CreditCommand createCreditInfoCommand = new CreateCreditCommand(creditReceiver);
            creditInvoker.setCommand(createCreditInfoCommand);
            creditInvoker.executeCommand();
        } catch (RewardException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
        }

    }

    @RabbitListener(queues = { "${rabbitmq.queue.name.update.points}" })
    public void receiveFollowersMessage(String message) {
        try {
            NewCreditMessage m = mapper.readValue(message, NewCreditMessage.class);
            Long userId = m.getUserId();
            int amount = m.getAmount();

            //
            CreditCommand getCreditCommand = new GetCreditInfoCommand(creditReceiver);
            creditInvoker.setCommand(getCreditCommand);
            creditInvoker.executeCommand();
            int oldPoint = creditInvoker.getCreditInfo().getPoints();

            // Set up credit receiver info
            // When there is a new follower, give one more point
            creditReceiver.setUserId(userId);
            creditReceiver.setChangePointsAmount(amount);

            // Write into db
            CreditCommand addPointsCommand = new AddPointsCommand(creditReceiver);
            creditInvoker.setCommand(addPointsCommand);
            creditInvoker.executeCommand();

            int point = oldPoint + amount;

            ProductCommand getAllProductCommand = new GetAllProductCommand(productReceiver);
            productInvoker.setCommand(getAllProductCommand);
            productInvoker.executeCommand();
            List<Product> allProducts = productInvoker.getAllProducts();

            for (Product p : allProducts) {
                // Give a message to user service that the product is unlocked
                // Check if the old point is smaller than price as well to avoid sending product
                // that user already unlocked
                int price = p.getPrice();
                if (oldPoint < price && price <= point) {
                    // Send message to MQ
                    PurchaseProductMessage purchaseProductMessage = new PurchaseProductMessage(userId, p.getId(),
                            p.getImageUrl());
                    String jsonString = mapper.writeValueAsString(purchaseProductMessage);
                    messageProducer.sendImageToUser(jsonString);
                }
            }

        } catch (RewardException | JsonProcessingException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
        }

    }

    @RabbitListener(queues = { "${rabbitmq.queue.name.update.coins}" })
    public void receiveLikesMessage(String message) {
        try {
            NewCreditMessage m = mapper.readValue(message, NewCreditMessage.class);
            Long userId = m.getUserId();
            int amount = m.getAmount();

            // Set up receiver info
            // When there is a new like, give one more coin
            creditReceiver.setUserId(userId);
            creditReceiver.setChangeCoinsAmount(amount);

            CreditCommand addCoinsCommand = new AddCoinsCommand(creditReceiver);
            creditInvoker.setCommand(addCoinsCommand);
            creditInvoker.executeCommand();
        } catch (RewardException | JsonProcessingException e) {
            LOGGER.info(LOGFORMAT, e.getMessage());
        }

    }
}
