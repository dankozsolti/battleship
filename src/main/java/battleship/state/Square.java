package battleship.state;

import javafx.scene.image.Image;

import java.util.HashMap;
/**
 * Direction variables.
 */
public enum Square {
    /**
     * Empty square.
     */
    SQUARE0,
    /**
     * Empty square, missed while guessing
     */
    SQUARE1,
    /**
     * Ship square
     */
    SQUARE2,
    /**
     * Ship square, hit while guessing
     */
    SQUARE3,
    /**
     * Fully hit ship
     */
    SQUARE4;

    private static HashMap<Square, Image> squares = new HashMap<Square, Image>();

    /**
     * Puts the image to its proper square.
     * @param sq the square to put the image.
     * @return the squares.
     */
    public static Image image(Square sq) {
        if (squares.isEmpty()) {
            squares.put(Square.SQUARE0, new Image(Square.class.getResource("/img/square0.png").toExternalForm()));
            squares.put(Square.SQUARE1, new Image(Square.class.getResource("/img/square1.png").toExternalForm()));
            squares.put(Square.SQUARE2, new Image(Square.class.getResource("/img/square2.png").toExternalForm()));
            squares.put(Square.SQUARE3, new Image(Square.class.getResource("/img/square3.png").toExternalForm()));
            squares.put(Square.SQUARE4, new Image(Square.class.getResource("/img/square4.png").toExternalForm()));
        }

        return squares.get(sq);
    }
}
