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

import java.io.FileInputStream;

import java.io.File;
import java.util.ArrayList;

public class loginViewController {

    @FXML
    Button login;
    @FXML
    TextField username;

    private final String path = "data/data.dat";
    public static ArrayList<User> users;


    public void start(Stage stage) throws Exception {
        stage.setTitle("Photos Application");


        //set up
        Album stockAlbum = new Album("stock");

        for(int i=1; i<=5; i++){
            File imageFile = new File("data/image"+i+".jpg");
            Image image = new Image(new FileInputStream("data/image"+i+".jpg"));
            Photo photo = new Photo("image"+i, imageFile.lastModified(), image);
            stockAlbum.addPhoto(photo);
        }

        User stockUser = new User("stock");
        stockUser.getAlbums().add(stockAlbum);
        users = new ArrayList<User>();
        users.add(stockUser);
        //System.out.println(stockUser.getAlbums().get(0).getName());


    }

    //Changing Scenes (Login)

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
