package photos.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MCViewController {
    private ArrayList<User> users;
    private User user;
    private Album album;
    private Photo photo;

    @FXML
    Button moveTo, copyTo, back;
    @FXML
    TableView<Album> tableView;
    @FXML
    TableColumn colName, colNum, colDate1, colDate2;
    @FXML
    Text text;

    public void start(Stage stage, User user, ArrayList<User> users, Album album, Photo photo) throws Exception {
        stage.setTitle("Move/Copy Application");
        this.users = users;
        this.user = user;
        this.album = album;
        this.photo = photo;

        text.setText("Please select an album you would like to move/copy this photo ("+photo.getCaption()+") to:");


        colName.setCellValueFactory((new PropertyValueFactory<>("name")));
        colNum.setCellValueFactory((new PropertyValueFactory<>("numberPhotos")));
        colDate1.setCellValueFactory((new PropertyValueFactory<>("EDS")));
        colDate2.setCellValueFactory((new PropertyValueFactory<>("LDS")));

        tableView.setItems(FXCollections.observableArrayList(user.getAlbums()));
        tableView.getItems().remove(album); //prevent moving to same folder
        tableView.getSelectionModel().select(0);
    }

    public void copy(ActionEvent e) throws Exception {
        Album dest = tableView.getSelectionModel().getSelectedItem();
        dest.addPhoto(photo);

        Stage stage = (Stage) copyTo.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
        Parent root = (Parent) loader.load();
        photoViewController controller = loader.<photoViewController>getController();
        controller.start(stage, user, users, album);
        stage.setScene(new Scene(root));

    }

    public void move(ActionEvent e) throws Exception {
        Album dest = tableView.getSelectionModel().getSelectedItem();
        dest.addPhoto(photo);
        album.getPhotos().remove(photo);

        Stage stage = (Stage) copyTo.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
        Parent root = (Parent) loader.load();
        photoViewController controller = loader.<photoViewController>getController();
        controller.start(stage, user, users, album);
        stage.setScene(new Scene(root));

    }

    public void back(ActionEvent e)throws Exception {
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
        Parent root = (Parent) loader.load();
        photoViewController controller = loader.<photoViewController>getController();
        controller.start(stage, user, users, album);
        stage.setScene(new Scene(root));
    }
}
