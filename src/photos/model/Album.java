package photos.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class Album {

    private String name;
    private int numberPhotos;
    private Calendar earliestDate;
    private Calendar latestDate;
    private ArrayList<Photo> photos;
    private String EDS;
    private String LDS;

    /*private SimpleStringProperty nameP;
    private SimpleStringProperty numberPhotosP;
    private SimpleStringProperty earliestDateP;
    private SimpleStringProperty latestDateP;*/


    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
        numberPhotos = 0;
    }

    public Album(serAlbum serialized_album) throws FileNotFoundException {
        photos = new ArrayList<Photo>();
        this.name = serialized_album.getName();
        this.numberPhotos = serialized_album.getNumberPhotos();
        this.earliestDate = serialized_album.getEarliestDate();
        this.latestDate = serialized_album.getLatestDate();
        for (serPhoto p : serialized_album.getPhotos()) {
            this.photos.add(new Photo(p));
        }
        this.EDS = serialized_album.getEDS();
        this.LDS = serialized_album.getLDS();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getNumberPhotos(){
        return this.numberPhotos;
    }

    public void setNumberPhotos(int numberPhotos){
        this.numberPhotos = numberPhotos;
    }

    public Calendar getEarliestDate(){
        return this.earliestDate;
    }

    public void setEarliestDate(Calendar earliestDate){
        this.earliestDate=earliestDate;
    }

    public Calendar getLatestDate(){
        return this.latestDate;
    }

    public void setLatestDate(Calendar latestDate){
        this.latestDate=latestDate;
    }

    public String getEDS(){
        return this.EDS;
    }

    public void setEDS(String EDS){
        this.EDS = EDS;
    }

    public String getLDS(){
        return this.LDS;
    }

    public void setLDS(String LDS){
        this.LDS = LDS;
    }

    public void addPhoto(Photo p){
        if(photos.isEmpty()) {
            this.earliestDate = p.getDate();
            this.latestDate = p.getDate();
        } else{
            if(p.getDate().before(this.earliestDate)){
                this.earliestDate = p.getDate();
            }
            if(p.getDate().after(this.latestDate)){
                this.latestDate = p.getDate();
            }
        }
        EDS = "" + this.earliestDate.getTime();
        LDS = "" + this.latestDate.getTime();
        photos.add(p);
        numberPhotos++;
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photosList){
        this.photos=photosList;
    }

}
