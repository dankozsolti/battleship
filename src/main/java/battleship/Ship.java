package battleship;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ship {
    private int size;
    private int direction;

    private int x;
    private int y;
}
