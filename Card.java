public class Card {
  private int value;
  private String suit;
  private String rank;

  // Constructors
  public Card(int value, String suit, String rank) {
    this.value = value;
    this.suit = suit;
    this.rank = rank;
  }

// Accessor Getter Methods
  public int getValue() {
    return value;
  }

  public String getSuit() {
    return suit;
  }

  public String getRank() {
    return rank;
  }
// allows you to change the card's value
  public void setValue(int value) {
    this.value = value;
  }

  // Mutator allows you to change the card's suit
  public void setSuit(String suit) {
    this.suit = suit;
  }

  // Mutator allows you to change the card's rank
  public void setRank(String rank) {
    this.rank = rank;
  }


  // toString method to return a string representation of the card
  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}
