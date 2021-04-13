package photos.app;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.model.User;
import photos.model.serUser;
import photos.view.loginViewController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static javafx.application.Application.launch;
/**
 * entry point for photos application
 *
 * @author Parth Patel
 * @author Amanda Kang
 */
public class Photos extends Application{

    /**
     * Start method for the application
     * @param stage primary stage is passed here
     * @throws Exception
     */
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
        stage.setOnCloseRequest(e -> Platform.exit());


    }

    /**
     * stop is called when the application is closing. Its primary function is to create
     * and save serializable data
     */
    @Override
    public void stop() {
        try
        {
            ArrayList<serUser> serialized_data = new ArrayList<serUser>();
            FileOutputStream fos = new FileOutputStream("MyData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(User user : loginViewController.users) {
                serialized_data.add(new serUser(user));
            }
            oos.writeObject(serialized_data);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    public static void main(String[] args){
        launch(args);
    }



}
