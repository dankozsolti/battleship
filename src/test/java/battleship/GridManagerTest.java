package battleship;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridManagerTest {
    @Test
    void testIsHitOwn() {
        GridManager gridManager = new GridManager();

        assertFalse(gridManager.isHitOwn(2));
        gridManager.addOwnHit(2);
        assertTrue(gridManager.isHitOwn(2));
        assertFalse(gridManager.isHitOwn(3));
    }

    @Test
    void testIsHitEnemy() {
        GridManager gridManager = new GridManager();

        assertFalse(gridManager.isHitEnemy(6));
        gridManager.addEnemyHit(6);
        assertTrue(gridManager.isHitEnemy(6));
        assertFalse(gridManager.isHitEnemy(8));
    }

    @Test
    void testGuessEnemyShips() {
        PlatformImpl.startup(() -> {});

        GridManager gridManager = new GridManager();

        gridManager.guessOwnShips(null, true);
        gridManager.guessOwnShips(null, false);

        gridManager.addEnemyHit(3);
        gridManager.addEnemyMiss(4);
        GridPane enemyGrid = new GridPane();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                enemyGrid.add(new ImageView(), i, j);
        ImageView first = ((ImageView)enemyGrid.getChildren().get(0));
        first.setImage(Square.image(Square.SQUARE1));

        assertEquals(Square.image(Square.SQUARE1), first.getImage());
        gridManager.guessEnemyShips(enemyGrid, true);
        assertEquals(Square.image(Square.SQUARE0), first.getImage());

        first.setImage(Square.image(Square.SQUARE1));
        gridManager.guessEnemyShips(enemyGrid, false);
        assertEquals(Square.image(Square.SQUARE1), first.getImage());
    }

    @Test
    void testGuessOwnShips() {
        PlatformImpl.startup(() -> {});

        GridManager gridManager = new GridManager();

        gridManager.guessEnemyShips(null, true);
        gridManager.guessEnemyShips(null, false);

        gridManager.addOwnHit(3);
        gridManager.addOwnMiss(4);
        GridPane ownGrid = new GridPane();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                ownGrid.add(new ImageView(), i, j);
        ImageView first = ((ImageView)ownGrid.getChildren().get(0));
        first.setImage(Square.image(Square.SQUARE1));

        assertEquals(Square.image(Square.SQUARE1), first.getImage());
        gridManager.guessEnemyShips(ownGrid, true);
        assertEquals(Square.image(Square.SQUARE0), first.getImage());

        first.setImage(Square.image(Square.SQUARE1));
        gridManager.guessEnemyShips(ownGrid, false);
        assertEquals(Square.image(Square.SQUARE1), first.getImage());
    }
}
