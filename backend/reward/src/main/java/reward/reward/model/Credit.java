package reward.reward.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_credits")
public class Credit {
    @Id
    private Long userId;
    private String username;
    private int coins;
    private int credits;

    protected Credit() {
    }

    public Credit(Long userId, String username, int coins, int credits) {
        this.userId = userId;
        this.username = username;
        this.coins = coins;
        this.credits = credits;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
