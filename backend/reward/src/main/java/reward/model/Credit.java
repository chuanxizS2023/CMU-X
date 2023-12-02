package reward.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user_credits")
public class Credit {
    @Id
    @Getter
    private Long userId;

    private int coins;
    private int points;

    protected Credit() {
    }
}
