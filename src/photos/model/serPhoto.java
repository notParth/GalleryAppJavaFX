package photos.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class serPhoto implements Serializable {
    private File path;
    private String caption;
    private Calendar date;
    private ArrayList<Tag> tags;
    private String D;

    public serPhoto(Photo photo){
        this.path = photo.getPath();
        this.caption = photo.getCaption();
        this.date = photo.getDate();
        this.D = photo.getD();
        this.tags = photo.getTags();
    }

    public File getPath() {
        return path;
    }

    public String getCaption() {
        return caption;
    }

    public Calendar getDate() {
        return date;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public String getD() {
        return D;
    }




}
