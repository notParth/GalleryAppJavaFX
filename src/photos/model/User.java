package photos.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * User class to represent a user of our application
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class User {

    private String username;
    private ArrayList<Album> albums;

    /**
     * create a user for a given username
     * @param username
     */
    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
    }

    /**
     * create a user from a given serialized user instance
     * @param serialized_user
     * @throws FileNotFoundException
     */
    public User(serUser serialized_user) throws FileNotFoundException {
        this.username = serialized_user.getUsername();
        albums = new ArrayList<Album>();
        for (serAlbum a : serialized_user.getAlbums()) {
            this.albums.add(new Album(a));
        }
    }

    /**
     * gets the username of the user
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * gets the albums
     * @return albums
     */
    public ArrayList<Album> getAlbums(){
        return albums;
    }

    /**
     * used to compare two users
     * @param user
     * @return
     */
    public boolean equals(User user){
        return this.username.equals(user.username);
    }

    /**
     * toString for user
     * @return
     */
    public String toString(){
        return username;
    }

    /**
     * searches for a user
     * @param users
     * @return index of the user
     */
    public int findUser(ArrayList<User> users){
        for (int i=0; i<users.size(); i++) {
            if (users.get(i).username.equals(this.username)) {
                return i;
            }
        }
        return -1;
    }


}
