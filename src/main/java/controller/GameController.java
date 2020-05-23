package controller;

import battleship.Ship;
import battleship.Square;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
//import battleship.results.GameResult;
//import battleship.results.GameResultDao;
//import battleship.state.RollingCubesState;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class GameController {

    private String userName1;
    private String userName2;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel2;

    @FXML
    private GridPane ownGrid;

    private List<Ship> ownShips;
    private HashMap<Square, Image> squares;

    @FXML
    public void initialize() {
        ownShips = new ArrayList<Ship>();
        squares = new HashMap<Square, Image>();

        squares.put(Square.SQUARE0, new Image(this.getClass().getResource("/img/square0.png").toExternalForm()));
        squares.put(Square.SQUARE1, new Image(this.getClass().getResource("/img/square1.png").toExternalForm()));
        squares.put(Square.SQUARE2, new Image(this.getClass().getResource("/img/square2.png").toExternalForm()));
        squares.put(Square.SQUARE3, new Image(this.getClass().getResource("/img/square3.png").toExternalForm()));
    }

    public void initdata(String userName1, String userName2) {
        this.userName1 = userName1;
        this.userName2 = userName2;
        usernameLabel1.setText(this.userName1);
        usernameLabel2.setText(this.userName2);
    }

    private void drawOwnShips() {
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

    public void squareClickOwn(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());
        System.out.println("own grid, (" + clickedRow + "," + clickedColumn + ")");

        Ship ship = new Ship(3, 2,clickedRow, clickedColumn );
        ownShips.add(ship);

        drawOwnShips();
    }

    public void squareClickEnemy(MouseEvent mouseEvent) {

        int clickedColumn = GridPane.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = GridPane.getRowIndex((Node)mouseEvent.getSource());

        System.out.println("enemy grid, (" + clickedRow + "," + clickedColumn + ")");

    }
}
