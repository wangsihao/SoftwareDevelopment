package edu.gatech.seclass.sdpcryptogram;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Cryptogram {
    protected String encodedPhrase;
    protected String solutionPhrase;
    protected int cryptogramID = -1;

    public Cryptogram () { }

    public Cryptogram (String encodedPhrase,String solutionPhrase)
    {
        this.setEncodedPhrase(encodedPhrase);
        this.setSolutionPhrase(solutionPhrase);
    }

    public String getEncodedPhrase() {
        return encodedPhrase;
    }

    public void setEncodedPhrase(String encodedPhrase) {
        this.encodedPhrase = encodedPhrase;
    }

    public String getSolutionPhrase() {
        return solutionPhrase;
    }

    public void setSolutionPhrase(String solutionPhrase) {
        this.solutionPhrase = solutionPhrase;
    }

    public int getCryptogramID() { return cryptogramID; }

    public void setCryptogramID(int cryptogramID) {
        this.cryptogramID = cryptogramID;
    }
}
