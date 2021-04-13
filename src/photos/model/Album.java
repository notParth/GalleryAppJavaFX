package photos.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Album class holds photos and related information
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

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

    /**
     * Creates an instance of album with given name
     * @param name Name of the album
     */
    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
        numberPhotos = 0;
    }

    /**
     * Converts serialized album to album
     * @param serialized_album serializable album to be converted into album
     * @throws FileNotFoundException
     */
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

    /**
     * gets the name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * sets the name
     * @param name name of the album
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * gets the number of photos
     * @return number of photos
     */
    public int getNumberPhotos(){
        return this.numberPhotos;
    }

    /**
     * sets the number of photos
     * @param numberPhotos
     */
    public void setNumberPhotos(int numberPhotos){
        this.numberPhotos = numberPhotos;
    }

    /**
     * gets the earliest date of modification
     * @return earliest date
     */
    public Calendar getEarliestDate(){
        return this.earliestDate;
    }

    /**
     * sets the earliest date of modification
     * @param earliestDate
     */
    public void setEarliestDate(Calendar earliestDate){
        this.earliestDate=earliestDate;
    }

    /**
     * gets the last date of modification
     * @return last date of modification
     */
    public Calendar getLatestDate(){
        return this.latestDate;
    }

    /**
     * sets the last date of modification
     * @param latestDate
     */
    public void setLatestDate(Calendar latestDate){
        this.latestDate=latestDate;
    }

    /**
     * gets the EDS
     * @return EDS
     */
    public String getEDS(){
        return this.EDS;
    }

    /**
     * sets EDS
     * @param EDS
     */
    public void setEDS(String EDS){
        this.EDS = EDS;
    }

    /**
     * gets the LDS
     * @return LDS
     */
    public String getLDS(){
        return this.LDS;
    }

    /**
     * sets LDS
     * @param LDS
     */
    public void setLDS(String LDS){
        this.LDS = LDS;
    }

    /**
     * Adds photo to the album
     * @param p Photo to be added
     */
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

    /**
     * gets the photos
     * @return photos
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * sets the photos
     * @param photosList
     */
    public void setPhotos(ArrayList<Photo> photosList){
        this.photos=photosList;
    }

}
