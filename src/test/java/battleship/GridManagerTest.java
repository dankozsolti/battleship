package battleship;

import battleship.state.GridManager;
import battleship.state.Ship;
import battleship.state.Square;
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        gridManager.getEnemyShips().add(new Ship(1,2, 0, 0));
        gridManager.guessEnemyShips(enemyGrid,false);
        assertEquals(Square.image(Square.SQUARE1), first.getImage());

        gridManager.addEnemyHit(0);
        gridManager.guessEnemyShips(enemyGrid,false);
        assertEquals(Square.image(Square.SQUARE4), first.getImage());
    }

    @Test
    void testGuessOwnShips() {
        PlatformImpl.startup(() -> {});

        GridManager gridManager = new GridManager();

        gridManager.addOwnHit(3);
        gridManager.addOwnMiss(4);
        GridPane ownGrid = new GridPane();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                ownGrid.add(new ImageView(), i, j);
        ImageView first = ((ImageView)ownGrid.getChildren().get(0));
        first.setImage(Square.image(Square.SQUARE1));

        assertEquals(Square.image(Square.SQUARE1), first.getImage());
        gridManager.guessOwnShips(ownGrid, true);
        assertEquals(Square.image(Square.SQUARE0), first.getImage());

        first.setImage(Square.image(Square.SQUARE1));
        gridManager.guessOwnShips(ownGrid, false);
        assertEquals(Square.image(Square.SQUARE1), first.getImage());

        gridManager.getOwnShips().add(new Ship(1,2, 0, 0));
        gridManager.guessOwnShips(ownGrid,false);
        assertEquals(Square.image(Square.SQUARE1), first.getImage());

        gridManager.addOwnHit(0);
        gridManager.guessOwnShips(ownGrid,false);
        assertEquals(Square.image(Square.SQUARE4), first.getImage());
    }

    @Test
    void testisEmptySpace(){
        GridManager gridManager = new GridManager();

        Ship first = new Ship(1,2, 0, 0);
        assertTrue(gridManager.isEmptySpace(gridManager.getOwnShips(), first, false, true));
        assertTrue(gridManager.isEmptySpace(gridManager.getOwnShips(), first, false, false));
        assertTrue(gridManager.isEmptySpace(gridManager.getOwnShips(), first, true, true));

        gridManager.getOwnShips().add(first);
        assertFalse(gridManager.isEmptySpace(gridManager.getOwnShips(), first, false, true));
        assertFalse(gridManager.isEmptySpace(gridManager.getOwnShips(), first, false, false));
        assertFalse(gridManager.isEmptySpace(gridManager.getOwnShips(), first, true, true));
    }

    @Test
    void testIsSolveOwn() {
        GridManager gridManager = new GridManager();

        Ship first = new Ship(1,2, 0, 0);
        gridManager.getOwnShips().add(first);

        assertFalse(gridManager.isSolveOwn());
        gridManager.addOwnHit(0);
        assertTrue(gridManager.isSolveOwn());
    }

    @Test
    void testIsSolveEnemy() {
        GridManager gridManager = new GridManager();

        Ship first = new Ship(1,2, 0, 0);
        gridManager.getEnemyShips().add(first);

        assertFalse(gridManager.isSolveEnemy());
        gridManager.addEnemyHit(0);
        assertTrue(gridManager.isSolveEnemy());
    }

    @Test
    void testGetMisses(){
        GridManager gridManager = new GridManager();
        assertEquals(0, gridManager.getMisses());
        gridManager.addEnemyMiss(1);
        assertEquals(1, gridManager.getMisses());
    }
}
