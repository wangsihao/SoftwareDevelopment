# Assignment 5 -- Software Design

***Christopher Hobbs***

------------

1. **A user shall be able to choose to log in as the administrator or as a specific player when starting the application.  For simplicity, authentication is optional.**

This attribute is satisfied within the Player class which contains the userLogin() operation. The ability to login as an administrator is based on the isAdmin flag on the Player objects. When a new Player is added to the Player library, a hashed password is also included to facilitate authentication.

------------

2. **The application shall allow players to:**
	1. **Choose a cryptogram to solve**
	2. **Solve cryptograms**
	3. **See previously solved cryptograms**
	4. **View the list of player ratings**

Sub-requirements i, iii, and iv are satisfied by the association operations between the Player and Library classes. Namely, chooseCryptogram(), viewSolvedCryptograms(), and viewPlayerRatings(). 

Sub-requirement ii is satisfied by the association operation between the Player and Cryptogram classes and is dependent of other related association operations between other classes (addressed under other requirements).

------------

3. **The application shall allow the administrator to:**
	1. **Add new cryptograms**
	2. **Add new local players**

Sub-requirement i is satisfied by the addCryptogram() association operation between the Player and Cryptogram classes. This then calls subsequent association operations, such as addCryptogramToLibrary() and uploadCryptogram(), to save the new cryptogram and upload a copy to the central server. Ability to call this operation is dependent on the isAdmin flag of the player.

Sub-requirement ii is satisfied by the addPlayer() association operation between the Player and Library classes. Ability to call this operation is dependent on the isAdmin flag of the player.

------------

4. **The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions).**

This requirement is achieved with the Library class and the DatabaseIO utility class. The Library class maintains an active copy of all the data objects in ArrayLists while the program is running. It will also periodically write the data in the ArrayLists to a flat file (Assume something like JSON) for persistent storage. This is done through the saveLocalLibrary() and loadLocalLibrary() association operations.

This class will also handle syncing with the central server via the ExternalWebService utility class to ensure any new cryptograms created locally and uploaded and any new cryptograms on the server are downloaded. It also syncs any edits made by admins to the server as well as uploading and retrieving player ratings.

------------

5. **Cryptograms and player ratings will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:**
	1. **Send updated player ratings.**
	2. **Send new cryptograms and receive a unique identifier for them.**
	3. **Request a list of additional cryptograms.**
	4. **Request the current player ratings.**

Sub-requirement i is satisfied by the uploadPlayerRatings() association operation between the Library class and the ExternalWebService utility class. 

Sub-requirement ii is satisfied by the uploadCryptogram() association operation between the Library class and the ExternalWebService utility class. When a new cryptogram is created, addCryptogram() calls addCryptogramToLibrary() inorder to save the cryptogram object in the local database. Then uploadCryptogram() is called within addCryptogramToLibrary() and the cryptogramID attribute is updated with the ID received back from the ExternalWebService before the adding to library operation is completed. Then before the entire operation is completed, displayConfirmation() is called to confirm the cryptogram was successfully added and uploaded and to display the ID number.

Sub-requirements iii and iv are satisfied by the getCryptograms() and getPlayerRatings() association operations between the Library class and the ExternalWebService utility class.

------------

6. **A cryptogram shall have an encoded phrase (encoded with a simple substitution cipher), and a solution.**

This requirement is satisfied with the Cryptogram class which contains the attributes encodedPhrase and solutionPhrase. The requirement of the substitution cipher does not directly affect the design as it is a requirement for implementation logic.

------------

7. **A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces).**

This requirement is not addressed in the UML diagram as it does not directly affect the design of the program class structure. This is a requirement for implementation logic.

------------

8. **To add a player, the administrator will enter the following player information:**
	1. **A first name**
	2. **A last name**
	3. **A unique username**

This requirement is satisfied with the Player class which contains the attributes firstname, lastname, username. The uniqueness constraint of the username is identified by the {unique} tag. This class is also extended to include a hashedPassword and isAuthenticated attributes to handle player login and authentication. There is also an isAdmin attribute to define which player account have admin rights to add new players and cryptograms. Player accounts with the isAdmin attribute set to true can use the addPlayer operation to create a new player object in the libraryPlayer database.

------------

9. **To add a new cryptogram, an administrator will:**
	1. **Enter a solution phrase.**
	2. **Enter a matching encoded phrase.**
	3. **Edit any of the above information as necessary.**
	4. **Save the complete cryptogram.**
* **After doing so, the administrator shall see a confirmation message. The message shall contain the unique identifier assigned to the cryptogram by the external web service utility.**

Sub-requirements i and ii are satisfied by the Cryptogram class which contains the attributes encodedPhrase and solutionPhrase. 

Sub-requirement iii is satisfied by the editCryptogram() association operation between the Player and Cryptogram classes and its related follow-on operations (updateCryptogramInLibrary() and updateCryptogram()).

Sub-requirement iv is satisfied by the addCryptogramToLibrary() association operation between the Cryptogram and Library classes. 

------------

10. **To choose and solve a cryptogram, a player will:**
	1. **Choose a cryptogram from a list of all available cryptograms (see also Requirement 11).**
	2. **View the chosen cryptogram (including any prior solution, complete or in progress, in case he or she already worked on the same cryptogram earlier).**
	3. **Assign (or reassign) replacement letters to the encrypted letters and view the effects of these assignments in terms of resulting potential solution.**
	4. **Submit the current solution when he or she has replaced all letters in the puzzle and is satisfied with such solution.**
* **At this point, the player shall get a result indicating whether the solution was correct. At any point, the player may return to the list of cryptograms to try another one.**

Sub-requirement i is satisfied jointly by the viewCryptograms() and chooseCryptogram() association operations between the Player and Library classes.

Sub-requirement ii is handled by the CryptogramInstance class which is constantly updated by the updateSolutionState() and updateCryptogramInstance() association operations. This ensures that the current attempted solution is saved across plays.

Sub-requirement iii is not addressed in the UML diagram as it related to implementation logic and does not directly impact the design.

Sub-requirement iv is satisfied by the checkIfSolved() operation in the Cryptogram class. checkIfSolved() will also call updateSolutionState() (which then calls updateCryptogramInstance()) to ensure that the player's instance of the cryptogram is kept up-to-date.

------------

11. **The list of available cryptograms shall show, for each cryptogram, its identifier, whether the player has solved it, and the number of incorrect solution submissions, if any.**

This requirement is satisfied by the viewCryptograms() association operation between the Player and Library classes. This operation will display the full list of available cryptograms from the libraryCryptogram ArrayList in the Library class. It will then call getCryptogramInstances(username: String) to receive an array of all the cryptograms the current player has attempted and will collate it the isSolved and incorrectAttempt attribute data with the full list of available cryptograms. 

Note: New CryptogramInstance objects are only generated the first time a player attempts a cryptogram. If a player has never attempted a particular cryptogram, the list will default to show it as unsolved and having 0 incorrect attempts.

------------

12. **The list of player ratings shall display, for each player, his or her name, the number of cryptograms solved, the number of cryptograms started, and the total number of incorrect solutions submitted. The list shall be sorted in descending order by the number of cryptograms solved.**

This requirement is satisfied by the viewPlayerRatings() association operation between the Player and Library classes. This operation relies upon the getPlayerRatings() operation in the Library class to retrieve the data. 

The format and sorting of this data is not addressed in the UML diagram as it relates to implementation logic and does not directly affect the design.

------------

13. **The User Interface (UI) shall be intuitive and responsive.**

This requirement is not addressed in the UML diagram as it does not directly affect the design of the program class structure.

------------

