package photos.model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

public class Photo {

    private String caption;
    private Image image; //??? not sure how to work w images
    private Calendar date;
    private ArrayList<Tag> tags;
    private ImageView imageV;
    private String D;

    public Photo(String caption, long milliDate, Image image){
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

    public String getD(){
        return this.D;
    }

    public String getCaption(){
        return caption;
    }

    public void setCaption(String caption){
        this.caption=caption;
    }

    public Calendar getDate(){
        return date;
    }
    
    public Image getImage(){
        return image;
    }

    public ImageView getImageV(){
        return imageV;
    }

    public ArrayList<Tag> getTags(){
        return tags;
    }

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

    public int findTag(Tag t){
        for (int i=0; i<tags.size(); i++) {
            if (tags.get(i).getName().equals(t.getName()) && tags.get(i).getValue().equals(t.getValue())) {
                return i;
            }
        }
        return -1;
    }

    public String toString(){
        return caption;
    }
}
