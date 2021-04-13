package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.User;
import photos.model.serUser;

import java.io.FileInputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * controller for handling logging in event. It is also the primary point of entry.
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class loginViewController {

    @FXML
    Button login;
    @FXML
    TextField username;

    private final String path = "data/data.dat";
    public static ArrayList<User> users;

    /**
     * start method of login view
     * @param stage
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {
        stage.setTitle("Photos Application");
        users = new ArrayList<User>();

        ArrayList<serUser> ser_users = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream("MyData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ser_users = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }


        for (serUser u : ser_users) {
            users.add(new User(u));
        }
    }

    //Changing Scenes (Login)

    /**
     * handles the transition from login view to a different view (depends on the user)
     * @param e
     * @throws Exception
     */
    public void login(ActionEvent e) throws Exception{
        Stage stage = (Stage) login.getScene().getWindow();
        if (username.getText().trim().equals("admin")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/adminView.fxml"));
            Parent root = (Parent) loader.load();
            adminViewController controller = loader.<adminViewController>getController();
            controller.start(stage, users);
            stage.setScene(new Scene(root));
        } else{
            User loginUser = new User(username.getText().trim());
            int i = loginUser.findUser(users);
            if(i!=-1){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
                Parent root = (Parent) loader.load();
                albumViewController controller = loader.<albumViewController>getController();
                controller.start(stage, users.get(i), users);
                stage.setScene(new Scene(root));
            } else{ //if username incorrect
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERROR");
                errorAlert.setContentText("Problem: User does not exist");
                errorAlert.setHeaderText("Whoops! Something went wrong when trying to login.");
                errorAlert.showAndWait();
            }
        }
    }
}
