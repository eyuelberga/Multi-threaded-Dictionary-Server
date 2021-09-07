package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import config.Methods;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import models.db.DictWord;
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

public class AddUpdateController implements Initializable {
    @FXML
    private JFXTextField wordTextField;
    @FXML
    private JFXTextArea meaningTextField;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private JFXButton saveBtn;
    private ExecutorService service;
    private Boolean isInEditMode = Boolean.FALSE;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void handleAddUpdate(Event event){
        if(isInEditMode){
            update(event);
        }
        else {
            add(event);
        }
    }

    public void add(Event event){
        if (wordTextField.getText().equals("") || meaningTextField.getText().equals("")){
            wordTextField.getStyleClass().add("wrong-credentials");
            meaningTextField.getStyleClass().add("wrong-credentials");
        }
        else {
            SocketHandler socketHandler = new SocketHandler(new Request(Methods.ADD, new DictWord(wordTextField.getText(),meaningTextField.getText())));
            service = Executors.newSingleThreadExecutor();
            Future<Response> f = service.submit(socketHandler);
            try {
               ClientUtil.resolveResponse(f.get());
               clearFields();
               close(event);
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

    }
    public void update(Event event){
        if (wordTextField.getText().equals("") || meaningTextField.getText().equals("")){
            wordTextField.getStyleClass().add("wrong-credentials");
            meaningTextField.getStyleClass().add("wrong-credentials");
        }
        else {
            SocketHandler socketHandler = new SocketHandler(new Request(Methods.UPDATE, new DictWord(wordTextField.getText(),meaningTextField.getText())));
            service = Executors.newSingleThreadExecutor();
            Future<Response> f = service.submit(socketHandler);
            try {
                ClientUtil.resolveResponse(f.get());
                clearFields();
                close(event);
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

    }
    public void clearFields(){
        wordTextField.setText("");
        meaningTextField.setText("");
    }
    public void close(Event event){
        ((Node) event.getSource()).getScene().getWindow().hide();

    }
    public void inflateUI(DictWord word) {
        wordTextField.setText(word.getWord());
        meaningTextField.setText(word.getMeaning());
        saveBtn.setText("Update");
        wordTextField.setDisable(true);
        isInEditMode = Boolean.TRUE;
    }
}
