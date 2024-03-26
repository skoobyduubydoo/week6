import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck 
{
    private List<Card> cards;

    public Deck() 
    {
        this.cards = initializeDeck();
    }

    private List<Card> initializeDeck() 
    {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"}; ////////////suit names

        for (String suit : suits) 
        {
            for (int value = 2; value <= 14; value++) //////possible values 2-14 points
            {
                String name = value <= 10 ? Integer.toString(value) : switch (value) ////checks if card value is less than/ equal to 10. If greater than 10 the value is converted to a String 
                {
                    case 11 -> "Jack";
                    case 12 -> "Queen";
                    case 13 -> "King";
                    case 14 -> "Ace";
                    default -> throw new IllegalStateException("Unexpected value: " + value);
                };
                deck.add(new Card(value, name + " of " + suit));
            }
        }
        return deck;
    }

    // Shuffle method
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Draw method
    public Card draw() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }
}

