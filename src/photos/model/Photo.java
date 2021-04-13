package photos.model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Photo class holds photo and related information such as tags, date, path etc
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class Photo {

    private File path;
    private String caption;
    private Image image; //??? not sure how to work w images
    private Calendar date;
    private ArrayList<Tag> tags;
    private ImageView imageV;
    private String D;

    /**
     * Creates a photo instance for a given file on the system, caption, date and Image
     * @param path Path to the file
     * @param caption Caption of the photo
     * @param milliDate Date of the photo
     * @param image Image instance of the file
     */
    public Photo(File path, String caption, long milliDate, Image image){
        this.path = path;
        this.caption = caption;
        this.date = Calendar.getInstance();
        date.setTimeInMillis(milliDate);
        date.set(Calendar.MILLISECOND,0);
        this.D = "" + date.getTime();
        this.image = image;
        this.imageV = new ImageView();
        this.imageV.setFitWidth(50);
        this.imageV.setFitHeight(50);
        this.imageV.setImage(image);
        this.tags = new ArrayList<Tag>();
    }

    /**
     * Converts serialized photo instance into a photo instance
     * @param serialized_photo
     * @throws FileNotFoundException
     */
    public Photo(serPhoto serialized_photo) throws FileNotFoundException {
        this.path = serialized_photo.getPath();
        this.caption = serialized_photo.getCaption();
        this.date = serialized_photo.getDate();
        this.tags = serialized_photo.getTags();
        this.D = serialized_photo.getD();
        this.image = new Image(new FileInputStream(path));
        this.imageV = new ImageView();
        this.imageV.setFitWidth(50);
        this.imageV.setFitHeight(50);
        this.imageV.setImage(image);
    }

    /**
     * gets the path of the photo
     * @return file path
     */
    public File getPath() { return this.path; }

    /**
     * gets the D of the photo
     * @return D
     */
    public String getD(){
        return this.D;
    }

    /**
     * gets the caption of the photo
     * @return caption
     */
    public String getCaption(){
        return caption;
    }

    /**
     * sets the caption of the photo
     * @param caption
     */
    public void setCaption(String caption){
        this.caption=caption;
    }

    /**
     * get the date of the photo
     * @return date
     */
    public Calendar getDate(){
        return date;
    }

    /**
     * gets the Image instance of the file
     * @return Image instance
     */
    public Image getImage(){
        return image;
    }

    /**
     * gets the ImageView instance of the image
     * @return ImageView
     */
    public ImageView getImageV(){
        return imageV;
    }

    /**
     * gets the tags associated with the photo
     * @return tags
     */
    public ArrayList<Tag> getTags(){
        return tags;
    }

    /**
     * used to compare if two photos are equal
     * @param photo
     * @return boolean
     */
    public boolean equals(Photo photo){
        //note: only images matter w.r.t. equality
        Image image1 = this.image;
        Image image2 = photo.getImage();
        for (int i = 0; i < image1.getWidth(); i++)
        {
            for (int j = 0; j < image1.getHeight(); j++)
            {
                if (image1.getPixelReader().getArgb(i, j) != image2.getPixelReader().getArgb(i, j)) return false;
            }
        }
        return true;
    }

    /**
     * seraches for a given tag in the photo
     * @param t tag
     * @return index
     */
    public int findTag(Tag t){
        for (int i=0; i<tags.size(); i++) {
            if (tags.get(i).getName().equals(t.getName()) && tags.get(i).getValue().equals(t.getValue())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * toString for a given photo
     * @return
     */
    public String toString(){
        return caption;
    }
}
