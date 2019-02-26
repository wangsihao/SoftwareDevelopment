package edu.gatech.seclass.sdpcryptogram;

public class CryptogramInstance extends Cryptogram{

    protected int cryptogramID;
    protected String username;
    protected String currentSolution = "";
    protected boolean isSolved=false;
    protected int incorrectAttempts = 0;
    protected boolean isStarted = false;

    public CryptogramInstance (int cryptogramID, String username, boolean isSolved, int incorrectAttempts)
    {
        this.setUsername(username);
        this.setSolved(isSolved);
        this.setIncorrectAttempts(incorrectAttempts);
        this.setCryptogramID(cryptogramID);

    }

    public boolean isSolved() {
        return isSolved;
    }

    public int getCryptogramID() {
        return cryptogramID;
    }

    public void setCryptogramID(int cryptogramID) {
        this.cryptogramID = cryptogramID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(String currentSolution) {
        this.currentSolution = currentSolution;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(int incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }
    public void setStarted(boolean started) {isStarted = started;}

    public boolean isStarted() {return isStarted;}
}
