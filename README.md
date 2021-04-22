<h1 align = "center">Blackjack - Dayong Wu</h1>

---

> ## Background
>
> **What's up guys!**
>
> In this project, I have created a card game called Blackjack. The **core** of this project is its **object-oriented design**, which renders the whole project of high **scalability** and **extendability**.  

---

## General workflow of the game

1. Ask the players if they want to start or quit the game.
2. Get players' relevant information (e.g. number of players, names, money, etc).
3. If there is only one player, the computer will be the dealer. Otherwise, the program will randomly select one to be the dealer.
4. Ask how much money the player wants to bet (cannot exceed the total money).
5. Display the player's hand and the dealer's hand (with a card hidden).
6. If there is a Blackjack, this round will ends with a winner (either player or dealer) or a push.
7. Otherwise, player chooses the action (hit/stand/split/double up). Please note that "split" and "double up" are not always available. The game will handle the logics and determine if the player can choose one or both of these options.
8. Keep playing until one of the winning conditions is satisfied (i.e. bust/got 21/one side has a larger hand value, etc).
9. After a round ends, the program will print out the balance and ask if the user wishes to start a new game or cash out. If the user has run out of the money, the game will automatically stops and prints relevant messages.
10. If the user chooses to start again, the game will restart and carry on the remaining balance from last round. Otherwise, the game will stop and print out the remaining balance.

---

## Classes of the project (Recommended Viewing Order)

### 1. GameEntrance.java

- This class serves as the entrance of the entire project.
- Only this class has the main() method.

### 2. NotificationCenter.java

- This class serves as a center that stores all the messages during the game.
- So we don't need to write a lot of similar print statements in other classes!

### 3. Player.java

- This class maintains all the operations that a player can perform.

### 4. BlackjackPlayer.java

- This class specifically manages the operations of a Blackjack player.
- It inherits all the attributes from the Player class since a Blackjack player is a player.

### 5. Dealer.java

- This class takes charge of all the operations that a dealer can perform.

### 6. BlackjackDealer.java

- This class specifically manages the operations of a Blackjack dealer.
- It inherits all the attributes from the Dealer class since a Blackjack dealer is a dealer.

### 7. Card.java

- This class is responsible for managing the attributes of a (poker) card.

### 8. CardDeck.java

- This class represents a card deck (i.e. a deck of (poker) cards).

### 9. Hand.java

- This class manages the attributes of a hand (cards).

### 10. Game.java

- This is the super class for all types of games.

### 11. CardGame.java

- This is the intermediate class designed specifically for all types of card games.
- It inherits from the Game class since a card game is a game.

### 12. Blackjack.java

- This is the main class of the Blackjack game. It controls all the game logics.
- It inherits from the CardGame class because a Blackjack is a card game.

---

## Instructions on how to compile and run the program via Mac Terminal

1. Create a new folder on your MacBook and copy-paste all the 12 classes mentioned above to that folder.
2. Open Terminal and type "cd " (notice there is a  **whitespace** after "cd" !).
3. Then **drag the folder** to the terminal so that Mac can automatically complete the directory of that folder for you.
4. Press "Enter". Now you are inside the newly created folder.
5. Type "javac GameEntrance.java" in the terminal.
6. Type "java GameEntrance" in the terminal.
7. Now you should be able to play the game through Mac Terminal.
8. Please note that you should have a **JDK** installed in you MacBook with version at least **1.8**. 

---

## Instructions on how to compile and run the program in the IDE Console

1. Create a new JAVA project in IntelliJ IDEA CE.
2. Copy-paste all the 12 classes mentioned above to the **/src** folder.
3. Click the "Run" button or press Control+R to run the project.
4. Please note that you should have a **JDK** installed in you MacBook with version at least **1.8**. 

---

## * Highlights of the Project

### 1. Very concise main()

- As only an entrance of the project, the main() method has only **one** line.

- ```Java
  public class GameEntrance {
      public static void main(String[] args) { new Blackjack().play(); } // Start the whole game
  }
  ```

### 2. Input validation checking

- Input validation checking happens everywhere throughout the project. Here is an example:
  - When the players enter their bet, the input validation will be checked.
  - First its data type will be checked. If it is not of type Integer, system will prompt accordingly without crashing.
  - Then, the range of the input will be checked. If it is larger than the total money or less than $1, system will prompt accordingly without crashing.

### 3. Colorful and lively terminal (console) experience

- I have implemented [ANSI escape code/sequence] to display the shapes and colors of the suits.
- Therefore, when you play the game in the terminal or the IDE console, the (poker) cards will be displayed with lovely shapes and colorful suits. In this way, the gaming experience can be improved since things are not just all in one boring color!
- I think there is no need to put screenshots here, since when you play the game, there is a 100% chance for you to see all those things! So don't ruin the surprise ^-^
- Please **NOTE** that if your machine system is Unix-based (e.g. macOS), the display should have no problems. 
- However, if somehow you are using Windows and the display is not normal, please **COMMENT** the 4 add() methods with [ANSI code] and **UNCOMMENT** the following 4 add() methods in the **CardDeck.java** class! 
- This note has also been written in the corresponding place in the **CardDeck.java** class, so no worries if you are viewing my code without this ReadMe opened.

### 4. Data Statistics

- The games can carry out the balance of each round and print out the final result when the players decide to exit the game.

### 5. Proper code format

- Proper indentations, **very** **detailed** comments, etc.

---

## Things Worth Noting

### 1. Rules about getting bust

- After the first deal, if the player chooses to hit and keep hitting, once the player hand is bust, the round directly finishes with the dealer wins. The dealer does not need to hit cards. This rule is backed up by the assignment PDF: hand value of the player exceeds 21, the player’s hand is bust and the player loses. 

### 2. Rules about getting a 21 (Not Natural Blackjack)

- After the first deal, if the player chooses to hit and keep hitting, once the player hand reaches **EXACTLY** a 21, the player will automatically stop, and the dealer **does not** need to hit (according to the assignment PDF). Then the program will announce the player is the winner (since the dealer does not have a Natural Blackjack in the before the first deal, (s)he does not have a 21.)
- This rule is designed to make the game more fair since the rule above ("**Rule about getting bust**") actually tends to let the dealer win. So in this rule, if the player is very lucky to get exactly a 21, we denote the player wins.

### 3. Rules about Split

- The player can only split **ONCE** throughout a round, and they can only split **right after** the first deal and the two dealt cards have the same rank, and the player's money is enough for another equal bet. The definition of **same rank** here is: two cards have the same **face**. For example, [Jack, Jack] are of the same rank while [10, Jack] are **not** of the same rank although they both have 10 as their face values.
- When the player choose to split, (s)he will have two hands. In my program, I call the first hand as "ORIGINAL hand" and the new hand as "SPLIT hand".

### 4. Rules about Double Up

- The player can only double up if the current hand size is 2 and there is no Natural Blackjack, and the money is enough for another equal bet. 
- There are two scenarios where the hand size could be 2:
  1. After the first deal, the player will have 2 cards.
  2. Split, then the player will have 2 cards for both the ORIGINAL and SPLIT hands. Both hands are available for the double up option.
