package edu.gatech.seclass.sdpcryptogram;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;

    public User(String username) {
        this.setUsername(username);
    }

    public int login(){
        ArrayList<Player> playerList = Library.getPlayers();

        if ((getUsername().compareToIgnoreCase("administrator")==0)||(getUsername().compareToIgnoreCase("admin")==0)) {
            return 1; // type #1
        }
        else {
            for (Player p : playerList) {
                if (getUsername().equalsIgnoreCase(p.getUsername())) {
                    return 2; // type #2
                }
            }
            return 3; // type #3, when player not in the list
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
