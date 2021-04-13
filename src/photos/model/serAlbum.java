package photos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Serializable version of album
 *
 * @author Parth Patel
 * @author Amanda Kang
 */
public class serAlbum implements Serializable {

    private String name;
    private int numberPhotos;
    private Calendar earliestDate;
    private Calendar latestDate;
    private ArrayList<serPhoto> photos;
    private String EDS;
    private String LDS;

    /**
     * creates a serializable album for a given album instance
     * @param album
     */
    public serAlbum(Album album) {
        photos = new ArrayList<serPhoto>();
        this.name = album.getName();
        this.numberPhotos = album.getNumberPhotos();
        this.earliestDate = album.getEarliestDate();
        this.latestDate = album.getLatestDate();
        for (Photo photo : album.getPhotos()) {
            this.photos.add(new serPhoto(photo));
        }
        this.EDS = album.getEDS();
        this.LDS = album.getLDS();
    }

    /**
     * gets the name of the album
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the number of photos in the album
     * @return
     */
    public int getNumberPhotos() {
        return numberPhotos;
    }

    /**
     * gets the earliest date of modification
     * @return date
     */
    public Calendar getEarliestDate() {
        return earliestDate;
    }

    /**
     * gets the latest date of modification
     * @return date
     */
    public Calendar getLatestDate() {
        return latestDate;
    }

    /**
     * gets the photos
     * @return photo
     */
    public ArrayList<serPhoto> getPhotos() {
        return photos;
    }

    /**
     * gets EDS
     * @return EDS
     */
    public String getEDS() {
        return EDS;
    }

    /**
     * gets LDS
     * @return LDS
     */
    public String getLDS() {
        return LDS;
    }
}
