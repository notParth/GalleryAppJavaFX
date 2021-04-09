package photos.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.model.User;

import java.util.ArrayList;

public class adminViewController {

    @FXML
    Button logout, createUser, deleteUser;
    @FXML
    TextField username;
    @FXML
    ListView<User> listView;

    public void start(Stage stage, ArrayList<User> users) throws Exception {
        stage.setTitle("Admin View");
        listView.setItems(FXCollections.observableArrayList(users));
        listView.getSelectionModel().select(0);

        createUser.setOnAction(e -> addUser(users));
        deleteUser.setOnAction(e -> delete(users));

    }

    //Create User
    public void addUser(ArrayList<User> users){
        String name = username.getText().trim();
        if (name.isEmpty()) { //no name
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: Username is blank");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to create this user.");
            errorAlert.showAndWait();
        } else{
            User newUser = new User(username.getText().trim());
            int index = newUser.findUser(users);
            if(index != -1){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERROR");
                errorAlert.setContentText("Problem: User already exists");
                errorAlert.setHeaderText("Whoops! Something went wrong when trying to create this user.");
                errorAlert.showAndWait();
            } else{
                listView.getItems().add(newUser);
                listView.getSelectionModel().select(newUser);
                users.add(newUser); //update AL
            }
        }
    }

    //Delete User
    public void delete(ArrayList<User> users){
        User deletedUser = listView.getSelectionModel().getSelectedItem();
        if(listView.getItems().size() == 0) { //if list is empty when trying to delete
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: There are no users to delete");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to delete this user.");
            errorAlert.showAndWait();
        } else{
            listView.getItems().remove(deletedUser);
            users.remove(deletedUser); //update AL
        }
    }

    //Change Scenes
    public void logout(ActionEvent e) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/photos/view/loginView.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
