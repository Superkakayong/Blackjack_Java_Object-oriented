/**
 * This class serves as a center that stores all the messages during the game.
 * So we don't need to write a lot of similar print statements in other classes!
 */
public class NotificationCenter {
    // Display the welcome messages
    public static void welcome() {
        System.out.println("********************************************************************");
        System.out.println("Hello warriors! Welcome to Blackjack!");
        System.out.println("Please be notice about the followings:");
        System.out.println("    1. If there is only one player, the computer will be the dealer.");
        System.out.println("    2. Otherwise, we will randomly choose a dealer from you.");
        System.out.println("********************************************************************");
        System.out.println("Hit \"y/Y\" to start the game. Or hit any other key to exit.");
    }

    /*
        Prompt the players of the opportunity to exit the game
     */
    public static int startOrExit(String message) {
        if (message.equalsIgnoreCase("Y")) {
            System.out.println("**************************************************");
            System.out.println("Game starts! May the odds be ever in your favor~~");
            System.out.println("**************************************************");
            System.out.println();
            return 1;
        } else {
            System.out.println("**********************************************");
            System.out.println("Game exits:( Take care and come back anytime!");
            System.out.println("**********************************************");
            return 0;
        }
    }

    // Ask for the number of players
    public static void setPlayersNumber(int index) {
        switch (index) {
            case 1:
                System.out.println("Please enter the number of players (1 or 2):");
                break;
            case 2:
                System.out.println("Input out of range! Must be 1 or 2!");
                break;
            case 3:
                System.out.println("Invalid input! Must be an integer!");
                break;
        }
    }

    // Ask the information about setting players (money/name/...)
    public static void setPlayers(int playerIndex, int index) {
        switch (index) {
            case 1:
                System.out.println("Please enter player " + playerIndex + "'s name:");
                break;
            case 2:
                System.out.println("Player " + playerIndex + ", please enter your money (minimum 1.0, maximum 1000.0):");
                break;
            case 3:
                System.out.println("Input range must be [1.0, 1000.0]!");
                break;
            case 4:
                System.out.println("Invalid input! Must be a number!");
                break;
        }
    }

    // Choose the dealer
    public static void assignDealer(int index, String dealerName) {
        switch (index) {
            case 1:
                System.out.println("The computer will be the dealer.");
                break;
            case 2:
                System.out.println(dealerName + " is now the dealer. Your money has been returned.");
                break;
        }
    }

    // Info about betting money
    public static void betMoney(String name, int index) {
        switch (index) {
            case 1:
                System.out.println(name + ", how much money would you bet (cannot exceed your total money)?");
                break;
            case 2:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
            case 3:
                System.out.println("Invalid input! Bet must be at least $1!");
                break;
        }
    }

    // Prompt not enough balance
    public static void notEnoughBalance(double money) {
        System.out.println("Not enough balance! Only $" + money + " left!");
        System.out.print("Please enter your bet again: ");
    }

    // Display the cards
    public static void showCards(int index, int handValue) {
        switch (index) {
            case 1:
                System.out.println("Player's ORIGINAL hand: ");
                break;
            case 2:
                System.out.println("Dealer has: ");
                break;
            case 3:
                System.out.println("Current ORIGINAL hand value: " + handValue);
                System.out.println("----------------------------------");
                break;
            case 4:
                System.out.println("Current SPLIT hand value: " + handValue);
                System.out.println("-----------------------------------");
                break;
            case 5:
                System.out.println("Player's SPLIT hand: ");
                break;
            case 6:
                System.out.println("Current Dealer hand value: " + handValue);
                System.out.println("----------------------------------");
                break;
        }
    }

    // Get the action choice of the player
    public static void getPlayerChoice(int index) {
        switch (index) {
            case 1:
                System.out.println("You can choose one of the following actions (enter 1/2/3/4): ");
                System.out.println("1. Hit; 2. Stand; 3. Split; 4. Double Up");
                break;
            case 2:
                System.out.println("You can choose one of the following actions (enter 1/2/4): ");
                System.out.println("1. Hit; 2. Stand; 4. Double Up");
                break;
            case 3:
                System.out.println("Invalid input! You can only enter 1/2/3/4!");
                break;
            case 4:
                System.out.println("Invalid input! You can only enter 1/2/4!");
                break;
            case 5:
                System.out.println("Invalid input! Must be an integer!");
                break;
            case 6:
                System.out.println("You can choose one of the following actions (enter 1/2): ");
                System.out.println("1. Hit; 2. Stand");
                break;
            case 7:
                System.out.println("Invalid input! You can only enter 1/2!");
                break;
        }
    }

    // Print the information about the ORIGINAL or the SPLIT hand
    public static void originalOrSplit(int index) {
        switch (index) {
            case 1:
                System.out.println("***********************************************");
                System.out.println("Please choose an action for your ORIGINAL hand.");
                System.out.println("***********************************************");
                System.out.println();
                break;
            case 2:
                System.out.println("********************************************");
                System.out.println("Please choose an action for your SPLIT hand.");
                System.out.println("********************************************");
                System.out.println();
                break;
            case 3:
                System.out.println("**************************************************");
                System.out.println("ORIGINAL hand has hand value 21, no action needed.");
                System.out.println("**************************************************");
                System.out.println();
                break;
            case 4:
                System.out.println("***********************************************");
                System.out.println("SPLIT hand has hand value 21, no action needed.");
                System.out.println("***********************************************");
                System.out.println();
                break;
        }
    }

    // Manages the messages in the handOperation() method of the Blackjack class
    public static void handOperation(String playerName, double money, boolean whichHand, int timesOf21, int index) {
        String handName;

        if (whichHand) { handName = "Split hand"; }
        else { handName = "Original hand"; }

        switch (index) {
            case 1:
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.println(handName + " busts!");
//                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println();
                break;
            case 2:
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.print(handName + " has got " + timesOf21 + " time(s) of 21! ");
                System.out.println("Dealer has less than 21.");
//                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.println();
                break;
            case 3:
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.println("Dealer has also got a 21.");
//                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.println();
                break;
            case 4:
                System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
                System.out.println("Dealer busts!");
                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println();
                break;
        }
    }

    // Print the hand values of the dealer
    public static void dealerHandValue(String playerName, double money, int value) {
        System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
        System.out.println("Dealer has hand value of: " + value);
        System.out.println(playerName + ", you now have $" + money + " left.");
        System.out.println("¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡");
        System.out.println();
    }

    // Player got a 21
    public static void playerHas21() {
        System.out.println("Player has hand value 21, no actions needed.");
    }

    // Print out messages about the winning condition
    public static void winnerCongratulations(String playerName, double money, int index) {
        switch (index) {
            case 1:
                System.out.println("Blackjack! Dealer wins!");
                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println();
                break;
            case 2:
                System.out.println("Blackjack! Player wins! Congratulations!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
            case 3:
                System.out.println("Player reaches 21! Player wins! Congratulations!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
            case 4:
                System.out.println("Dealer has larger hand value. Dealer wins!");
                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println();
                break;
            case 5:
                System.out.println("Player has larger hand value. Player wins!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
            case 6:
                System.out.println("PUSH!");
                System.out.println(playerName + ", you still have $" + money + ".");
                System.out.println();
                break;
            case 7:
                System.out.println("Player busts! Dealer wins!");
                System.out.println(playerName + ", you now have $" + money + " left.");
                System.out.println();
                break;
            case 8:
                System.out.println("Dealer busts! Player wins!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
            case 9:
                System.out.println("ORIGINAL hand has larger hand value than the dealer hand!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
            case 10:
                System.out.println("SPLIT hand has larger hand value than the dealer hand!");
                System.out.println(playerName + ", you now have $" + money + "!");
                System.out.println();
                break;
        }
    }

    // Player has run out of money
    public static void runOutOfMoney() {
        System.out.println("Player has lost all his/her money. Game stops!");
        System.out.println("Reload your wallet and come back STRONGER! ^–^");
    }

    // Ask if the player wants to play again or quit
    public static void playAgainOrCashOut() {
        System.out.println("Do you want to enjoy another round or cash out?");
        System.out.println("Hit \"y/Y\" to for YES. Or hit any other key for CASH OUT.");
    }
}
