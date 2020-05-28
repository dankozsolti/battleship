package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LaunchController {

    @FXML
    private TextField username1Textfield;

    @FXML
    private TextField username2Textfield;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (username1Textfield.getText().isEmpty()) {
            errorLabel.setText("* Username1 is empty!");
        } else if(username2Textfield.getText().isEmpty()) {
            errorLabel.setText("* Username2 is empty!");
        } else if(username1Textfield.getText().equalsIgnoreCase(username2Textfield.getText())){
            errorLabel.setText("* Usernames must not match!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().initdata(username1Textfield.getText(),username2Textfield.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            log.info("Usernames set to {} and {}, loading game scene.", username1Textfield.getText(), username2Textfield.getText());
        }
    }
}

