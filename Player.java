import java.util.ArrayList;
import java.util.List;


public class Player 
{

    private List<Card> hand;
    private int score;
    private String name;

    public Player(String name) 
    {
        this.hand = new ArrayList<>();
        this.score = 26;
        this.name = name;
    }

    //Method describe cards in play
    public void describe() 
    {
        System.out.println("Player: " + name);
        for (Card card : hand) 
        {
            card.describe();
        }
    }
 

    // Method to get the size of the player's hand
    public int getHandSize() 
    {
        return hand.size();
    }


        // From deck Draw method
    public void draw(Deck deck) 
    {
        Card card = deck.draw();
        if (card != null) 
        {
            hand.add(card);
        }
    }


    //From hand Flip method
    public Card flip(Player player) 
    {
        if (!hand.isEmpty()) 
        {
            return hand.remove(0); 
        }
        return null;
    }


    // Method to add cards to the player's hand
    public void addCardsToHand(List<Card> cards) 
    {
        hand.addAll(cards);
    }


    //used to check if a player has all cards
    public boolean hasAllCards() 
    {
        return hand.size() == 52; 
    }

    
    public int getScore() 
    {
        return score;
    }


    // Score method
    public void incrementScore() 
    {
        score++;
    }

    public void decrementScore() 
    {
        score--;
    }

}

