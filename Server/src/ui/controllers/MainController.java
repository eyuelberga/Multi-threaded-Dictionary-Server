package ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import server.Server;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainController implements Initializable {
    private Server server;
    private ExecutorService service;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private HBox IPLabel;
    @FXML
    private HBox portLabel;
    @FXML
    private HBox machineLabel;
    @FXML
    private HBox portBox;
    @FXML
    private HBox threadPoolBox;
    @FXML
    private Text serverPort;
    @FXML
    private Text serverIP;
    @FXML
    private Text serverMachine;
    @FXML
    private JFXButton btnStart;
    @FXML
    private JFXButton btnStop;
    @FXML
    private ImageView imageUp;
    @FXML
    private ImageView imageDown;
    @FXML
    private Text timer;
    private Timeline timeline;
    private Integer timeSeconds;
    @FXML
    private JFXTextField portInput;
    @FXML
    private JFXTextField threadPoolInput;

    @FXML
    public void initialize() {
        init();
        btnStart.managedProperty().bind(btnStart.visibleProperty());
        btnStop.managedProperty().bind(btnStop.visibleProperty());
        imageUp.managedProperty().bind(imageUp.visibleProperty());
        imageDown.managedProperty().bind(imageDown.visibleProperty());
        IPLabel.managedProperty().bind(IPLabel.visibleProperty());
        portLabel.managedProperty().bind(portLabel.visibleProperty());
        machineLabel.managedProperty().bind(portLabel.visibleProperty());
        portBox.managedProperty().bind(portBox.visibleProperty());
        threadPoolBox.managedProperty().bind(threadPoolBox.visibleProperty());
    }

    private void init() {
        serverPort.setText("");
        serverMachine.setText("");
        serverIP.setText("");
        threadPoolInput.setText(Config.THREAD_POOL_SIZE.toString());
        portInput.setText(Config.PORT.toString());
    }

    @FXML
    public void startServer() {
        try {
            server = new Server(Integer.parseInt(portInput.getText()), Integer.parseInt(threadPoolInput.getText()));
            service = Executors.newSingleThreadExecutor();
            service.execute(server);
            serverPort.setText(server.getPort());
            serverIP.setText(server.getIP());
            serverMachine.setText(server.getMachine());
            toggleServerButtons(true);
            handleTimer();
        } catch (Throwable e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setHeaderText("Could not start server");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void stopServer() {
        try {
            server.stop();
            init();
            toggleServerButtons(false);
        } catch (Throwable e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setHeaderText("Could not stop server");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            service.shutdown();
        }

    }

    public void handleTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 0;
        timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), (EventHandler) event -> {
                    timeSeconds++;
                    timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                }
                ));
        timeline.playFromStart();
    }

    private void toggleServerButtons(Boolean start) {
        btnStop.setVisible(start);
        btnStart.setVisible(!start);
        imageUp.setVisible(start);
        imageDown.setVisible(!start);
        setLabelVisibility(start);

    }

    private void setLabelVisibility(Boolean visibility) {
        IPLabel.setVisible(visibility);
        portLabel.setVisible(visibility);
        machineLabel.setVisible(visibility);
        timer.setVisible(visibility);
        portBox.setVisible(!visibility);
        threadPoolBox.setVisible(!visibility);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
