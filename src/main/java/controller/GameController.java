package controller;

import battleship.Ship;
import battleship.Square;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class GameController {

    @FXML
    private Label usernameLabel1;

    @FXML
    private Label usernameLabel2;

    @FXML
    private GridPane ownGrid;

    @FXML
    private GridPane enemyGrid;

    @FXML
    private ToggleButton vertical;

    @FXML
    private ToggleButton horizontal;

    @FXML
    private Button endPlacementButton;

    @FXML
    private Button startOwnButton;

    @FXML
    private Button startEnemyButton;

    @FXML
    private Button endOwnButton;

    @FXML
    private Button endEnemyButton;


    private List<Ship> ownShips;
    private List<Ship> enemyShips;
    private HashMap<Square, Image> squares;
    private List<Integer> enemyhit;
    private List<Integer> enemymiss;
    private List<Integer> ownhit;
    private List<Integer> ownmiss;

    @FXML
    public void initialize() {
        ownShips = new ArrayList<Ship>();
        enemyShips = new ArrayList<Ship>();
        squares = new HashMap<Square, Image>();
        ownhit = new ArrayList<Integer>();
        ownmiss = new ArrayList<Integer>();
        enemyhit = new ArrayList<Integer>();
        enemymiss = new ArrayList<Integer>();

        squares.put(Square.SQUARE0, new Image(this.getClass().getResource("/img/square0.png").toExternalForm()));
        squares.put(Square.SQUARE1, new Image(this.getClass().getResource("/img/square1.png").toExternalForm()));
        squares.put(Square.SQUARE2, new Image(this.getClass().getResource("/img/square2.png").toExternalForm()));
        squares.put(Square.SQUARE3, new Image(this.getClass().getResource("/img/square3.png").toExternalForm()));
        squares.put(Square.SQUARE4, new Image(this.getClass().getResource("/img/square4.png").toExternalForm()));

        vertical.setDisable(true);
    }

    public void initdata(String userName1, String userName2) {
        usernameLabel1.setText(userName1);
        usernameLabel2.setText(userName2);
    }

    private void guessOwnShips(boolean clear) {
        for (int i = 0 ; i < 10; i++) {
            for (int j = 0 ; j < 10; j++) {
                int index = i * 10 + j;

                ImageView view = (ImageView) ownGrid.getChildren().get(index);

                if (clear) {
                    view.setImage(squares.get(Square.SQUARE0));
                } else {
                    if (ownmiss.contains(index)) {
                        view.setImage(squares.get(Square.SQUARE1));
                    } else if (ownhit.contains(index)) {
                        view.setImage(squares.get(Square.SQUARE3));
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
                        view2.setImage(squares.get(Square.SQUARE4));
                    }
                }
            }
        }
    }

    private void guessEnemyShips(boolean clear) {
        for (int i = 0 ; i < 10; i++) {
            for (int j = 0 ; j < 10; j++) {
                int index = i * 10 + j;

                ImageView view = (ImageView) enemyGrid.getChildren().get(index);

                if (clear) {
                    view.setImage(squares.get(Square.SQUARE0));
                } else {
                    if (enemymiss.contains(index)) {
                        view.setImage(squares.get(Square.SQUARE1));
                    } else if (enemyhit.contains(index)) {
                        view.setImage(squares.get(Square.SQUARE3));
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
                        view2.setImage(squares.get(Square.SQUARE4));
                    }
                }
            }
        }
    }

    private void drawOwnShips(){
        for (Ship s : ownShips) {
            int startX = s.getX() * 10 + s.getY();
            for (int x = 0; x < s.getSize(); x++) {

                int index = s.getDirection() == 1 ? startX + x : startX + (10 * x);
                if (index >= 100)
                    continue;

                ImageView view = (ImageView) ownGrid.getChildren().get(index);
                view.setImage(squares.get(Square.SQUARE2));

            }
        }
    }

    private void drawEnemyShips(){
        for (Ship s : enemyShips) {
            int startX = s.getX() * 10 + s.getY();
            for (int x = 0; x < s.getSize(); x++) {
                int index = s.getDirection() == 1 ? startX + x : startX + (10 * x);
                if (index >= 100)
                    continue;

                ImageView view = (ImageView) enemyGrid.getChildren().get(index);
                view.setImage(squares.get(Square.SQUARE2));
            }
        }
    }

    private boolean isEmptySpace(List<Ship> ships, Ship ship, boolean isSpace) {
        if (horizontal.isDisable()) {
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

    int stage = 0;
    boolean prepPhase = true;

    public void endPlacementTurn() {
        if(stage == 10) {
            ownGrid.setVisible(false);
            stage++;
            log.info("Stage: " + stage);
        }else if (stage == 21) {
            enemyGrid.setVisible(false);
            ownGrid.setVisible(false);
            stage++;
            endPlacementButton.setVisible(false);
            horizontal.setVisible(false);
            vertical.setVisible(false);
            log.info("Stage:" + stage);
            prepPhase = false;
            startOwnButton.setVisible(true);
        }
        endPlacementButton.setDisable(true);
    }

    public void squareClickOwn(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        if(stage>=22 && !prepPhase){
            if (stage % 2 == 1 || (!endEnemyButton.isDisable() && endEnemyButton.isVisible())) {
                log.warn("NEM TE JÖSSZ TE FASSZOPÓ");
                return;
            }

            endEnemyButton.setVisible(true);
            endEnemyButton.setDisable(true);

            Ship fire = new Ship(1, 1, clickedRow, clickedColumn);
            int index = clickedRow * 10 + clickedColumn;
            if (ownhit.contains(index) || ownmiss.contains(index)) {
                log.warn("You already hit that!");
                return;
            }

            if (!isEmptySpace(ownShips, fire, false)) {
                log.info("Hit!");
                ownhit.add(index);
                guessOwnShips(false);
            } else{
                log.info("Miss.");
                ownmiss.add(index);
                System.out.println(ownmiss);
                ImageView view = (ImageView) ownGrid.getChildren().get(index);
                view.setImage(squares.get(Square.SQUARE1));
                endEnemyButton.setDisable(false);
                stage++;
                log.info("Stage:{} ", stage);
            }

            int maxS = 0;
            for (Ship s : ownShips) {
                maxS += s.getSize();
            }
            if (maxS == ownhit.size()) {
                log.info("You sank the ship!");
            }
            return;
        }

        int size = 0;
        if(prepPhase) {
            if (stage == 0) {
                size = 4;
            } else if (stage == 1 || stage == 2) {
                size = 3;
            } else if (stage >= 3 && stage <= 5) {
                size = 2;
            } else if (stage >= 6 && stage <= 9) {
                size = 1;
            } else {
                log.warn("Can't place any more!");
                return;
            }
        }

        Ship ship = new Ship(size, horizontal.isDisable() ? 1 : 2, clickedRow, clickedColumn);

        if (prepPhase) {
            if (!isEmptySpace(ownShips, ship, true)) {
                log.warn("Ship in line!!");
                return;
            }

            ownShips.add(ship);
            drawOwnShips();
            stage++;
            log.info("Stage: " + stage);
            if (stage == 10) {
                endPlacementButton.setDisable(false);
            }

        }
    }

    public void squareClickEnemy(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        if(stage>=22 && !prepPhase){
            if (stage % 2 == 0 || (!endOwnButton.isDisable() && endOwnButton.isVisible())) {
                log.warn("NEM TE JÖSSZ TE FASSZOPÓ");
                return;
            }

            endOwnButton.setVisible(true);
            endOwnButton.setDisable(true);

            Ship fire = new Ship(1, 1, clickedRow, clickedColumn);
            int index = clickedRow * 10 + clickedColumn;
            if (enemyhit.contains(index) || enemymiss.contains(index)) {
                log.warn("You already hit that!");
                return;
            }

            if (!isEmptySpace(enemyShips, fire, false)) {
                log.info("Hit!");
                enemyhit.add(index);
                guessEnemyShips(false);
            } else{
                log.info("Miss.");
                enemymiss.add(index);
                System.out.println(enemymiss);
                ImageView view = (ImageView) enemyGrid.getChildren().get(index);
                view.setImage(squares.get(Square.SQUARE1));
                endOwnButton.setDisable(false);
                log.info("Stage:{} ", stage);
            }

            int maxS = 0;
            for (Ship s : enemyShips) {
                maxS += s.getSize();
            }
            if (maxS == enemyhit.size()) {
                log.info("You sank the ship!");
            }
            return;
        }

        int size = 0;
        if (prepPhase) {
            if (stage < 11) {
                log.warn("It's not your turn yet!");
                return;
            }
            if (stage == 11) {
                size = 4;
            } else if (stage == 12 || stage == 13) {
                size = 3;
            } else if (stage >= 14 && stage <= 16) {
                size = 2;
            } else if (stage >= 17 && stage <= 20) {
                size = 1;
            } else {
                log.info("Can't place any more!");
                return;
            }
        }

        //log.info("enemy grid, (" + clickedRow + "," + clickedColumn + ")");
        Ship ship = new Ship(size, horizontal.isDisable() ? 1 : 2, clickedRow, clickedColumn);

        if (prepPhase) {
            if (!isEmptySpace(enemyShips, ship, true)) {
                log.warn("Ship in line!!");
                return;
            }

            enemyShips.add(ship);
            drawEnemyShips();
            stage++;
            log.info("Stage: " + stage);
            if (stage == 21) {
                endPlacementButton.setDisable(false);
            }

        }
    }

    public void startOwn(){
        drawOwnShips();
        guessOwnShips(false);
        guessEnemyShips(true);
        guessEnemyShips(false);
        ownGrid.setVisible(true);
        enemyGrid.setVisible(true);
        startOwnButton.setVisible(false);
        stage++;
    }

    public void startEnemy(){
        drawEnemyShips();
        guessEnemyShips(false);
        guessOwnShips(true);
        guessOwnShips(false);
        enemyGrid.setVisible(true);
        ownGrid.setVisible(true);
        startEnemyButton.setVisible(false);
        stage++;
    }

    public void endOwn(){
        startEnemyButton.setVisible(true);
        guessOwnShips(true);
        endOwnButton.setVisible(false);
        ownGrid.setVisible(false);
        enemyGrid.setVisible(false);
    }

    public void endEnemy(){
        startOwnButton.setVisible(true);
        guessEnemyShips(true);
        endEnemyButton.setVisible(false);
        enemyGrid.setVisible(false);
        ownGrid.setVisible(false);
    }

    public void horizontalRelease() {

        vertical.setDisable(false);
        horizontal.setDisable(true);
        vertical.setSelected(false);
    }

    public void verticalRelease(){

        horizontal.setDisable(false);
        vertical.setDisable(true);
        horizontal.setSelected(false);
    }
}