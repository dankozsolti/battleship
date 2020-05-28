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

public class GameResults {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String player;

    @Column(nullable = false)
    private String otherPlayer;

    @Column(nullable = false)
    private String winnerPlayer;

    private boolean solved;

    private int steps;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
