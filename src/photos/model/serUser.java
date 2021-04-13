package photos.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Serializable version of User
 *
 * @author Parth Patel
 * @author Amanda Kang
 */

public class serUser implements Serializable {

    private String username;
    private ArrayList<serAlbum> albums;

    /**
     * creates a serializable version of a given user
     * @param user
     */
    public serUser(User user) {
        this.username = user.getUsername();
        albums = new ArrayList<serAlbum>();
        for (Album album : user.getAlbums()) {
            this.albums.add(new serAlbum(album));
        }
    }

    /**
     * gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the albums associated with a user
     * @return albums
     */
    public ArrayList<serAlbum> getAlbums() {
        return albums;
    }
}
