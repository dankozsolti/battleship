package battleship.state;

import lombok.*;

@Data
@AllArgsConstructor
public class Ship {

    private int size;
    private int direction;

    private int x;
    private int y;

    private int getHeight(boolean isSpace) {
        if (this.direction == 1)
            return this.size + (isSpace ? 1 : 0);
        return 1 + (isSpace ? 1 : 0);
    }

    private int getWidth(boolean isSpace) {
        if (this.direction == 1)
            return 1 + (isSpace ? 1 : 0);
        return this.size + (isSpace ? 1 : 0);
    }

    public boolean inShip(Ship other, boolean isSpace) {
        float xmin = Math.max(this.x, other.x);
        float xmax1 = this.x + this.getWidth(isSpace);
        float xmax2 = other.x + other.getWidth(isSpace);
        float xmax = Math.min(xmax1, xmax2);
        if (xmax > xmin) {
            float ymin = Math.max(this.y, other.y);
            float ymax1 = this.y + this.getHeight(isSpace);
            float ymax2 = other.y + other.getHeight(isSpace);
            float ymax = Math.min(ymax1, ymax2);
            if (ymax > ymin) {
                return true;
            }
        }
        return false;
    }
}
