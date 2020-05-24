package battleship;

import lombok.*;

@Data
@AllArgsConstructor
public class Ship {

    private int size;
    private int direction;

    private int x;
    private int y;

    private int getHeight() {
        if (this.direction == 1)
            return this.size + 1;
        return 1 + 1;
    }

    private int getWidth() {
        if (this.direction == 1)
            return 1 + 1;
        return this.size + 1;
    }

    public boolean inShip(Ship other) {
        float xmin = Math.max(this.x, other.x);
        float xmax1 = this.x + this.getWidth();
        float xmax2 = other.x + other.getWidth();
        float xmax = Math.min(xmax1, xmax2);
        if (xmax > xmin) {
            float ymin = Math.max(this.y, other.y);
            float ymax1 = this.y + this.getHeight();
            float ymax2 = other.y + other.getHeight();
            float ymax = Math.min(ymax1, ymax2);
            if (ymax > ymin) {
                return true;
            }
        }
        return false;
    }
}
