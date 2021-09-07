package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.logic.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ClientUtil {
    public static final String ICON_IMAGE_LOC = "/resources/style/add.png";
    public static void resolveResponse(Response res){
        switch (res.code) {
            case OPERATION_SUCCESSFUL:
                showSuccessMessage(res.message);
                break;

            case OPERATION_FAIL :
            case INVALID_METHOD:
                showErrorMessage("Error response form server",res.message);
                break;

            case PARSING_ERROR :
                showErrorMessage("Could not parse request");
                break;
            case SERVER_DOWN:
                showErrorMessage("Server is down");
                break;
            default:
                showErrorMessage("Unknown error occurred");
                break;
    }
}
public static void showSuccessMessage(String message){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText("Success");
    alert.setContentText(message);
    alert.showAndWait();
}
public static void showErrorMessage(String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error");
    alert.setContentText(message);
    alert.showAndWait();
}
    public static void showErrorMessage(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public   static void showInfoMessage(String header,String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static Optional<ButtonType> showConfirmationMessage(String header, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert.showAndWait();
    }
    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return controller;
    }
    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }
}
