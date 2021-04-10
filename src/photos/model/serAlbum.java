package photos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class serAlbum implements Serializable {

    private String name;
    private int numberPhotos;
    private Calendar earliestDate;
    private Calendar latestDate;
    private ArrayList<serPhoto> photos;
    private String EDS;
    private String LDS;

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

    public String getName() {
        return name;
    }

    public int getNumberPhotos() {
        return numberPhotos;
    }

    public Calendar getEarliestDate() {
        return earliestDate;
    }

    public Calendar getLatestDate() {
        return latestDate;
    }

    public ArrayList<serPhoto> getPhotos() {
        return photos;
    }

    public String getEDS() {
        return EDS;
    }

    public String getLDS() {
        return LDS;
    }
}
