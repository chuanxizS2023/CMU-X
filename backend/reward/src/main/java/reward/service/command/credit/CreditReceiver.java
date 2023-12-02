package reward.service.command.credit;

import org.springframework.stereotype.Service;

import lombok.*;
import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;
import reward.model.CreditHistory;
import reward.service.CreditService;

import java.util.List;

@Service
@Getter
@Setter
public class CreditReceiver {

    private Long userId;
    private Integer changePointsAmount;
    private Integer changeCoinsAmount;
    private final CreditService creditService;

    public CreditReceiver(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        creditService.setUserCreditInfo(userId);
    }

    public void checkCreditValue() throws RewardException {
        if (changeCoinsAmount == null || changePointsAmount == null) {
            throw new RewardException(ExceptionType.MISSINGCREDITVALUE);
        }
    }

    public void createUserCreditInfo() throws RewardException {
        // Check if coins or points is missing
        this.checkCreditValue();
        creditService.createUserCredit(userId, changeCoinsAmount, changePointsAmount);

    }

    // Method to get current points for the user
    public int getPoints() throws RewardException {
        creditService.setUserCreditInfo(userId);
        return creditService.getPonts();
    }

    // Method to get current coins for the user
    public int getCoins() throws RewardException {
        creditService.setUserCreditInfo(userId);
        return creditService.getCoins();
    }

    // Method to add points for the user
    public void addPoints() throws RewardException {
        creditService.setUserCreditInfo(userId);
        creditService.addPoints(changePointsAmount);

    }

    // Method to add coins for the user
    public void addCoins() throws RewardException {
        creditService.setUserCreditInfo(userId);
        creditService.addCoins(changeCoinsAmount);
    }

    // Method to deduct coins for the user
    public void deductCoins() throws RewardException {
        creditService.setUserCreditInfo(userId);
        creditService.deductCoins(changeCoinsAmount);
    }

    // Method to find credit history
    public List<CreditHistory> getCreditHistory() throws RewardException {
        creditService.setUserCreditInfo(userId);
        return creditService.findHistoryByUserId(userId);
    }
}
