package photos.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Serializable version of Photo
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class serPhoto implements Serializable {
    private File path;
    private String caption;
    private Calendar date;
    private ArrayList<Tag> tags;
    private String D;

    /**
     * creates a serializable version of a given photo
     * @param photo
     */
    public serPhoto(Photo photo){
        this.path = photo.getPath();
        this.caption = photo.getCaption();
        this.date = photo.getDate();
        this.D = photo.getD();
        this.tags = photo.getTags();
    }

    /**
     * gets the path of the photo
     * @return path
     */
    public File getPath() {
        return path;
    }

    /**
     * gets the caption of the photo
     * @return caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * gets the date of the photo
     * @return date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * gets the tags of the photo
     * @return tags
     */
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * gets D
     * @return D
     */
    public String getD() {
        return D;
    }




}
