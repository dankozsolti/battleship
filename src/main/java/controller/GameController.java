package controller;

import battleship.state.GridManager;
import battleship.state.Ship;
import battleship.state.Square;
import battleship.results.GameResults;
import battleship.results.GameResultsDao;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class GameController {

    @FXML
    private Label usernameLabel1;

    @FXML
    private Label usernameLabel2;

    @FXML
    private Label stopWatch;

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

    @FXML
    private Label finishLabel;

    @FXML
    private Button doneButton;

    private Instant startTime;
    private Timeline stopWatchTimeline;
    private GridManager gridManager;
    private GameResultsDao gameResultDao;
    private Instant beginGame;
    private boolean hasFinished;
    private String WinnerName;
    private int misses;

    @FXML
    public void initialize() {
        gameResultDao = GameResultsDao.getInstance();
        gridManager = new GridManager();

        vertical.setDisable(true);
        startTime = Instant.now();
        createStopWatch();
        beginGame = Instant.now();
    }

    public void initdata(String userName1, String userName2) {
        usernameLabel1.setText(userName1);
        usernameLabel2.setText(userName2);
    }

    private void drawOwnShips(){
        for (Ship s : gridManager.getOwnShips()) {
            int startX = s.getX() * 10 + s.getY();
            for (int x = 0; x < s.getSize(); x++) {

                int index = s.getDirection() == 1 ? startX + x : startX + (10 * x);
                if (index >= 100)
                    continue;

                ImageView view = (ImageView) ownGrid.getChildren().get(index);
                view.setImage(Square.image(Square.SQUARE2));

            }
        }
    }

    private void drawEnemyShips(){
        for (Ship s : gridManager.getEnemyShips()) {
            int startX = s.getX() * 10 + s.getY();
            for (int x = 0; x < s.getSize(); x++) {
                int index = s.getDirection() == 1 ? startX + x : startX + (10 * x);
                if (index >= 100)
                    continue;

                ImageView view = (ImageView) enemyGrid.getChildren().get(index);
                view.setImage(Square.image(Square.SQUARE2));
            }
        }
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
                log.warn("It's not your turn!");
                return;
            }

            endEnemyButton.setVisible(true);
            endEnemyButton.setDisable(true);

            Ship fire = new Ship(1, 1, clickedRow, clickedColumn);
            int index = clickedRow * 10 + clickedColumn;
            if (gridManager.isHitOwn(index) || gridManager.isMissOwn(index)) {
                log.warn("You already hit that!");
                return;
            }

            if (!gridManager.isEmptySpace(gridManager.getOwnShips(), fire, horizontal.isDisable(),false)) {
                log.info("Hit!");
                gridManager.addOwnHit(index);
                gridManager.guessOwnShips(ownGrid, false);
            } else{
                log.info("Miss.");
                gridManager.addOwnMiss(index);
                ImageView view = (ImageView) ownGrid.getChildren().get(index);
                view.setImage(Square.image(Square.SQUARE1));
                endEnemyButton.setDisable(false);
            }

            if (gridManager.isSolveOwn()) {
                log.info("{} WON!",usernameLabel2.getText());
                finishLabel.setText(usernameLabel2.getText() + " WON!");
                ownGrid.setDisable(true);
                WinnerName = usernameLabel2.getText();
                hasFinished = true;
                doneButton.setVisible(true);
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
            if (!gridManager.isEmptySpace(gridManager.getOwnShips(), ship, horizontal.isDisable(),true)) {
                log.warn("Ship in line!!");
                return;
            }

            gridManager.getOwnShips().add(ship);
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
                log.warn("It's not your turn!");
                return;
            }

            endOwnButton.setVisible(true);
            endOwnButton.setDisable(true);

            Ship fire = new Ship(1, 1, clickedRow, clickedColumn);
            int index = clickedRow * 10 + clickedColumn;
            if (gridManager.isHitEnemy(index) || gridManager.isMissEnemy(index)) {
                log.warn("You already hit that!");
                return;
            }

            if (!gridManager.isEmptySpace(gridManager.getEnemyShips(), fire, horizontal.isDisable(),false)) {
                log.info("Hit!");
                gridManager.addEnemyHit(index);
                gridManager.guessEnemyShips(enemyGrid,false);
            } else{
                log.info("Miss.");
                gridManager.addEnemyMiss(index);
                ImageView view = (ImageView) enemyGrid.getChildren().get(index);
                view.setImage(Square.image(Square.SQUARE1));
                endOwnButton.setDisable(false);
            }
            if (gridManager.isSolveEnemy()) {
                log.info("{} WON!", usernameLabel1.getText());
                finishLabel.setText(usernameLabel1.getText() + " WON!");
                enemyGrid.setDisable(true);
                WinnerName = usernameLabel1.getText();
                hasFinished = true;
                doneButton.setVisible(true);
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

        Ship ship = new Ship(size, horizontal.isDisable() ? 1 : 2, clickedRow, clickedColumn);

        if (prepPhase) {
            if (!gridManager.isEmptySpace(gridManager.getEnemyShips(), ship, horizontal.isDisable(),true)) {
                log.warn("Ship in line!!");
                return;
            }

            gridManager.getEnemyShips().add(ship);
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
        gridManager.guessOwnShips(ownGrid,false);
        gridManager.guessEnemyShips(enemyGrid,true);
        gridManager.guessEnemyShips(enemyGrid,false);
        ownGrid.setVisible(true);
        enemyGrid.setVisible(true);
        startOwnButton.setVisible(false);
        stage++;
        log.info("Stage: {}",stage);
    }

    public void startEnemy(){
        drawEnemyShips();
        gridManager.guessEnemyShips(enemyGrid,false);
        gridManager.guessOwnShips(ownGrid,true);
        gridManager.guessOwnShips(ownGrid,false);
        enemyGrid.setVisible(true);
        ownGrid.setVisible(true);
        startEnemyButton.setVisible(false);
        stage++;
        log.info("Stage: {}",stage);
    }

    public void endOwn(){
        startEnemyButton.setVisible(true);
        gridManager.guessOwnShips(ownGrid,true);
        endOwnButton.setVisible(false);
        ownGrid.setVisible(false);
        enemyGrid.setVisible(false);
    }

    public void endEnemy(){
        startOwnButton.setVisible(true);
        gridManager.guessEnemyShips(enemyGrid,true);
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

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatch.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

    private GameResults getResult() {
        GameResults result = GameResults.builder()
                .player(usernameLabel1.getText())
                .otherPlayer(usernameLabel2.getText())
                .winnerPlayer(WinnerName)
                .solved(hasFinished)
                .duration(Duration.between(beginGame, Instant.now()))
                .misses(gridManager.getMisses())
                .build();
        return result;
    }

    public void finishGame(ActionEvent actionEvent) throws IOException {
        gameResultDao.persist(getResult());

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/topten.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Finished game, loading Top Ten scene.");
    }
}