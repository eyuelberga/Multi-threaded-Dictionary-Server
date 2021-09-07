import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class
Main extends Application {

    public static void main(String[] args)throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl  =getClass().getResource("/resources/fxml/client.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setTitle("Dictionary client");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }
}
