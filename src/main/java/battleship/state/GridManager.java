package battleship.state;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GridManager {
    @Getter
    private List<Ship> ownShips;
    @Getter
    private List<Ship> enemyShips;

    private List<Integer> enemyhit;
    private List<Integer> enemymiss;
    private List<Integer> ownhit;
    private List<Integer> ownmiss;



    public GridManager() {
        ownShips = new ArrayList<Ship>();
        enemyShips = new ArrayList<Ship>();

        ownhit = new ArrayList<Integer>();
        ownmiss = new ArrayList<Integer>();
        enemyhit = new ArrayList<Integer>();
        enemymiss = new ArrayList<Integer>();
    }

    public void guessOwnShips(GridPane ownGrid, boolean clear) {
        for (int i = 0 ; i < 10; i++) {
            for (int j = 0 ; j < 10; j++) {
                int index = i * 10 + j;
                ImageView view = (ImageView) ownGrid.getChildren().get(index);

                if (clear) {
                    view.setImage(Square.image(Square.SQUARE0));
                } else {
                    if (ownmiss.contains(index)) {
                        view.setImage(Square.image(Square.SQUARE1));
                    } else if (ownhit.contains(index)) {
                        view.setImage(Square.image(Square.SQUARE3));
                    }
                }
            }
        }

        if (!clear) {
            for (Ship s : ownShips) {
                boolean allDestroyed = true;
                int shipindex = s.getX() * 10 + s.getY();
                for (int i = 0; i < s.getSize(); i++) {
                    int thisIndex = shipindex;
                    if (s.getDirection() == 1) {
                        thisIndex += i;
                    } else {
                        thisIndex += i * 10;
                    }
                    if (!ownhit.contains(thisIndex)) {
                        allDestroyed = false;
                        break;
                    }
                }
                if (allDestroyed) {
                    for (int i = 0; i < s.getSize(); i++) {
                        int thisIndex = shipindex;
                        if (s.getDirection() == 1) {
                            thisIndex += i;
                        } else {
                            thisIndex += i * 10;
                        }
                        ImageView view2 = (ImageView) ownGrid.getChildren().get(thisIndex);
                        view2.setImage(Square.image(Square.SQUARE4));
                    }
                }
            }
        }
    }

    public void guessEnemyShips(GridPane enemyGrid, boolean clear) {
        for (int i = 0 ; i < 10; i++) {
            for (int j = 0 ; j < 10; j++) {
                int index = i * 10 + j;
                ImageView view = (ImageView) enemyGrid.getChildren().get(index);

                if (clear) {
                    view.setImage(Square.image(Square.SQUARE0));
                } else {
                    if (enemymiss.contains(index)) {
                        view.setImage(Square.image(Square.SQUARE1));
                    } else if (enemyhit.contains(index)) {
                        view.setImage(Square.image(Square.SQUARE3));
                    }
                }
            }
        }

        if (!clear) {
            for (Ship s : enemyShips) {
                boolean allDestroyed = true;
                int shipindex = s.getX() * 10 + s.getY();
                for (int i = 0; i < s.getSize(); i++) {
                    int thisIndex = shipindex;
                    if (s.getDirection() == 1) {
                        thisIndex += i;
                    } else {
                        thisIndex += i * 10;
                    }
                    if (!enemyhit.contains(thisIndex)) {
                        allDestroyed = false;
                        break;
                    }
                }
                if (allDestroyed) {
                    for (int i = 0; i < s.getSize(); i++) {
                        int thisIndex = shipindex;
                        if (s.getDirection() == 1) {
                            thisIndex += i;
                        } else {
                            thisIndex += i * 10;
                        }
                        ImageView view2 = (ImageView) enemyGrid.getChildren().get(thisIndex);
                        view2.setImage(Square.image(Square.SQUARE4));
                    }
                }
            }
        }
    }

    public boolean isEmptySpace(List<Ship> ships, Ship ship, boolean vertical, boolean isSpace) {
        if (vertical) {
            if (10 - ship.getY() < ship.getSize()) {
                log.warn("Ship cant fit..");
                return false;
            }
        } else {
            if (10 - ship.getX() < ship.getSize()) {
                log.warn("Ship cant fit..");
                return false;
            }
        }

        for (Ship s : ships) {
            if (s.inShip(ship, isSpace))
                return false;
        }
        return true;
    }

    public boolean isHitOwn(int index) {
        return ownhit.contains(index);
    }

    public void addOwnHit(int index) {
        if (!isHitOwn(index))
            ownhit.add(index);
    }

    public boolean isMissOwn(int index) {
        return ownmiss.contains(index);
    }

    public void addOwnMiss(int index) {
        if (!isMissOwn(index))
            ownmiss.add(index);
    }

    public boolean isHitEnemy(int index) {
        return enemyhit.contains(index);
    }

    public void addEnemyHit(int index) {
        if (!isHitEnemy(index))
            enemyhit.add(index);
    }

    public boolean isMissEnemy(int index) {
        return enemymiss.contains(index);
    }

    public void addEnemyMiss(int index) {
        if (!isMissEnemy(index))
            enemymiss.add(index);
    }

    public boolean isSolveOwn() {
        int maxS = 0;

        for (Ship s : ownShips)
            maxS += s.getSize();

        return maxS == ownhit.size();
    }

    public boolean isSolveEnemy() {
        int maxS = 0;

        for (Ship s : enemyShips)
            maxS += s.getSize();

        return maxS == enemyhit.size();
    }

    public int getMisses() {
        if(isSolveEnemy()){
            return enemymiss.size();
        } else if(isSolveOwn()){
            return ownmiss.size();
        }
        return getMisses();
    }
}
