How the Design meets the requirements:
1. A user shall be able to choose to log in as the administrator or as a specific player when starting the application.  For simplicity, authentication is optional.
This requirement was accomplished by creating 3 specific classes: the user class, which has a boolean for selection of administrator, and two child classes, administrator and player. The login method is used to call up either the user or the player depending on the entry.
2. The application shall allow players to  (1) choose a cryptogram to solve, (2) solve cryptograms, (3) see previously solved cryptograms, and (4) view the list of player ratings.
This functionality is implemented between the player class and several new classes. The CryptogramList class contains all of the Cryptograms as well as a list of their uniqueIDs, isSolved indicators and numberIncorrectSolutions integer for the given player. These items are called up to view by the players chooseCryptogram method. The player then chooses from the list which Cryptogram they either want to solve or look at and select that one. The CryptogramList then returns that particular Cryptogram. At this point, the player enters data in the cryptogram (or looks at the data that is already in there). The player then enters letters and confirms their answer. The Cryptogram class method isSolutionCorrect will return true if it is and adjust its parameters (such as numberIncorrectSolutions) if not. Once this is complete, the PlayerRatings class, and CryptogramList class are updated with the new information. The two lists then update the web server database. 
Mentioned above was the PlayerRatings class. This class was created in order to meet the needs of the player to view the ranked list. The player has a method of viewRatings which calls and returns the PlayerRatings information.
3. The application shall allow the administrator to (1) add new cryptograms, and (2) add new local players.
The Administrator class has to methods within it: addNewPlayer and addNewCryptogram. The new class AdminCryptogram was created in order to assist with the building and saving of a new cryptogram.
4. The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions).
It is assumed that all player, PlayerList, Cryptogram, and CryptogramList information will be persistent.
5. Cryptograms and player ratings will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
    Send updated player ratings.
    Send new cryptograms and receive a unique identifier for them.
    Request a list of additional cryptograms.
    Request the current player ratings.
You should represent this utility as a utility class called "ExternalWebService" that (1) is connected to the other classes in the system that use it and (2) explicitly lists relevant methods used by those classes.
The external web service was created with the four methods listed above. The AdminCryptogram class uses the sendNewCryptograms method. This method will save the new cryptogram and produce a unique identifier for it. The CryptogramList class uses the requestListOfAdditionalCryptograms method. This method will check the CryptogramList against the database to see if there are new Cryptograms. If there are, the method will return the new cryptograms to be stored locally. The PlayerList class uses botht he requestCurrentPlayerRatings method and the sendUpdatedPlayerRatings method. On instatiation, the requestCurrentPlayerRatings method is called and returns the player ratings from the web server so they can overwrite the PlayerList. When the current player information changes (number of solved cryptograms, number of incorrect responses, etc.) the PlayerList is updated and uses the sendUpdatedPlayerRatings function to update the web server.

6. A cryptogram shall have an encoded phrase (encoded with a simple substitution cipher), and a solution. 
The Cryptogram class has an encodedPhrase field which accomplishes this requirement. 
7. A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces).
The AdminCryptogram class has a private method called isCryptogramValid. This method checks the encodedPhrase and the solutionPhrase to ensure only the alphabetic characters are encoded. To ensure that requirement 6 is also followed, there will be a check to ensure the number of characters are identical as well. If the method returns false, an error will display and the administrator will not be able to submit the new cryptogram.
8. To add a player, the administrator will enter the following player information:
  A first name
  A last name
  A unique username
This requirement is accomplished by adding the addNewPlayer method to the Administrator class with the parameters of firstName, lastName, and userName. I leave the chore of determining unique user names up to the administrator.
9. To add a new cryptogram, an administrator will:
  Enter a solution phrase.
  Enter a matching encoded phrase.
  Edit any of the above information as necessary.
  Save the complete cryptogram.
After doing so, the administrator shall see a confirmation message. The message shall contain the unique identifier assigned to the cryptogram by the external web service utility.
The method addNewCryptogram in the Adminstrator class was created to handle this requirement. This method uses the AdminCryptogram class to create the Cryptogram. Within the class, the phrases will be entered and once complete the user will indicate as such and the isCryptogramValid routine will run to ensure it is correct. If it is, the createCryptogram class will send the new Cryptogram to the web server and receive a uniqueID for the cryptogram back. Last, the Administrator will be returned a message indicating the transfer was complete.
10. To choose and solve a cryptogram, a player will: 
  Choose a cryptogram from a list of all available cryptograms (see also Requirement 11).
      Within the Player class the chooseCryptogram method allows the players to view the cryptogram identifiers, if each is solved, and how   many incorrect solution submissions each entry of the CryptogramList class. 
  View the chosen cryptogram (including any prior solution, complete or in progress, in case he or she already worked on the same         cryptogram earlier).
      The user will select one of the cryptogram uniqueIDs from the list. This will return that particular Cryptogram. The data fields of Cryptogram contains, the currentSolution which would have any partial solutions from before.
  Assign (or reassign) replacement letters to the encrypted letters and view the effects of these assignments in terms of resulting potential solution.
      This would be completed within the Cryptogram class. 
  Submit the current solution when he or she has replaced all letters in the puzzle and is satisfied with such solution.
      Once the player submits their solution the isSolutionCorrect method within the Cryptogram class is called in order to determine if the solution is correct. If it is, the isSolved boolean will be set to true. If it is not, the numberIncorrectSolutions integer will be incremented. The isSolved and numberIncorrectSolutions are returned to the player which the PlayerRatings class then uses to update the web server with the new ranking information. 
At this point, the player shall get a result indicating whether the solution was correct. At any point, the player may return to the list of cryptograms to try another one.
The chooseCryptogram method would be called again to accomplish this last part of requirement 10.
11. The list of available cryptograms shall show, for each cryptogram, its identifier, whether the player has solved it, and the number of incorrect solution submissions, if any.
See Requirement 10a.
12. The list of player ratings shall display, for each player, his or her name, the number of cryptograms solved, the number of cryptograms started, and the total number of incorrect solutions submitted. The list shall be sorted in descending order by the number of cryptograms solved.  
The PlayerRatings class had data fields including playerName, numberSolved, NumberStarted, and totalNumberIncorrect. These data fields are arrays containing all the player ranking information. Any time information changes, the rankPlayers method will be called to re order the information. There are two reasons the data would change. First, the current player information changes (solves a cryptogram, starts a cryptogram, gets a cryptogram wrong). Second, the PlayerRatings class calls the requestCurrentPlayerRatings method of the web server and the resulting data is different than what is contained locally. 
13. The User Interface (UI) shall be intuitive and responsive.
This requirement was not considered because it does not affect the design.
