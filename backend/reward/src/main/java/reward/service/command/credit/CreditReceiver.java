package reward.service.command.credit;

import org.springframework.stereotype.Service;

import lombok.*;
import reward.exception.ErrorHandling.ExceptionType;
import reward.exception.ErrorHandling.RewardException;
import reward.model.Credit;
import reward.model.CreditHistory;
import reward.service.CreditService;

import java.util.List;

@Service
@Getter
@Setter
public class CreditReceiver {

    private Long userId;
    private String username;
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
        creditService.createUserCredit(userId, username);

    }

    // Method to get current credit info for the user
    public Credit getCreditInfo() throws RewardException {
        creditService.setUserCreditInfo(userId);
        return creditService.getCredit();
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
