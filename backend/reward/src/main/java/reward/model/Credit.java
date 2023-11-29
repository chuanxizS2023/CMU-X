package reward.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_credits")
public class Credit {
    @Id
    private Long userId;
    private int coins;
    private int points;

    protected Credit() {
    }

    public Credit(Long userId, int coins, int points) {
        this.userId = userId;
        this.coins = coins;
        this.points = points;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
