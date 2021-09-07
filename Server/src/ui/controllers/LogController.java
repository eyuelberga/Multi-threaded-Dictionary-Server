package ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.logic.Log;
import utils.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LogController implements Initializable {
    private ObservableList<LogController.LogInfo> list = FXCollections.observableArrayList();
    @FXML
    private TableView<LogController.LogInfo> tableView;
    @FXML
    private TableColumn<LogController.LogInfo, String> timeCol;
    @FXML
    private TableColumn<LogController.LogInfo, String> actionCol;
    @FXML
    private TableColumn<LogController.LogInfo, String> clientCol;
    @FXML
    private TableColumn<LogController.LogInfo, String> descriptionCol;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(list);

    }

    @FXML
    private void loadData() {
        list.clear();
        List<Log> logList = Logger.getInstance().getLogList();
        for (Log l : logList
        ) {
            list.add(new LogController.LogInfo(l.getTime(),l.getAction().toString(), l.getClient(), l.getDescription()));
        }
    }

    public static class LogInfo {
        private final SimpleStringProperty time;
        private final SimpleStringProperty action;
        private final SimpleStringProperty client;
        private final SimpleStringProperty description;

        public LogInfo(String time, String action, String client, String description) {
            this.time = new SimpleStringProperty(time);
            this.action = new SimpleStringProperty(action);
            this.client = new SimpleStringProperty(client);
            this.description = new SimpleStringProperty(description);
        }

        public String getTime() {
            return time.get();
        }
        public SimpleStringProperty timeProperty() {
            return time;
        }
        public String getAction() {
            return action.get();
        }

        public SimpleStringProperty actionProperty() {
            return action;
        }

        public String getClient() {
            return client.get();
        }

        public SimpleStringProperty clientProperty() {
            return client;
        }

        public String getDescription() {
            return description.get();
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }
    }
}
