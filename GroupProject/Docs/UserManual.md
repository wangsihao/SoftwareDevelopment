# User Manual


**Author**: Team 45 


## 1.1 General Information
The SDPCryptogram, based on Android platform, will let the users play the game of solving cryptograms, upload their solutions to local database and external web services, view other player ratings and rankings, and create new user accounts and cryptograms.

## 2.1. System Information
This applet uses a local Android device which contains all the implemented interface and classes, including specifically a Library class to handle most the Cryptogram, Player and Rating operations and also talks to a local Database by JSON. Through the Library class, the local System can access an External Web Service to upload and update the Cryptogram and Player information. 


## 2.2. System Configurations
SDPCryptogram is an app for android  devices. It is compatible with Android API level 19 and higher.
After installation on the android device, SDPCryptogram needs no further configuration to work.


## 3.1. Login
The app starts with a login menu, which you can choose to login as administrator(input "Admin" or "Administrator", case insensitive) or player. Simply enter your username and click the login button, then you will log into the system.

## 3.2. Administrator
If you log in as adminstrator, then you will have two choices, add a player or add a cryptogram. Click the corresponding button that you would like to edit. 

### 3.2.1. Add a player
- This can be used to create a new player.
- Clicking on this menu item opens an editable text input box where you need to enter the player's name and username.
- The "Submit" button is clicked to create new list while the "Cancel" button is clicked to cancel the add player operation.

### 3.2.2. Add a cryptogram
- This can be used to create a new cryptogram.
- Clicking on this menu item opens an editable text input box where you need to enter the cryptogram's encoded phrase and solution.
- The "Submit" button is clicked to create new list while the "Cancel" button is clicked to cancel the add player operation.

## 3.3. Player
If you log in as player, then you will have two choices, view player rating list or view cryptogram list. Click the corresponding tab that you would like to edit. 

### 3.3.1. View Player Rating list
If you choose to view player rating list, the app will give you a list of all player's record. Which include the number of cryptograms started, solved by each player. Each player's total failure attempts will also be showed on the list.

### 3.3.2. View Cryptogram list
If you choose to view cryptogram list, the app will display the list of all available cryptograms. In the list, you can choose a cryptogram to solve or view the solved cryptogram list. Just click the cryptogram you want to solve and you will go to the playing interface.

If you enter the playing interface, you can find the encoded cryptogram phrase on the screen, the number of attempts is showed below. When finished the solution, you can click submit to test whether your solution is correct. You can also click cancel button to return to the cryptogram list.

## Appendix
### Built With
 
 * [Android Studio](https://developer.android.com/studio/index.html) - Android IDE
 * [Git](https://git-scm.com/) - Version Control
 * [StarUML 2](http://staruml.io/) - UML Creation
 * [OmniGraffle](https://www.omnigroup.com/omnigraffle) - UI Design
 * [Gliffy](https://www.gliffy.com/) - UI Design
 
### Versioning
 
 We use Github for versioning. 
 
### Authors (Alphabetical)
 
 * **Chris Hobbs** - chobbs30
 * **Harris Federman** - hfederman3
 * **Sihao Wang** - swang632
 * **Zhe Guang** - zguang3
 
 
### Acknowledgments
 
 * User Manual Template provided by Billie Thompson ([@PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2))




