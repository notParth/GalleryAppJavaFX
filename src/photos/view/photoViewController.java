package photos.view;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import photos.model.Album;
import photos.model.Photo;
import photos.model.Tag;
import photos.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;

/**
 * controller for handling the photos view
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class photoViewController {
    @FXML
    Button logout, back, nextPhoto, prevPhoto, addTag, MC;
    @FXML
    TableView<Photo> tableViewPhotos;
    @FXML
    TableView<Tag> tableViewTags;
    @FXML
    TableColumn colImage, colCaption, colName, colValue;
    @FXML
    ImageView imageView;
    @FXML
    Text dateTaken, caption;


    private ArrayList<User> users;
    private User user;
    private Album album;

    /**
     * start method for the photos view
     * @param stage
     * @param user
     * @param users
     * @param album
     * @throws Exception
     */
    public void start(Stage stage, User user, ArrayList<User> users, Album album) throws Exception {
        stage.setTitle("Photos Application");
        this.users = users;
        this.user = user;
        this.album = album;

        colImage.setCellValueFactory((new PropertyValueFactory<>("imageV")));
        colCaption.setCellValueFactory((new PropertyValueFactory<>("caption")));
        colName.setCellValueFactory((new PropertyValueFactory<>("name")));
        colValue.setCellValueFactory((new PropertyValueFactory<>("value")));

        tableViewPhotos.setItems(FXCollections.observableArrayList(album.getPhotos()));
        if (!album.getPhotos().isEmpty()) {
            tableViewPhotos.getSelectionModel().select(0);
            Photo selectedPhoto = tableViewPhotos.getSelectionModel().getSelectedItem();
            imageView.setImage(selectedPhoto.getImage());
            caption.setText("Caption: " + selectedPhoto.getCaption());
            dateTaken.setText("Date Taken : " + selectedPhoto.getD());
            tableViewTags.setItems(FXCollections.observableArrayList(selectedPhoto.getTags()));
            tableViewTags.getSelectionModel().select(0);
        }
        tableViewPhotos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        imageView.setImage(newVal.getImage());
                        caption.setText("Caption: " + newVal.getCaption());
                        dateTaken.setText("Date Taken : " + newVal.getD());
                        tableViewTags.setItems(FXCollections.observableArrayList(newVal.getTags()));
                        tableViewTags.getSelectionModel().select(0);
                    }else{
                        imageView.setImage(null);
                        caption.setText("Caption: ");
                        dateTaken.setText("Date Taken : ");
                    }
                }
        );


    }

    /**
     * handles the addition of a photo to an album
     * @param e
     * @throws Exception
     */
    //Add/Remove/Rename Photo
    public void addPhoto(ActionEvent e) throws Exception {
        //FileDialog explorer = new FileDialog(new JFrame());
        //explorer.setVisible(true);
        //File[] f = explorer.getFiles();
        FileChooser explorer = new FileChooser();
        explorer.setTitle("Open Image");
        explorer.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) back.getScene().getWindow();
        File f[] = explorer.showOpenMultipleDialog(stage).toArray(new File[0]);
        if (f.length > 0) {
            String path = f[0].getAbsolutePath();
            Image image = new Image(new FileInputStream(path));
            Photo photo = new Photo(new File(path), f[0].getName(), f[0].lastModified(), image);
            for (Photo p : album.getPhotos()) {
                if (p.equals(photo)) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("ERROR");
                    errorAlert.setContentText("Problem: This photo is already in the album.");
                    errorAlert.setHeaderText("Whoops! Something went wrong when trying to add this photo.");
                    errorAlert.showAndWait();
                    return;
                }
            }
            for (Album a : user.getAlbums()){
                for (Photo p : a.getPhotos()){
                    if(p.equals(photo)){
                        album.addPhoto(p);
                        tableViewPhotos.getItems().add(p);
                        tableViewPhotos.getSelectionModel().select(p);
                        imageView.setImage(p.getImage());
                        caption.setText("Caption: " + p.getCaption());
                        dateTaken.setText("Date Taken : " + p.getD());
                        return;
                    }
                }
            }
            album.addPhoto(photo);
            tableViewPhotos.getItems().add(photo);
            tableViewPhotos.getSelectionModel().select(photo);
            imageView.setImage(photo.getImage());
            caption.setText("Caption: " + photo.getCaption());
            dateTaken.setText("Date Taken : " + photo.getD());
        }
    }

    /**
     * handles the renaming of a photo
     * @param e
     * @throws Exception
     */
    public void renamePhoto(ActionEvent e) throws Exception {
        if(!album.getPhotos().isEmpty()){
            TextInputDialog getPhotoName = new TextInputDialog();
            getPhotoName.setHeaderText("Rename Photo");
            getPhotoName.setContentText("New Photo Name:");
            Optional<String> result = getPhotoName.showAndWait();
            if (result.isPresent()) {
                String name = getPhotoName.getEditor().getText().trim();
                if (name.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("ERROR");
                    errorAlert.setContentText("Problem: The new photo name is empty.");
                    errorAlert.setHeaderText("Whoops! Something went wrong when trying to recaption this photo.");
                    errorAlert.showAndWait();
                } else if(name.equals(tableViewPhotos.getSelectionModel().getSelectedItem().getCaption())){
                    return;
                } else {
                    tableViewPhotos.getSelectionModel().getSelectedItem().setCaption(name);
                    caption.setText("Caption: " + getPhotoName.getEditor().getText().trim());
                    tableViewPhotos.refresh();
                }
            }
        }
    }

    /**
     * handles the deletion of a photo
     * @param e
     * @throws Exception
     */
    public void deletePhoto(ActionEvent e) throws Exception {
        if (!album.getPhotos().isEmpty()) {
            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelAlert.setTitle("WARNING");
            cancelAlert.setContentText("Confirm Action");
            cancelAlert.setHeaderText("Are you sure you want to delete this photo? This action cannot be undone.");
            Optional<ButtonType> result = cancelAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Photo selectedPhoto = tableViewPhotos.getSelectionModel().getSelectedItem();
                album.getPhotos().remove(selectedPhoto);
                tableViewPhotos.getItems().remove(selectedPhoto);
            }
        }
    }

    //Next/Prev Photo
    public void SNP(ActionEvent e) {
        tableViewPhotos.getSelectionModel().selectNext();
    }

    public void SPP(ActionEvent e) {
        tableViewPhotos.getSelectionModel().selectPrevious();
    }


    //Move, Copy

    /**
     * handles the transition from photos view to move and copy view
     * @param e
     * @throws Exception
     */
    public void moveCopy(ActionEvent e) throws Exception {
        if (user.getAlbums().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: no photo is selected.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to move/copy this photo.");
            errorAlert.showAndWait();
        }
        Stage stage = (Stage) MC.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/MCView.fxml"));
        Parent root = (Parent) loader.load();
        MCViewController controller = loader.<MCViewController>getController();
        Photo photo = tableViewPhotos.getSelectionModel().getSelectedItem();
        controller.start(stage, user, users, album, photo);
        stage.setScene(new Scene(root));
    }

    /**
     * handles logging out
     * @param e
     * @throws Exception
     */
    //Changing Scenes
    public void logout(ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/photos/view/loginView.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * handles backing into the previous/parent window
     * @param e
     * @throws Exception
     */
    public void back(ActionEvent e) throws Exception {
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
        Parent root = (Parent) loader.load();
        albumViewController controller = loader.<albumViewController>getController();
        controller.start(stage, user, users);
        stage.setScene(new Scene(root));
    }

    /**
     * handles the addition of a tag to a photo
     * @param e
     * @throws Exception
     */
    //Adding, Deleting tags
    public void addTag(ActionEvent e) throws Exception {
        if (!album.getPhotos().isEmpty()) {
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/addTagView.fxml"));
            Parent root = (Parent) loader.load();
            addTagViewController controller = loader.<addTagViewController>getController();
            controller.start(stage, user, users, album, tableViewPhotos.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root));
        }
    }

    /** handles the deletion of a tag in a photo
     *
     * @param e
     */
    public void deleteTag(ActionEvent e) {
        Photo selectedPhoto = tableViewPhotos.getSelectionModel().getSelectedItem();
        Tag selectedTag = tableViewTags.getSelectionModel().getSelectedItem();
        selectedPhoto.getTags().remove(selectedTag);
        tableViewTags.getItems().remove(selectedTag);
    }

}
