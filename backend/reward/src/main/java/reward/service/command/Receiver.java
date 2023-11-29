package reward.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;
import reward.service.CreditService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Receiver {

    private Long userId;
    private Integer changePointsAmount;
    private Integer changeCoinsAmount;
    private final CreditService creditService;

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    public Receiver(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        creditService.setUserCreditInfo(userId);
    }

    public long getUserId() {
        return this.userId;
    }

    public void setChangePointsAmount(int changePointsAmount) {
        this.changePointsAmount = changePointsAmount;
    }

    public long getChangePointsAmount() {
        return this.changePointsAmount;
    }

    public void setChangeCoinsAmount(int changeCoinsAmount) {
        this.changeCoinsAmount = changeCoinsAmount;
    }

    public long getChangeCointsAmount() {
        return this.changeCoinsAmount;
    }

    public void checkCreditValue() throws RewardException {
        if (changeCoinsAmount == null || changePointsAmount == null) {
            throw new RewardException(ExceptionType.MISSINGCREDITVALUE);
        }
    }

    public void createUserCreditInfo() {
        try {
            // Check if coins or points is missing
            this.checkCreditValue();
            creditService.createUserCredit(userId, changeCoinsAmount, changePointsAmount);
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
        }

    }

    // Method to get current points for the user
    public int getPoints() {
        creditService.setUserCreditInfo(userId);
        try {
            return creditService.getPonts();
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
            return -1;
        }
    }

    // Method to get current coins for the user
    public int getCoins() {
        creditService.setUserCreditInfo(userId);
        try {
            return creditService.getCoins();
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
            return -1;
        }
    }

    // Method to add points for the user
    public void addPoints() {
        creditService.setUserCreditInfo(userId);
        try {
            creditService.addPoints(changePointsAmount);
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
        }
    }

    // Method to add coins for the user
    public void addCoins() {
        creditService.setUserCreditInfo(userId);
        try {
            creditService.addCoins(changeCoinsAmount);
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
        }
    }

    // Method to deduct coins for the user
    public void deductCoins() {
        creditService.setUserCreditInfo(userId);
        try {
            creditService.deductCoins(changeCoinsAmount);
        } catch (RewardException e) {
            LOGGER.info("%n{}%n", e.getMessage());
        }
    }
}
