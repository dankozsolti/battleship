package battleship.state;

import lombok.*;

/**
 * Class for the core Ship class.
 */

@Data
@AllArgsConstructor
public class Ship {

    private int size;
    private int direction;

    private int x;
    private int y;

    /**
     * Gets the height of the ship.
     * @param isSpace decides whether we need the space between ships(while placing)
     *                or not(while guessing).
     * @return the height of the ship
     */
    private int getHeight(boolean isSpace) {
        if (this.direction == 1)
            return this.size + (isSpace ? 1 : 0);
        return 1 + (isSpace ? 1 : 0);
    }

    /**
     * Gets the width of the ship.
     * @param isSpace decides whether we need the space between ships(while placing)
     *                or not(while guessing).
     * @return the height of the ship
     */
    private int getWidth(boolean isSpace) {
        if (this.direction == 1)
            return 1 + (isSpace ? 1 : 0);
        return this.size + (isSpace ? 1 : 0);
    }

    /**
     * Checks whether another ship is overlapping.
     * @param other the other ship.
     * @param isSpace includes/excludes spacing between ships.
     * @return true or false, depending on the overlapping.
     */
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
