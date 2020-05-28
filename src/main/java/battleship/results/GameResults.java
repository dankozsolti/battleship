package battleship.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
/**
 * Class representing the result of a game played by a specific player.
 */
public class GameResults {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the player.
     */
    @Column(nullable = false)
    private String player;

    /**
     * The name of the other player.
     */
    @Column(nullable = false)
    private String otherPlayer;

    /**
     * The name of the player who won the game.
     */
    @Column(nullable = false)
    private String winnerPlayer;

    /**
     * Indicates whether the player has solved the puzzle.
     */
    private boolean solved;

    /**
     * The number of misses by the player.
     */
    private int misses;

    /**
     * The duration of the game.
     */
    @Column(nullable = false)
    private Duration duration;

    /**
     * The timestamp when the result was saved.
     */
    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
