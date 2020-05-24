package controller;

import battleship.Ship;
import battleship.Square;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.css.Rect;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    private List<Ship> ownShips;
    private List<Ship> enemyShips;
    private HashMap<Square, Image> squares;

    @FXML
    public void initialize() {
        ownShips = new ArrayList<Ship>();
        enemyShips = new ArrayList<Ship>();
        squares = new HashMap<Square, Image>();

        squares.put(Square.SQUARE0, new Image(this.getClass().getResource("/img/square0.png").toExternalForm()));
        squares.put(Square.SQUARE1, new Image(this.getClass().getResource("/img/square1.png").toExternalForm()));
        squares.put(Square.SQUARE2, new Image(this.getClass().getResource("/img/square2.png").toExternalForm()));
        squares.put(Square.SQUARE3, new Image(this.getClass().getResource("/img/square3.png").toExternalForm()));

        vertical.setDisable(true);
    }

    public void initdata(String userName1, String userName2) {
        usernameLabel1.setText(userName1);
        usernameLabel2.setText(userName2);
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

    private boolean isEmptySpace(List<Ship> ships, Ship ship) {
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
            if (s.inShip(ship))
                return false;
        }
        return true;
    }


    public void squareClickOwn(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());
        System.out.println("own grid, (" + clickedRow + "," + clickedColumn + ")");

        Ship ship = new Ship(3, horizontal.isDisable() ? 1 : 2,clickedRow, clickedColumn);
        ownShips.add(ship);

        drawOwnShips();
    }

    public void squareClickEnemy(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        System.out.println("enemy grid, (" + clickedRow + "," + clickedColumn + ")");

        int size = 3;
        Ship ship = new Ship(size, horizontal.isDisable() ? 1 : 2, clickedRow, clickedColumn);
        if (!isEmptySpace(enemyShips, ship)) {
            log.warn("Ship in line!!");
            return;
        }
        enemyShips.add(ship);

        drawEnemyShips();
    }

    public void squareHoverOwn(){
        System.out.println("Hovered own");
    }

    public void squareHoverEnemy(){
        System.out.println("Hovered enemy");
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
