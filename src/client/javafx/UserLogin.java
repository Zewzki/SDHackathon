package client.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserLogin extends Application {
    Scene userLoginScene, userRegisterScene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UserLoginFXML.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
    }
}
