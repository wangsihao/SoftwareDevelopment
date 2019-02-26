
package edu.gatech.seclass.sdpcryptogram;


import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;


public  class Library {
    private static ExternalWebService web = ExternalWebService.getInstance();
    private static ArrayList<Cryptogram> libraryCryptogram = new ArrayList<>();
    private static ArrayList<CryptogramInstance> libraryCryptogramInstance = new ArrayList<>();
    private static ArrayList<Rating> libraryRating = new ArrayList<>();
    protected static ArrayList<Player> libraryPlayer = new ArrayList<>();
    private static Context mContext;


    /****** BORROWED FROM EXTERNALWEBSERVICE.JAVA ******/
    //singleton
    private static Library singleton = null;

    protected static Library getInstance(Context context) {
        if(singleton == null) {
            singleton = new Library();
            mContext = context;
            try {

                libraryCryptogram = getCryptogramsFromFile();
                libraryPlayer = getPlayersFromFile();
                libraryRating = getRatingsFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return singleton;
    }

    protected static Library getInstance() {
        return singleton;
    }

    protected Library() { }

    /****** BORROWED FROM EXTERNALWEBSERVICE.JAVA ******/

    public static List<String> getAllUsernames(){
        List<String> usernames = webGetPlayerUsernames();
        return usernames;
    }

    public static ArrayList<Cryptogram> getAllCryptograms(){
        try {
            libraryCryptogram = getCryptogramsFromFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return libraryCryptogram;
    }

    public static ArrayList<CryptogramInstance> getAllCryptogramInstances(String username){
        try {
            libraryCryptogramInstance = getCryptogramInstancesfromfile(username);
            if (libraryCryptogramInstance.size() <= 1){
                //getAllCryptograms();
                for (Cryptogram c : libraryCryptogram){
                    CryptogramInstance i = new CryptogramInstance(c.getCryptogramID(),username,false,0);
                    i.setCurrentSolution(c.getEncodedPhrase());
                    libraryCryptogramInstance.add(i);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return libraryCryptogramInstance;
    }

    public static ArrayList<Rating> getPlayerRatings(){
        try {
            libraryRating = getRatingsFromFile();
            Collections.sort(libraryRating, Rating.getSolvedCompare());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return libraryRating;
    }

    public static ArrayList<Player> getPlayers(){
        try {
            libraryPlayer = getPlayersFromFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return libraryPlayer;
    }

    public static void addPlayer(Player newplayer){
        try {
            //alibraryPlayer = getPlayersFromFile();
            libraryPlayer.add(newplayer);
            addRating(new Rating(newplayer.getUsername(), newplayer.getLastname(), newplayer.getFirstname(), 0, 0, 0));
            boolean check = writePlayersToFile(libraryPlayer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return;
    }

    public static int addCryptogram(Cryptogram newcryptogram){
        //getAllCryptograms();
        String uniqueID = webAddCryptogram(newcryptogram);
        newcryptogram.setCryptogramID(Integer.parseInt(uniqueID));
        libraryCryptogram.add(newcryptogram);
        try{
            writeCryptogramsToFile(libraryCryptogram);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return newcryptogram.getCryptogramID();
    }

    public static void addCryptogramInstance(CryptogramInstance cryptogramInstance){
        libraryCryptogramInstance = getAllCryptogramInstances(cryptogramInstance.getUsername());
        libraryCryptogramInstance.add(cryptogramInstance);
        try{
            writeCryptogramInstancesToFile(libraryCryptogramInstance, cryptogramInstance.getUsername());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return;
    }

    public static void addRating(Rating newRating){
        getPlayerRatings();
        webUpdatePlayerRatings(newRating);
        int check = 0;
        for(Rating rating : libraryRating){
            if(rating.getUsername() !=null && rating.getUsername().equals(newRating.getUsername())) {
                check = 1;
            }
            else if (rating.getUsername() == null && rating.getFirstname().equals(newRating.getFirstname()) && rating.getLastname().equals(newRating.getLastname())) {
                libraryRating.set(libraryRating.indexOf(rating), newRating);
                check = 1;
            }
        }
        if (check == 0){
            libraryRating.add(newRating);
        }
        try{
            writeRatingsToFile(libraryRating);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return;
    }

    public static void updateCryptogram(int cryptogramID, String newEncodedPhrase, String newSolutionsPhrase){
        if(libraryCryptogram.size() == 0){
            getAllCryptograms();
        }
        Cryptogram newCryptogram = new Cryptogram(newEncodedPhrase,newSolutionsPhrase);
        newCryptogram.setCryptogramID(cryptogramID);
        for (Cryptogram cryptogram: libraryCryptogram){
            if(cryptogram.getCryptogramID() == cryptogramID){
                libraryCryptogram.set(libraryCryptogram.indexOf(cryptogram),newCryptogram);
            }
        }
        return;
    }

    public static Cryptogram getCryptogram(int cryptogramID){
        if (libraryCryptogram.size() == 0) {
            getAllCryptograms();
        }
        Cryptogram retCryptogram = new Cryptogram();
        for (Cryptogram cry : libraryCryptogram){
            if(cry.getCryptogramID() == cryptogramID){
                retCryptogram = cry;
            }
        }
        return retCryptogram;
    }
    public static void startCryptogramInstance(int cryptogramID, String username){
        getAllCryptogramInstances(username);
        int solved = 0;
        int incorrect = 0;
        int started = 0;
        Rating up = new Rating();
        for (CryptogramInstance cry : libraryCryptogramInstance){
            if(cry.getCryptogramID() == cryptogramID){
                CryptogramInstance crypt = libraryCryptogramInstance.get(libraryCryptogramInstance.indexOf(cry));
                crypt.setStarted(true);
                libraryCryptogramInstance.set(libraryCryptogramInstance.indexOf(cry),crypt);
            }
        }
        for (Rating r : libraryRating){
            if (r.getUsername() !=null && r.getUsername().equals(username)){
                up = r;
                for (CryptogramInstance c : libraryCryptogramInstance){
                    if (c.isSolved()){
                        solved++;
                    }
                    if (c.isStarted()){
                        started ++;
                    }
                    incorrect += c.getIncorrectAttempts();
                }
            }
        }
        updateRating(up.getUsername(),solved,started,incorrect);
        try{
            writeCryptogramInstancesToFile(libraryCryptogramInstance,username);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
    public static void updateCryptogramInstance(int cryptogramID, String newEncodedPhrase, String newSolutionPhrase, boolean isSolved, int incorrectAttempts, String username){
        getAllCryptogramInstances(username);
        Rating up = new Rating();
        int solved = 0;
        int incorrect = 0;
        int started = 0;
        Cryptogram newCryptogram = new Cryptogram(newEncodedPhrase,newSolutionPhrase);
        newCryptogram.setCryptogramID(cryptogramID);
        CryptogramInstance newCryptogramInstance = new CryptogramInstance(newCryptogram.getCryptogramID(),username,isSolved,incorrectAttempts);
        newCryptogramInstance.setCurrentSolution(newSolutionPhrase);
        for (CryptogramInstance cry : libraryCryptogramInstance){
            if(cry.getCryptogramID() == cryptogramID){
                libraryCryptogramInstance.set(libraryCryptogramInstance.indexOf(cry),newCryptogramInstance);
            }
        }
        for (Rating r : libraryRating){
            if (r.getUsername() !=null && r.getUsername().equals(username)){
                up = r;
                for (CryptogramInstance c : libraryCryptogramInstance){
                    if (c.isSolved()){
                        solved++;
                    }
                    if (c.isStarted()){
                        started ++;
                    }
                    incorrect += c.getIncorrectAttempts();
                }
            }
        }
        updateRating(up.getUsername(),solved,started,incorrect);
        try{
            writeCryptogramInstancesToFile(libraryCryptogramInstance,username);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void updateRating(String username, int cryptogramsSolved, int cryptogramsStarted, int totalIncorrectSolutions){
        Rating newRating = new Rating(username);
        newRating.setCryptogramsSolved(cryptogramsSolved);
        newRating.setCryptogramsStarted(cryptogramsStarted);
        newRating.setTotalIncorrectSolutions(totalIncorrectSolutions);
        getPlayerRatings();
        for (Rating rating : libraryRating){
            if(rating.getUsername() != null&& rating.getUsername().equals(username)){
                newRating.setFirstname(rating.getFirstname());
                newRating.setLastname(rating.getLastname());
                libraryRating.set(libraryRating.indexOf(rating),newRating);
            }
            else if (rating.getUsername() == null && rating.getFirstname().equals(newRating.getFirstname()) && rating.getLastname().equals(newRating.getLastname())){
                newRating.setFirstname(rating.getFirstname());
                newRating.setLastname(rating.getLastname());
                libraryRating.set(libraryRating.indexOf(rating),newRating);
            }
        }
        webUpdatePlayerRatings(newRating);
        try{
            writeRatingsToFile(libraryRating);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void pullDataFromServer(){
        ArrayList<Cryptogram> cryptograms = new ArrayList<Cryptogram>();
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        cryptograms = webGetAllCryptograms();
        ratings = webGetPlayerRating();
        int check_rate = 0;
        int check_crypt = 0;
        for(Rating r: ratings){
            check_rate = 0;
            for(Rating libr : libraryRating){
                if (r.getUsername() != null && r.getUsername().equals(libr.getUsername()) ){
                    check_rate = 1;
                }
                else if (r.getUsername() == null && r.getLastname().equals(libr.getLastname()) && (r.getFirstname()).equals(libr.getFirstname())){
                    check_rate = 1;
                }
            }
            if (check_rate == 0){
                libraryRating.add(r);
            }

        }
        for(Cryptogram c: cryptograms) {
            check_crypt = 0;
            for (Cryptogram libc : libraryCryptogram){
                if (c.getCryptogramID() == libc.getCryptogramID()){
                    check_crypt = 1;
                }
            }
            if (check_crypt == 0){
                libraryCryptogram.add(c);
            }
        }
        try{
            writeCryptogramsToFile(libraryCryptogram);
            writeRatingsToFile(libraryRating);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static String webAddCryptogram(Cryptogram cryptogram) {
        return web.addCryptogramService(cryptogram.getEncodedPhrase(), cryptogram.getSolutionPhrase());
    }

    private static boolean webUpdatePlayerRatings(Rating rating){
        return web.updateRatingService(rating.getUsername(), rating.getFirstname(), rating.getLastname(), rating.getCryptogramsSolved(), rating.getCryptogramsStarted(), rating.getTotalIncorrectSolutions());
    }

    private static ArrayList<Cryptogram> webGetAllCryptograms(){
        ArrayList<Cryptogram> cryptolist = new ArrayList<Cryptogram>();
        List<String[]> array = web.syncCryptogramService();
        for(String[] a : array){
            Cryptogram cryptogram = new Cryptogram();
            cryptogram.setSolutionPhrase(a[2]);
            cryptogram.setEncodedPhrase(a[1]);
            cryptogram.setCryptogramID(Integer.parseInt(a[0]));
            cryptolist.add(cryptogram);
        }
        return cryptolist;
    }

    private static ArrayList<Rating> webGetPlayerRating(){
        ArrayList<Rating> ratingList = new ArrayList<Rating>();
        List<ExternalWebService.PlayerRating> playerRatings = web.syncRatingService();
        for(ExternalWebService.PlayerRating a : playerRatings){
            Rating rating = new Rating();
            rating.setFirstname(a.getFirstname());
            rating.setLastname(a.getLastname());
            rating.setCryptogramsSolved(a.getSolved());
            rating.setCryptogramsStarted(a.getStarted());
            rating.setTotalIncorrectSolutions(a.getIncorrect());
            ratingList.add(rating);
        }
        return ratingList;
    }

    private static List<String> webGetPlayerUsernames(){
        return web.playernameService();
    }

    private static boolean writeCryptogramsToFile(ArrayList<Cryptogram> cryptogram_list) throws IOException{
        JSONObject writer = new JSONObject();
        JSONArray crypto = new JSONArray();
        for(Cryptogram cry : cryptogram_list){
            crypto.add(cry.getSolutionPhrase());
            crypto.add(cry.getEncodedPhrase());
            crypto.add(Integer.toString(cry.getCryptogramID()));
        }

        FileOutputStream fOut = mContext.openFileOutput("Cryptograms.json", Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        writer.put("cryptogram_list", crypto);
        osw.write(writer.toJSONString());
        osw.flush();
        osw.close();
        return true;
    }

    private static boolean writeCryptogramInstancesToFile(ArrayList<CryptogramInstance> cryptogramInstances,String username) throws IOException{
        JSONObject writer = new JSONObject();
        JSONArray crypto = new JSONArray();
        for(CryptogramInstance cry : cryptogramInstances){
            crypto.add(Integer.toString(cry.getCryptogramID()));
            crypto.add(Integer.toString(cry.getIncorrectAttempts()));
            crypto.add(cry.getCurrentSolution());
            crypto.add(Boolean.toString(cry.isSolved()));
            crypto.add(Boolean.toString(cry.isStarted()));
            crypto.add(cry.getUsername());
        }
        String filename = username + "CryptogramInstances.json";
        FileOutputStream fOut = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        writer.put("cryptogram_instances:",crypto);
        osw.write(writer.toJSONString());
        osw.flush();
        osw.close();

        return true;
    }

    private static ArrayList<CryptogramInstance> createNewCryptogramInstances(String username,ArrayList<Cryptogram> cryptograms){
        ArrayList<CryptogramInstance> cryptogramInstances = new ArrayList<CryptogramInstance>();
        for(Cryptogram cry: cryptograms){
            CryptogramInstance temp = new CryptogramInstance(cry.getCryptogramID(),username,false,0);
        }
        return cryptogramInstances;
    }

    private static boolean writeRatingsToFile(ArrayList<Rating> arrayRatings) throws IOException{

        JSONObject writer = new JSONObject();
        JSONArray ratings = new JSONArray();
        for(Rating rate : arrayRatings){
            ratings.add(rate.getUsername());
            ratings.add(rate.getFirstname());
            ratings.add(rate.getLastname());
            ratings.add(Integer.toString(rate.getCryptogramsSolved()));
            ratings.add(Integer.toString(rate.getCryptogramsStarted()));
            ratings.add(Integer.toString(rate.getTotalIncorrectSolutions()));
        }

        FileOutputStream fOut = mContext.openFileOutput("Ratings.json", Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        writer.put("ratings_list",ratings);
        osw.write(writer.toJSONString());
        osw.flush();
        osw.close();

        return true;
    }

    private static boolean writePlayersToFile(ArrayList<Player> arrayPlayers) throws IOException{

        JSONObject writer = new JSONObject();
        JSONArray ratings = new JSONArray();
        for(Player rate : arrayPlayers){
            ratings.add(rate.getUsername());
            ratings.add(rate.getFirstname());
            ratings.add(rate.getLastname());
        }

        FileOutputStream fOut = mContext.openFileOutput("Players.json", Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        writer.put("player_list",ratings);
        osw.write(writer.toJSONString());
        osw.flush();
        osw.close();

        return true;
    }
    private static ArrayList<Player> getPlayersFromFile() throws IOException{
        ArrayList<Player> player= new ArrayList<Player>();
        JSONParser parser = new JSONParser();

        try{
            FileInputStream fIn = mContext.openFileInput("Players.json");
            InputStreamReader isr = new InputStreamReader(fIn);

            Object Obj = parser.parse(isr);
            JSONObject cryptolist = (JSONObject)Obj;

            ArrayList<String> array = (ArrayList<String>)cryptolist.get("player_list");
            int i=0;
            Player temp = new Player("","","");
            for(String a : array){
                if(i==0){
                    temp.setUsername(a);
                    i++;
                }
                else if (i==1){
                    temp.setFirstname(a);
                    i++;
                }
                else if (i==2){
                    temp.setLastname(a);
                    i=0;
                    player.add(temp);
                    temp = new Player("","","");
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return player;
    }

    private static ArrayList<Rating> getRatingsFromFile() throws IOException{
        ArrayList<Rating> fileRatings= new ArrayList<Rating>();
        JSONParser parser = new JSONParser();

        FileInputStream fIn = mContext.openFileInput("Ratings.json");
        InputStreamReader isr = new InputStreamReader(fIn);

        try{
            Object Obj = parser.parse(isr);
            JSONObject cryptolist = (JSONObject)Obj;

            ArrayList<String> array = (ArrayList<String>)cryptolist.get("ratings_list");
            int i=0;
            Rating temp = new Rating("","","",0,0,0);
            for(String a : array){
                if(i==0){
                    temp.setUsername(a);
                    i++;
                }
                else if (i==1){
                    temp.setFirstname(a);
                    i++;
                }
                else if (i==2){
                    temp.setLastname(a);
                    i++;
                }
                else if (i==3){
                    temp.setCryptogramsSolved(Integer.parseInt(a));
                    i++;
                }
                else if (i==4){
                    temp.setCryptogramsStarted(Integer.parseInt(a));
                    i++;
                }
                else if (i==5){
                    temp.setTotalIncorrectSolutions(Integer.parseInt(a));
                    i = 0;
                    fileRatings.add(temp);
                    temp = new Rating("","","",0,0,0);
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return fileRatings;
    }

    private static ArrayList<Cryptogram> getCryptogramsFromFile() throws IOException{
        ArrayList<Cryptogram> cryptograms= new ArrayList<Cryptogram>();
        JSONParser parser = new JSONParser();

        FileInputStream fIn = mContext.openFileInput("Cryptograms.json");
        InputStreamReader isr = new InputStreamReader(fIn);

        try{
            Object Obj = parser.parse(isr);
            JSONObject cryptolist = (JSONObject)Obj;

            ArrayList<String> array = (ArrayList<String>)cryptolist.get("cryptogram_list");
            int i=0;
            Cryptogram temp = new Cryptogram("","");
            for(String a : array){
                if(i==0){
                    temp.setSolutionPhrase(a);
                    i++;
                }
                else if (i==1){
                    temp.setEncodedPhrase(a);
                    i++;
                }
                else if (i==2){
                    temp.setCryptogramID(Integer.parseInt(a));//Integer.parseInt(a);
                    i = 0;
                    cryptograms.add(temp);
                    temp = new Cryptogram("","");
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return cryptograms;
    }

    private static ArrayList<CryptogramInstance> getCryptogramInstancesfromfile(String username) throws IOException{
        ArrayList<CryptogramInstance> cryptograms= new ArrayList<CryptogramInstance>();
        JSONParser parser = new JSONParser();

        String filename = username + "CryptogramInstances.json";
        //ensure file exists
        FileOutputStream fOut = mContext.openFileOutput(filename, Context.MODE_APPEND);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);
        osw.append("");

        osw.close();


        FileInputStream fIn = mContext.openFileInput(filename);
        InputStreamReader isr = new InputStreamReader(fIn);

        try{
            Object Obj = parser.parse(isr);
            JSONObject cryptolist = (JSONObject)Obj;

            ArrayList<String> array = (ArrayList<String>)cryptolist.get("cryptogram_instances:");
            int i=0;
            Cryptogram cryptogram = new Cryptogram("","");
            CryptogramInstance temp = new CryptogramInstance(cryptogram.getCryptogramID(),"",false,-1);
            for(String a : array){
                if(i==0){
                    temp.setCryptogramID(Integer.parseInt(a));
                    i++;
                }
                else if (i==1){
                    temp.setIncorrectAttempts(Integer.parseInt(a));
                    i++;
                }
                else if (i==2){
                    temp.setCurrentSolution(a);
                    i ++;
                }
                else if (i==3){
                    temp.setSolved(Boolean.parseBoolean(a));
                    i++;
                }
                else if (i==4){
                    temp.setStarted(Boolean.parseBoolean(a));
                    i++;
                }
                else if (i==5){
                    temp.setUsername(a);
                    i = 0;
                    cryptograms.add(temp);
                    Cryptogram cryptogram2 = new Cryptogram("","");
                    temp = new CryptogramInstance(cryptogram2.getCryptogramID(),"",false,-1);
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return cryptograms;
    }

}
