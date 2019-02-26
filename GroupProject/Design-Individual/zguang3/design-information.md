# Cryptogram game design information

The design information for each of the requirements listed in the Assignment 5 instruction, as below. 

## Requirements
**1. A user shall be able to choose to log in as the administrator or as a specific player when starting the application.  For simplicity, authentication is optional.**

This login requirement is fulfilled by defining the Class User and the method chooseLogInIdentifier. The returned String value will be the attribute logInIdentifier to determine whether it is administrator or any specific player to log in. The authentification is not considered since it is optional. 

**2. The application shall allow players to  (1) choose a cryptogram to solve, (2) solve cryptograms, (3) see previously solved cryptograms, and (4) view the list of player ratings.**

This set of requirements are fulfilled by defining a class Player with methods (1) chooseCryptogram, (2) solveCryptogram, (3) viewCryptogram, and (4) viewPlayerRating, for each of the functions respectively. 

**3. The application shall allow the administrator to (1) add new cryptograms, and (2) add new local players.**

To meet this requirement, the class Administrator is defined with two methods addNewCryptogram and addNewLocalPlayer. These methods are also associated with class Cryptogram and Player respectively. 

**4. The application shall maintain an underlying database to save persistent information across runs (e.g., cryptograms, players, solutions).**

The database is omitted as it is only persisting information otherwise present in the design.

**5. Cryptograms and player ratings will be shared with other instances of the application.  An external web service utility will be used by the application to communicate with a central server to:
	a.Send updated player ratings.
	b.Send new cryptograms and receive a unique identifier for them.
	c.Request a list of additional cryptograms.
	d.Request the current player ratings.
You should represent this utility as a utility class called "ExternalWebService" that (1) is connected to the other classes in the system that use it and (2) explicitly lists relevant methods used by those classes.**

To meet this requirement, utility class called "ExternalWebService" is defined with methods sendUpdatedPlayerRating, sendNewCryptogram, requestAdditionalCryptogram and requestCurrentPlayerRating to realize the functions (a-d). Also connections are made with two other classes Cryptogram and PlayerRating, with relevant methods explicitly listed along the connecting lines. 

**6. A cryptogram shall have an encoded phrase (encoded with a simple substitution cipher), and a solution.**

In the defined Cryptogram class, there are two attributes "encodedPhrase" and "solution" to meet the above requirements. 

**7. A cryptogram shall only encode alphabetic characters, but it may include other characters (such as punctuation, numbers, or white spaces).**

I did not consider this requirement because it does not affect the design directly.

**8. To add a player, the administrator will enter the following player information:
	a.A first name
	b.A last name
	c.A unique username**

In the Player class, I added the "firstname", "lastname" and "username" attributes. And connection is shown between Administrator and Player class by the addNewLocalPlayer method. 

**9. To add a new cryptogram, an administrator will:
	a.Enter a solution phrase.
	b.Enter a matching encoded phrase.
	c.Edit any of the above information as necessary.
	d.Save the complete cryptogram.
After doing so, the administrator shall see a confirmation message. The message shall contain the unique identifier assigned to the cryptogram by the external web service utility.**

The addNewCryptogram method connects the two classes Administrator and Cryptogram. The entered solution phrase and encoded phrase will go into the attribute "solution" and "encodedPhrase" of the Cryptogram class. To edit the information, the "editInformation" is included in the Cryptogram class. To save the complete cryptogram, the "saveCryptogram" is added in the Cryptogram class.

**10. To choose and solve a cryptogram, a player will:
	a.Choose a cryptogram from a list of all available cryptograms (see also Requirement 11).
	b.View the chosen cryptogram (including any prior solution, complete or in progress, in case he or she already worked on the same cryptogram earlier).
	c.Assign (or reassign) replacement letters to the encrypted letters and view the effects of these assignments in terms of resulting potential solution.
	d.Submit the current solution when he or she has replaced all letters in the puzzle and is satisfied with such solution.
At this point, the player shall get a result indicating whether the solution was correct. At any point, the player may return to the list of cryptograms to try another one.**

To meet the requirement, in the class Cryptograms added a checkAvailableCryptogram method to check the available list of cryptograms from ExternalWebService. The Player class has defined a viewCryptogram method to view the cryptogram info. Also the attributes "priorSolution" and "inProgress" are added in Cryptogram class determine the cryptogram status as in requirement b. The assign(or reassign) function will be fulfilled by the solveCryptogram in Player class. And the submitting function will be fulfilled by the submitSolution method in Player class, which has a Boolean return value to tell if the solution is correct. To start over again anytime, the chooseCryptogram method in Player class can be used. 

**11. The list of available cryptograms shall show, for each cryptogram, its identifier, whether the player has solved it, and the number of incorrect solution submissions, if any.**

Defined in class Cryptograms the attributes "identifier", "whetherSolved", and "numberofIncorrectSolutions". 

**12. The list of player ratings shall display, for each player, his or her name, the number of cryptograms solved, the number of cryptograms started, and the total number of incorrect solutions submitted. The list shall be sorted in descending order by the number of cryptograms solved.**

Defined in the class PlayerRating, the attributes "name", "numberofSolvedCryptograms", "numberofStartedCryptograms", "numberofIncorrectSubmissions", and "sequenceNumber" in Integer to mark the order in the list. 

**13. The User Interface (UI) shall be intuitive and responsive.**

I did not consider this requirement because it does not affect the design directly.


## Author

*Zhe Guang* - In completion of the Assignment 5, CS 6300 SDP Class

## Acknowledgments

Many thanks for the discussions on Piazza and Office Hour by the Instructors and Classmates