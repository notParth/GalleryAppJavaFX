package photos.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.Tag;
import photos.model.User;

import java.util.ArrayList;

/**
 * controller to handle the addTag view
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class addTagViewController {
    @FXML
    ComboBox<String> tagID;
    @FXML
    TextField valueID;
    @FXML
    Button okAT, cancelAT;

    private ArrayList<User> users;
    private User user;
    private Album album;
    private Photo photo;
    public static ArrayList<String> existingTags;

    /**
     * start method
     * @param stage
     * @param user
     * @param users
     * @param album
     * @param photo
     * @throws Exception
     */
    public void start(Stage stage, User user, ArrayList<User> users, Album album, Photo photo) throws Exception {
        this.users = users;
        this.user = user;
        this.album = album;
        this.photo = photo;

        if(existingTags==null){
            existingTags = new ArrayList<String>();
            existingTags.add("location");
            existingTags.add("person");
            existingTags.add("type");
        }
        tagID.setItems(FXCollections.observableArrayList(existingTags));
    }

    /**
     * handles the confirmation of a tag addition
     * @param e
     * @throws Exception
     */
    public void okay(ActionEvent e) throws Exception{
        String name = tagID.getValue().trim();
        String value = valueID.getText().trim();
        Tag newTag = new Tag(name, value);

        if(name.isEmpty() || value.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: One of the fields is empty");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to add this tag.");
            errorAlert.showAndWait();
        } else if(photo.findTag(newTag)!=-1){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: This tag,value pair exists on this photo already.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to add this tag.");
            errorAlert.showAndWait();
        } else{
            boolean hasSpace = false;
            for(int i = 0; i < name.length(); i++){
                if(Character.isWhitespace(name.charAt(i))){
                    hasSpace = true;
                }
            }
            for(int i = 0; i < value.length(); i++){
                if(Character.isWhitespace(value.charAt(i))){
                    hasSpace = true;
                }
            }
            if(hasSpace){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERROR");
                errorAlert.setContentText("Problem: Tag names and values cannot contain whitespace.");
                errorAlert.setHeaderText("Whoops! Something went wrong when trying to add this tag.");
                errorAlert.showAndWait();
                return;
            }

            if(!existingTags.contains(name)){
                existingTags.add(name);
            }
            photo.getTags().add(newTag);
            Stage stage = (Stage) okAT.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
            Parent root = (Parent) loader.load();
            photoViewController controller = loader.<photoViewController>getController();
            controller.start(stage, user, users, album);
            stage.setScene(new Scene(root));
        }
    }

    /**
     * handles the cancellation of a tag addition
     * @param e
     * @throws Exception
     */
    public void cancel(ActionEvent e)throws Exception {
        Stage stage = (Stage) cancelAT.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
        Parent root = (Parent) loader.load();
        photoViewController controller = loader.<photoViewController>getController();
        controller.start(stage, user, users, album);
        stage.setScene(new Scene(root));
    }

}
