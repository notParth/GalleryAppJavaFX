package photos.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.Tag;
import photos.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class searchViewController {
    @FXML
    Button back;
    @FXML
    TableView<Photo> tableView;
    @FXML
    ImageView imageView;
    @FXML
    TextField startDate, endDate, tagVals, albumName;
    @FXML
    TableColumn colImage, colCaption;

    private Album results;
    private ArrayList<User> users;
    private User user;
    public void start(Stage stage, User user, ArrayList<User> users) throws Exception {
        stage.setTitle("Search View");
        this.users = users;
        this.user = user;

        if(results==null){
            results = new Album("results");
        }

        colImage.setCellValueFactory((new PropertyValueFactory<>("imageV")));
        colCaption.setCellValueFactory((new PropertyValueFactory<>("caption")));
        tableView.setItems(FXCollections.observableArrayList(results.getPhotos()));
        if (!results.getPhotos().isEmpty()) {
            tableView.getSelectionModel().select(0);
            Photo selectedPhoto = tableView.getSelectionModel().getSelectedItem();
            imageView.setImage(selectedPhoto.getImage());
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        imageView.setImage(newVal.getImage());
                    }else{
                        imageView.setImage(null);
                    }
                }
        );



    }

    //searches
    public void searchDate(ActionEvent e) throws Exception{
        if(!results.getPhotos().isEmpty()){
            results.getPhotos().clear();
            tableView.setItems(FXCollections.observableArrayList(results.getPhotos()));
        }
        String startString = startDate.getText().trim();
        String endString = endDate.getText().trim();
        if(startString.isEmpty() || endString.isEmpty()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: One of the dates is empty. If the date range is one day, fill in both fields with the same date.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to perform this search.");
            errorAlert.showAndWait();
            return;
        }
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        try{
            if(startString.length()!=10 || endString.length()!=10){
                throw new Exception();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setLenient(false);
            Date date1 = sdf.parse(startString);
            startDate.setTime(date1);
            startDate.set(Calendar.MILLISECOND, 0);
            Date date2 = sdf.parse(endString);
            endDate.setTime(date2);
            endDate.set(Calendar.MILLISECOND, 0);
        } catch(Exception p){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: One of the dates is invalid. Please make sure your date is in the format MM/dd/yyyy.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to perform this search.");
            errorAlert.showAndWait();
            return;
        }
        if(startDate.after(endDate)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: The end date is before the start date.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to perform this search.");
            errorAlert.showAndWait();
            return;
        }
        for(Album album : user.getAlbums()){
            for(Photo photo : album.getPhotos()){
                if(photo.getDate().before(endDate) && photo.getDate().after(startDate)){
                    boolean duplicate = false;
                    for(Photo p:results.getPhotos()){
                        if(p.equals(photo)){
                            duplicate = true;
                        }
                    }
                    if(!duplicate){
                        results.addPhoto(photo);
                        tableView.getItems().add(photo);
                    }
                }
            }
        }
        if(results.getPhotos().isEmpty()){
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setContentText("Your search returned no results.");
            infoAlert.showAndWait();
            return;
        }
        tableView.getSelectionModel().select(0);
        imageView.setImage(tableView.getSelectionModel().getSelectedItem().getImage());
    }

    public void searchTag(ActionEvent e){
        if(!results.getPhotos().isEmpty()){
            results.getPhotos().clear();
            tableView.setItems(FXCollections.observableArrayList(results.getPhotos()));
        }
        String TV1, TV2, op;
        String TV = tagVals.getText().trim()+" ";
        String[] split = TV.split("\\s");
        if(split.length == 1){
            TV1 = split[0];
            String[] TV1split = TV1.split("=");
            Tag tag1 = new Tag(TV1split[0], TV1split[1]);
            for(Album album : user.getAlbums()){
                for(Photo photo : album.getPhotos()){
                    if(photo.findTag(tag1)!=-1) {
                        boolean duplicate = false;
                        for(Photo p:results.getPhotos()){
                            if(p.equals(photo)){
                                duplicate = true;
                            }
                        }
                        if(!duplicate){
                            results.addPhoto(photo);
                            tableView.getItems().add(photo);
                        }
                    }
                }
            }
        } else if(split.length == 3){
            op = split[1];
            if(op.equals("OR") || op.equals("AND")){
                TV1 = split[0];
                TV2 = split[2];
                String[] TV1split = TV1.split("=");
                Tag tag1 = new Tag(TV1split[0], TV1split[1]);
                String[] TV2split = TV2.split("=");
                Tag tag2 = new Tag(TV2split[0], TV2split[1]);
                for(Album album : user.getAlbums()){
                    for(Photo photo : album.getPhotos()){
                        boolean t1 = false;
                        boolean t2 = false;
                        if(photo.findTag(tag1)!=-1) {
                            t1 = true;
                        }
                        if(photo.findTag(tag2)!=-1) {
                            t2 = true;
                        }
                        boolean isValid=false;
                        if(op.equals("OR")){
                            if(t1 || t2){
                                isValid = true;
                            }
                        }else{
                            if(t1 && t2){
                                isValid=true;
                            }
                        }
                        if(isValid){
                            boolean duplicate = false;
                            for(Photo p:results.getPhotos()){
                                if(p.equals(photo)){
                                    duplicate = true;
                                }
                            }
                            if(!duplicate){
                                results.addPhoto(photo);
                                tableView.getItems().add(photo);
                            }
                        }
                    }
                }

            }else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("ERROR");
                errorAlert.setContentText("Problem: The conjunction/disjunction operator is invalid. Please make sure the input is in one of the following formats: (1) tag1=value1 (2) tag1=value1 OR tag2=value2 (3) tag1=value1 AND tag2=value2.");
                errorAlert.setHeaderText("Whoops! Something went wrong when trying to perform this search.");
                errorAlert.showAndWait();
                return;
            }
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: The format is invalid. Please make sure the input is in one of the following formats: (1) tag1=value1 (2) tag1=value1 OR tag2=value2 (3) tag1=value1 AND tag2=value2.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to perform this search.");
            errorAlert.showAndWait();
            return;
        }
        if(results.getPhotos().isEmpty()){
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setContentText("Your search returned no results.");
            infoAlert.showAndWait();;
            return;
        }
        tableView.getSelectionModel().select(0);
        imageView.setImage(tableView.getSelectionModel().getSelectedItem().getImage());
    }

    public void createAlbum(ActionEvent e){
        String name = albumName.getText().trim();
        if(results.getPhotos().isEmpty()){
            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelAlert.setTitle("WARNING");
            cancelAlert.setContentText("Confirm Action");
            cancelAlert.setHeaderText("This album will be empty because there are no valid search results. Are you sure you want to create this album?");

            Optional<ButtonType> result = cancelAlert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }
        }
        if (name.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("ERROR");
            errorAlert.setContentText("Problem: The new album name is empty.");
            errorAlert.setHeaderText("Whoops! Something went wrong when trying to create this album.");
            errorAlert.showAndWait();
        }
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
        for(Photo p : results.getPhotos()){
            newAlbum.addPhoto(p);
        }
        user.getAlbums().add(newAlbum);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setContentText("Album "+name+" has been created.");
        infoAlert.showAndWait();;
        return;
    }

    //Change Scenes
    public void back(ActionEvent e) throws Exception{
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
        Parent root = (Parent) loader.load();
        albumViewController controller = loader.<albumViewController>getController();
        controller.start(stage, user, users);
        stage.setScene(new Scene(root));
    }
}
