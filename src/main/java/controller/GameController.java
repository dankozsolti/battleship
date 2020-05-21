package controller;

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
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GameController {

    private String userName;
    private int stepCount;
    private List<Image> squareImages;
    private Instant beginGame;

    @FXML
    private Label usernameLabel;

    @FXML
    private GridPane ownGrid;

    @FXML
    private GridPane enemyGrid;

    @FXML
    private Label stepLabel;

    @FXML
    private Label solvedLabel;

    @FXML
    private Button doneButton;

    public void initdata(String userName) {
        this.userName = userName;
        usernameLabel.setText("Current user: " + this.userName);
    }

    public void squareClickOwn(MouseEvent mouseEvent) {


        int clickedColumn = ownGrid.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = ownGrid.getRowIndex((Node)mouseEvent.getSource());
        System.out.println("own grid, (" + clickedColumn + "," + clickedRow + ")");



    }

    public void squareClickEnemy(MouseEvent mouseEvent) {


        int clickedColumn = enemyGrid.getColumnIndex((Node)mouseEvent.getSource());
        int clickedRow = enemyGrid.getRowIndex((Node)mouseEvent.getSource());

        System.out.println("enemy grid, (" + clickedColumn + "," + clickedRow + ")");



    }
}
