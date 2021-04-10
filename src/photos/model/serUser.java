package photos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class serUser implements Serializable {

    private String username;
    private ArrayList<serAlbum> albums;

    public serUser(User user) {
        this.username = user.getUsername();
        albums = new ArrayList<serAlbum>();
        for (Album album : user.getAlbums()) {
            this.albums.add(new serAlbum(album));
        }
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<serAlbum> getAlbums() {
        return albums;
    }
}
