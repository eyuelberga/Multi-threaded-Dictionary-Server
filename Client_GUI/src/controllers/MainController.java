package controllers;

import com.jfoenix.controls.JFXTextField;
import config.ClientConfig;
import config.Methods;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.db.DictWord;
import models.logic.Config;
import models.logic.Request;
import models.logic.Response;
import utils.ClientUtil;
import utils.SocketHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainController implements Initializable {
    private ObservableList<MainController.DictionaryWords> list = FXCollections.observableArrayList();
    @FXML
    private TableView<MainController.DictionaryWords> tableView;
    @FXML
    private TableColumn<MainController.DictionaryWords, String> wordCol;
    @FXML
    private JFXTextField searchTextField;
    @FXML
    private Text wordText;
    @FXML
    private Text serverConfigText;
    @FXML
    private Text meaningText;
    @FXML
    private VBox wordDisplayBox;
    @FXML
    private HBox wordFunctionsBox;

    private ExecutorService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.setOnKeyReleased(event->{
            if (event.getCode() == KeyCode.ENTER){
                if (searchTextField.getText().equals("")){
                    setWordVisibility(false);
                }
                else {
                search();
            }
            }
        });
       setServerConfig();
    }
    private void setServerConfig(){
        String serverConfig = ClientConfig.getInstance().getHostName()+":"+ClientConfig.getInstance().getPort().toString();
        serverConfigText.setText(serverConfig);
    }
    private void search(){
        SocketHandler socketHandler = new SocketHandler(new Request(Methods.SEARCH, new DictWord(searchTextField.getText())));
        service = Executors.newSingleThreadExecutor();
        Future<Response> f = service.submit(socketHandler);
        try {
            DictWord [] result = f.get().data;
            wordText.setText(result[0].getWord());
            meaningText.setText(result[0].getMeaning());
            setWordVisibility(true);
        }
        catch (NullPointerException e){
            ClientUtil.showInfoMessage("Word not found","The word you searched for is not in the database");
            setWordVisibility(false);
        }
        catch (Throwable e) {
            if (e.getCause() != null){
                ClientUtil.showErrorMessage("Problem sending request to server",e.getCause().getMessage());
                setWordVisibility(false);
            }
            else {
                ClientUtil.showErrorMessage("Problem sending request to server",e.getMessage());
                setWordVisibility(false);
            }
        }

    }
    private void initCol() {
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));
        tableView.setItems(list);
    }
    private void setWordVisibility(Boolean visible){
        wordDisplayBox.setVisible(visible);
        wordFunctionsBox.setVisible(visible);
    }
    @FXML
    private void addWord(){
        ClientUtil.loadWindow(getClass().getResource("/resources/fxml/add.fxml"), "Add New Word", null);
    }
    @FXML
    private void config(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/setting.fxml"));
            Parent parent = loader.load();

            SettingsController controller = (SettingsController) loader.getController();
            controller.inflateUI(new Config(ClientConfig.getInstance().getHostName(),ClientConfig.getInstance().getPort()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Configure server");
            stage.setScene(new Scene(parent));
            stage.show();
            ClientUtil.setStageIcon(stage);

            stage.setOnHiding((e) -> {
                handleServerConfigRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            ClientUtil.showErrorMessage(ex.getMessage());
        }
    }
    public void deleteWord(){
        ButtonType result =ClientUtil.showConfirmationMessage("Delete word","Are you sure you want to delete this word?").get();
       if (result == ButtonType.OK){
            delete();
       }
    }
    public void updateWord(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/add.fxml"));
            Parent parent = loader.load();

            AddUpdateController controller = (AddUpdateController) loader.getController();
            controller.inflateUI(new DictWord(wordText.getText(),meaningText.getText()));

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Word");
            stage.setScene(new Scene(parent));
            stage.show();
            ClientUtil.setStageIcon(stage);

            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    @FXML
    private void handleRefresh(ActionEvent event) {
        search();
    }
    private void handleServerConfigRefresh(ActionEvent event) {
        setServerConfig();
    }
    @FXML
    private void clearFields() {
        searchTextField.setText("");
    }

    private void delete(){
        SocketHandler socketHandler = new SocketHandler(new Request(Methods.REMOVE, new DictWord(wordText.getText())));
        service = Executors.newSingleThreadExecutor();
        Future<Response> f = service.submit(socketHandler);
        try {
            ClientUtil.resolveResponse(f.get());
            setWordVisibility(false);
            clearFields();
        }
        catch (Throwable e) {
            if (e.getCause() != null){
                ClientUtil.showErrorMessage("Problem sending request to server",e.getCause().getMessage());
            }
            else {
                ClientUtil.showErrorMessage("Problem sending request to server",e.getMessage());
            }
        }
    }

    @FXML
    private void loadData() {

        list.clear();
        SocketHandler socketHandler = new SocketHandler(new Request(Methods.ALL,null));
        service = Executors.newSingleThreadExecutor();
        Future<Response> f = service.submit(socketHandler);
        try {

            DictWord [] logList = f.get().data;
            System.out.println(logList);
            for (DictWord l : logList
            ) {
                list.add(new MainController.DictionaryWords(l.getWord()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public static class DictionaryWords {
        private final SimpleStringProperty word;

        public DictionaryWords(String word) {
            this.word = new SimpleStringProperty(word);
        }

        public String getWord() {
            return word.get();
        }

        public SimpleStringProperty wordProperty() {
            return word;
        }

    }
}
