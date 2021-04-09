package photos.model;

import java.util.ArrayList;

public class User {

    private String username;
    private ArrayList<Album> albums;

    public User(String username){
        this.username = username;
        albums = new ArrayList<Album>();
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
