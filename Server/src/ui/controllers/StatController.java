package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import utils.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class StatController implements Initializable {
    @FXML
    private Text totalRequests;
    @FXML
    private Text connectionLimit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
    public void initialize() {
        connectionLimit.setText(Logger.getInstance().getConnectionLimit());
        totalRequests.setText(Logger.getInstance().getTotalRequests().toString());
    }
}
