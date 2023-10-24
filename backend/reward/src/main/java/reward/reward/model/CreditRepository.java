package reward.reward.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, String> {
    Credit getCreditByUserId(final String userId);
    void updateCredit(final String userId, final int coins, final int credits);
}