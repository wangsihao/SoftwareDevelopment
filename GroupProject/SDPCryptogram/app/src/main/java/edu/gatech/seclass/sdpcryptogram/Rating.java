package edu.gatech.seclass.sdpcryptogram;

import java.util.Comparator;

public class Rating {
    protected String username;
    protected String firstname;
    protected String lastname;
    protected int cryptogramsSolved = 0;
    protected int cryptogramsStarted = 0;
    protected int totalIncorrectSolutions = 0;

    public Rating() {};

    public Rating (String username){
        this.setUsername(username);
    }

    Rating(String username, String lastname,String firstname, int started, int solved, int totalIncorrect) {
        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setCryptogramsStarted(started);
        this.setCryptogramsSolved(solved);
        this.setTotalIncorrectSolutions(totalIncorrect);
    }

    public static Comparator<Rating> SolvedCompare = new Comparator<Rating>() {
        @Override
        public int compare(Rating o1, Rating o2) {

            return o2.getCryptogramsSolved() - o1.getCryptogramsSolved();
        }
    };

    public static Comparator<Rating> getSolvedCompare() {
        return SolvedCompare;
    }

    public static void setSolvedCompare(Comparator<Rating> solvedCompare) {
        SolvedCompare = solvedCompare;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getCryptogramsSolved() {
        return cryptogramsSolved;
    }

    public void setCryptogramsSolved(int cryptogramsSolved) {
        this.cryptogramsSolved = cryptogramsSolved;
    }

    public int getCryptogramsStarted() {
        return cryptogramsStarted;
    }

    public void setCryptogramsStarted(int cryptogramsStarted) {
        this.cryptogramsStarted = cryptogramsStarted;
    }

    public int getTotalIncorrectSolutions() {
        return totalIncorrectSolutions;
    }

    public void setTotalIncorrectSolutions(int totalIncorrectSolutions) {
        this.totalIncorrectSolutions = totalIncorrectSolutions;
    }
}



