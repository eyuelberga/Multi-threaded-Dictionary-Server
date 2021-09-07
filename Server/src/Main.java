import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl  =getClass().getResource("/ui/fxml/server.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setTitle("Dictionary server");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }
}
