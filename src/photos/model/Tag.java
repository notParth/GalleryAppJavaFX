package photos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {
    private String name;
    private String value;



    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value=value;
    }

    public String toString(){
        return name+", "+value;
    }

    public boolean equals(Tag tag){
        if(this.name.equals(tag.name) && this.value.equals(tag.value)){
                return true;
        }
        return false;
    }
}
