package battleship;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.HashMap;

public enum Square {
    SQUARE0,
    SQUARE1,
    SQUARE2,
    SQUARE3,
    SQUARE4;

    private static HashMap<Square, Image> squares = new HashMap<Square, Image>();

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
