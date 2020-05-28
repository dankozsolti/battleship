package controller;

import battleship.results.GameResults;
import battleship.results.GameResultsDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;


import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Slf4j
/**
 * Controller class for result screen.
 */
public class TopTenController {

    @FXML
    private TableView<GameResults> toptenTable;

    @FXML
    private TableColumn<GameResults, String> player;

    @FXML
    private TableColumn<GameResults, String> otherPlayer;

    @FXML
    private TableColumn<GameResults, String> winnerPlayer;

    @FXML
    private TableColumn<GameResults, Integer> misses;

    @FXML
    private TableColumn<GameResults, Duration> duration;

    @FXML
    private TableColumn<GameResults, ZonedDateTime> created;

    private GameResultsDao gameResultsDao;

    /**
     * Go back to the main menu.
     * @param actionEvent Event of the action
     * @throws IOException Error if file not found
     */
    public void back(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading launch scene.");
    }

    /**
     * Initialize all the variables and store them in a property for display.
     */
    @FXML
    public void initialize() {
        gameResultsDao = GameResultsDao.getInstance();

        List<GameResults> toptenList = gameResultsDao.findBest(10);

        //Set cell informations from GameResults class
        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        otherPlayer.setCellValueFactory(new PropertyValueFactory<>("otherPlayer"));
        winnerPlayer.setCellValueFactory(new PropertyValueFactory<>("winnerPlayer"));
        misses.setCellValueFactory(new PropertyValueFactory<>("misses"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));


        duration.setCellFactory(column -> {
            TableCell<GameResults, Duration> cell = new TableCell<GameResults, Duration>() {

                @Override
                protected void updateItem(Duration item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(DurationFormatUtils.formatDuration(item.toMillis(),"H:mm:ss"));
                    }
                }
            };

            return cell;
        });

        created.setCellFactory(column -> {
            TableCell<GameResults, ZonedDateTime> cell = new TableCell<GameResults, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);

                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        ObservableList<GameResults> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(toptenList);

        toptenTable.setItems(observableResult);
    }

}
