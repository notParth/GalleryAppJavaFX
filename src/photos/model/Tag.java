package photos.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tag class for photos
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class Tag implements Serializable {
    private String name;
    private String value;

    /**
     * creates a tag instance for a given name and value
     * @param name
     * @param value
     */
    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * gets tag's name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * sets tag's name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * gets value of the tag
     * @return value
     */
    public String getValue(){
        return value;
    }

    /**
     * sets value
     * @param value
     */
    public void setValue(String value){
        this.value=value;
    }

    /**
     * toString for tag
     * @return
     */
    public String toString(){
        return name+", "+value;
    }

    /**
     * equals method for comparison of two tags
     * @param tag
     * @return
     */
    public boolean equals(Tag tag){
        if(this.name.equals(tag.name) && this.value.equals(tag.value)){
                return true;
        }
        return false;
    }
}
