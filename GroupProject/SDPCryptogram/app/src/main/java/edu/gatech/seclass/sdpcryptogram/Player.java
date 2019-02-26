package edu.gatech.seclass.sdpcryptogram;

public class Player extends User {

    protected String firstname;
    protected String lastname;

    public Player(String username, String firstname, String lastname) {
        super(username);
        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
