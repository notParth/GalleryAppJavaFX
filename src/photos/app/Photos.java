package photos.app;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.view.loginViewController;

import static javafx.application.Application.launch;

public class Photos extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/photos/view/loginView.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        loginViewController listController = loader.getController();
        listController.start(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        //the closing event of the application invokes the method shutdown()
        //shutdown is used to creates and store session data
       // stage.setOnHidden(e -> listController.shutdown());
        stage.show();


    }

    public static void main(String[] args){
        launch(args);
    }



}
