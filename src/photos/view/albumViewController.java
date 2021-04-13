package photos.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.User;

import java.util.ArrayList;
import java.util.Optional;

/**
 * controller for handling the album view
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class albumViewController {

    @FXML
    Button logout, openAlbum, search, createAlbum;
    @FXML
    TableView<Album> tableView;
    @FXML
    TableColumn colName, colNum, colDate1, colDate2;


    private ArrayList<User> users;
    private User user;

    /**
     * start method of album view
     * @param stage
     * @param user
     * @param users
     * @throws Exception
     */
    public void start(Stage stage, User user, ArrayList<User> users) throws Exception {
        stage.setTitle("Album View");
        this.users = users;
        this.user = user;

        colName.setCellValueFactory((new PropertyValueFactory<>("name")));
        colNum.setCellValueFactory((new PropertyValueFactory<>("numberPhotos")));
        colDate1.setCellValueFactory((new PropertyValueFactory<>("EDS")));
        colDate2.setCellValueFactory((new PropertyValueFactory<>("LDS")));

        tableView.setItems(FXCollections.observableArrayList(user.getAlbums()));
        tableView.getSelectionModel().select(0);


    }

    /**
     * handles the creation of an album for a user
     * @param e
     */
    // Create/Rename/Delete Album
    public void create(ActionEvent e) {
        TextInputDialog getAlbumName = new TextInputDialog();
        getAlbumName.setHeaderText("Create Album");
        getAlbumName.setContentText("Album Name:");
        Optional<String> result = getAlbumName.showAndWait();
        if (result.isPresent()) {
            String name = getAlbumName.getEditor().getText().trim();
            if (name.isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERROR");
                errorAlert.setContentText("Problem: The new album name is empty.");
                errorAlert.setHeaderText("Whoops! Something went wrong when trying to create this album.");
                errorAlert.showAndWait();
            } else {
                for(Album album : user.getAlbums()){
                    if(album.getName().equals(name)){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("ERROR");
                        errorAlert.setContentText("Problem: This album already exists for the user.");
                        errorAlert.setHeaderText("Whoops! Something went wrong when trying to create this album.");
                        errorAlert.showAndWait();
                        return;
                    }
                }
                Album newAlbum = new Album(name);
                user.getAlbums().add(newAlbum);
                tableView.getItems().add(newAlbum);
                tableView.getSelectionModel().select(newAlbum);
            }
        }
    }

    /**
     * handles the deletion of an album for a user
     * @param e
     */
    public void delete(ActionEvent e) {
        if (!user.getAlbums().isEmpty()) {
            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelAlert.setTitle("WARNING");
            cancelAlert.setContentText("Confirm Action");
            cancelAlert.setHeaderText("Are you sure you want to delete this album? This action cannot be undone.");
            Optional<ButtonType> result = cancelAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Album selectedAlbum = tableView.getSelectionModel().getSelectedItem();
                ;
                user.getAlbums().remove(selectedAlbum);
                tableView.getItems().remove(selectedAlbum);
            }
        }
    }

    /**
     * handles the renaming of an album for a user
     * @param e
     */
    public void rename(ActionEvent e) {
        if (!user.getAlbums().isEmpty()) {
            TextInputDialog getAlbumName = new TextInputDialog();
            getAlbumName.setHeaderText("Rename Photo");
            getAlbumName.setContentText("New Photo Name:");
            Optional<String> result = getAlbumName.showAndWait();
            if (result.isPresent()) {
                String name = getAlbumName.getEditor().getText().trim();
                if (name.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("ERROR");
                    errorAlert.setContentText("Problem: The new album name is empty.");
                    errorAlert.setHeaderText("Whoops! Something went wrong when trying to rename this album.");
                    errorAlert.showAndWait();
                } else if(name.equals(tableView.getSelectionModel().getSelectedItem().getName())){
                    return;
                } else{
                    for(Album album : user.getAlbums()){
                        if(album.getName().equals(name)){
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("ERROR");
                            errorAlert.setContentText("Problem: This album already exists for the user.");
                            errorAlert.setHeaderText("Whoops! Something went wrong when trying to rename this album.");
                            errorAlert.showAndWait();
                            return;
                        }
                    }
                    tableView.getSelectionModel().getSelectedItem().setName(name);
                    tableView.refresh();
                }


            }
        }
    }

    /**
     * handles the transition from album view to photos view
     * @param e
     * @throws Exception
     */
    // Changing Scenes
    public void open(ActionEvent e) throws Exception {
        if (!user.getAlbums().isEmpty()) {
            Stage stage = (Stage) openAlbum.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/photoView.fxml"));
            Parent root = (Parent) loader.load();
            photoViewController controller = loader.<photoViewController>getController();
            controller.start(stage, user, users, tableView.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root));
        }
    }

    /**
     * handles the transition from album view to search view
     * @param e
     * @throws Exception
     */
    public void search(ActionEvent e) throws Exception {
        Stage stage = (Stage) search.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/searchView.fxml"));
        Parent root = (Parent) loader.load();
        searchViewController controller = loader.<searchViewController>getController();
        controller.start(stage, user, users);
        stage.setScene(new Scene(root));
    }

    /**
     * handles logging out for a given user
     * @param e
     * @throws Exception
     */
    public void logout(ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/photos/view/loginView.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
