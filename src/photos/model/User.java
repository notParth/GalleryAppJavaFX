package photos.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class User {

    private String username;
    private ArrayList<Album> albums;

    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
    }
    public User(serUser serialized_user) throws FileNotFoundException {
        this.username = serialized_user.getUsername();
        albums = new ArrayList<Album>();
        for (serAlbum a : serialized_user.getAlbums()) {
            this.albums.add(new Album(a));
        }
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }

    public boolean equals(User user){
        return this.username.equals(user.username);
    }

    public String toString(){
        return username;
    }

    public int findUser(ArrayList<User> users){
        for (int i=0; i<users.size(); i++) {
            if (users.get(i).username.equals(this.username)) {
                return i;
            }
        }
        return -1;
    }


}
