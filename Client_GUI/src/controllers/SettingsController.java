package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import config.ClientConfig;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import models.logic.Config;
import utils.ClientUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private JFXTextField portTextField;
    @FXML
    private JFXTextField hostTextField;
    @FXML
    private JFXButton saveBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void handleSave(Event event){
        try {
            ClientConfig.getInstance().setHostName(hostTextField.getText());
            ClientConfig.getInstance().setPort(Integer.parseInt(portTextField.getText()));
            ClientUtil.showSuccessMessage("Settings successfully updated");
            close(event);

        }
        catch (Throwable e){
            if (e.getCause() != null){
                ClientUtil.showErrorMessage("Problem saving settings",e.getCause().getMessage());
            }
            else {
                ClientUtil.showErrorMessage("Problem saving settings",e.getMessage());
            }
        }


    }
    public void inflateUI(Config config) {
        hostTextField.setText(config.getHostName());
        portTextField.setText(config.getPort().toString());
    }
    public void close(Event event){
        ((Node) event.getSource()).getScene().getWindow().hide();

    }
}
