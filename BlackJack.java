// Marco Gallegos, Sara Filipinas, Noel Sandoval

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BlackJack {

  // Array to represent a deck of 52 cards
  private static Card[] cards = new Card[52];

  // Keeps track of the current card being dealt from the deck
  private static int currentCardIndex = 0;

  // Tracks suit index while creating the deck
  private static int suitIndex = 0;

  public static void main(String[] args) {
    // Scanner to read player's input
    Scanner scanner = new Scanner(System.in);

    // Boolean variable to control the game loop
    boolean turn = true;

    // Variable to store the player's decision (whether to play again or not)
    String playerDecision = "";

    ArrayList<Card> playerHand = new ArrayList<>();  // Store player's cards
    ArrayList<Card> dealerHand = new ArrayList<>();  // Store dealer's cards

    // Main game loop, continues as long as 'turn' is true
    while(turn) {
      initializeDeck(); // Initialize a new deck of cards
       shuffleDeck(); // Shuffle the deck if uncommented

       // Player's initial hand
       playerHand.add(dealCard());
       playerHand.add(dealCard());
       displayHand("Player", playerHand);

       // Dealer's initial hand
       dealerHand.add(dealCard());
       dealerHand.add(dealCard());
       displayHand("Dealer", dealerHand);

      int playerTotal = 0;  // Player's hand total
      int dealerTotal = 0;  // Dealer's hand total

      // Deal initial two cards to the player and compute total value
      playerTotal = dealInitialPlayerCards();

      // Deal the initial card to the dealer
      dealerTotal = dealInitialDealerCards();

      // Player's turn to hit or stand
      playerTotal = playerTurn(scanner, playerTotal);

      // If the player's total exceeds 21, they bust, and the game ends
      if (playerTotal > 21) {
        System.out.println("You busted! Dealer wins.");
        return;  // Exit the game
      }

      // Dealer's turn to draw cards
      dealerTotal = dealerTurn(dealerTotal);

      // Determine who won the game
      determineWinner(playerTotal, dealerTotal);

      // Ask the player if they want to play again
      System.out.println("Would you like to play another hand?");
      playerDecision = scanner.nextLine().toLowerCase();  // Convert input to lowercase for consistency

      // Validate input, ensuring it's either "yes" or "no"
      while(!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
        System.out.println("Invalid action. Please type 'yes' or 'no'.");
        playerDecision = scanner.nextLine().toLowerCase();
      }

      // If player chooses "no", end the game loop
      if (playerDecision.equals("no"))
          turn = false;  // Set 'turn' to false to stop the loop
    }
    System.out.println("Thanks for playing!");  // Farewell message
  }

  // Function to initialize the deck of cards
  private static void initializeDeck() {
    // Define the possible suits and ranks of the cards
    String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
    
    int suitsIndex = 0, rankIndex = 0;  // Indexes to track suit and rank

    // Loop through and create all 52 cards
    for (int i = 0; i < cards.length; i++) {
      int val = 10;  // Default value for face cards (Jack, Queen, King, Ace)

      // Assign values for number cards (2-10)
      if(rankIndex < 9)
        val = Integer.parseInt(RANKS[rankIndex]);

      // Create and assign a new card to the deck with value, suit, and rank
      cards[i] = new Card(val, SUITS[suitsIndex], RANKS[rankIndex]);

      suitsIndex++;  // Move to the next suit

      // If all suits for a rank are completed, move to the next rank
      if (suitsIndex == 4) {
        suitsIndex = 0;
        rankIndex++;
      }
    }
  }

  // Function to shuffle the deck of cards
  private static void shuffleDeck() {
    Random random = new Random();
    
    // Randomly swap each card with another card in the deck
    for (int i = 0; i < cards.length; i++) {
      int index = random.nextInt(cards.length);  // Pick a random index
      Card temp = cards[i];  // Temporary variable for swapping
      cards[i] = cards[index];
      cards[index] = temp;
    }
  }

  // New Feature:  Display all cards in a hand for both player and dealer
  public static void displayHand(String who, ArrayList<Card> hand) {
      System.out.println(who + "'s hand:");
      for (Card card : hand) {
          System.out.println(card);  // toString() is called automatically
      }
      System.out.println();  // Blank line after the hand
  }

  // Function to deal the player's initial two cards
  private static int dealInitialPlayerCards() {
    // Deal two cards to the player
    Card card1 = dealCard();
    Card card2 = dealCard();

    // Show the player's cards
    System.out.println("Your cards: " + card1.getRank() + " of " + card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());

    // Return the total value of the player's initial hand
    return card1.getValue() + card2.getValue();
  }

  // Function to deal the dealer's initial card
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();  // Deal one card to the dealer
    System.out.println("Dealer's card: " + card1);
    return card1.getValue();  // Return the value of the dealer's initial card
  }

  // Function to manage the player's turn
  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {
      // Ask the player whether they want to hit or stand
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.nextLine().toLowerCase();  // Convert action to lowercase

      if (action.equals("hit")) {
        Card newCard = dealCard();  // Deal a new card
        playerTotal += newCard.getValue();  // Add the value of the new card to the player's total
        System.out.println("You drew a " + newCard);  // Show the drawn card

        // If player's total exceeds 21, they bust
        if (playerTotal > 21) {
          System.out.println("You busted! Dealer wins!");
          playerTotal = 0;  // Reset total for the next game
          return playerTotal;  // End player's turn
        }
      } else if (action.equals("stand")) {
        break;  // Player chooses to stand, end their turn
      } else {
        // If input is invalid, ask for valid input again
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
      }
    }
    return playerTotal;  // Return the player's final total
  }

  // Function to manage the dealer's turn
  private static int dealerTurn(int dealerTotal) {
    // Dealer must hit until their total is at least 17
    while (dealerTotal < 17) {
      Card newCard = dealCard();  // Deal a new card to the dealer
      dealerTotal += newCard.getValue();  // Add card's value to dealer's total
    }
    System.out.println("Dealer's total is " + dealerTotal);  // Show dealer's total
    return dealerTotal;  // Return dealer's final total
  }

  // Function to determine the winner of the round
  private static void determineWinner(int playerTotal, int dealerTotal) {
    if (dealerTotal > 21 || playerTotal > dealerTotal) {
      // Player wins if dealer busts or player has a higher score
      System.out.println("You win!");
    } else if (dealerTotal == playerTotal) {
      // It's a tie if both totals are equal
      System.out.println("It's a tie!");
    } else if(dealerTotal == 21){
      System.out.println("Player wins !!");
    }
    
    else {
      // Dealer wins if their total is higher and they haven't busted
      System.out.println("Dealer wins!");
      playerTotal = 0;  // Reset player's total
    }
  }

  // Function to deal the next card in the deck
  private static Card dealCard() {
    return cards[currentCardIndex++];  // Return the next card and move to the next one
  }
}