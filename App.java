import java.util.ArrayList;
import java.util.List;

public class App 
{

    public static void main(String[] args) 
    {
        Deck deck = new Deck();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");


        deck.shuffle();

        dealInitialCards(player1, player2, deck);

        int turnCount = 0;
        int warPoints = 5;


//////////////////////////////////////////////////////////////Creates parameters for gameplay and scoring//////////////////////////////////////////////////////
        while (true) 
        {
            Card card1 = player1.flip();
            Card card2 = player2.flip();

            turnCount++;

            System.out.println("*******************************************************************************");
            System.out.println("\t\t\t\tTurn " + (turnCount));
            System.out.println("*******************************************************************************");

            if (card1 != null && card2 != null) 
            {
                System.out.println("\nPlayer 1:");
                card1.describe();
                System.out.println("\nPlayer 2:");
                card2.describe();
            } 
                else 
                {
                    determineWinner(player1, player2);
                break;
                }

                if (card1.getValue() > card2.getValue()) 
                {
                    player1Wins(player1, player2, card1, card2);
                } 
                    else if (card1.getValue() < card2.getValue()) 
                    {
                        player2Wins(player1, player2, card1, card2);
                    } 
                        else 
                        {
                            resolveWar(player1, player2, card1, card2, warPoints);
                        }

                if (player1.hasAllCards() || player2.hasAllCards()) 
                {
                    determineWinner(player1, player2);
                    break;
                }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////Creates a deck of 52 cards and divides it between the two players///////////////////////////////
    private static void dealInitialCards(Player player1, Player player2, Deck deck) 
    {
        for (int i = 0; i < 52; i++) 
        {
            player1.draw(deck);
            player2.draw(deck);
        }
    }


////////////////////////////////////If the value of player 1's card is greater than the value of player 2's card, player 2 loses the card and it is added to player 1's deck. The game score is...
///////////////////////////////////...also adjusted +1 for player 1 and -1 for player 2.
    private static void player1Wins(Player player1, Player player2, Card card1, Card card2) 
    {
        System.out.println("\nPlayer 1 wins the flip!");
        System.out.println("\nSCORES:");
        player1.incrementScore();
        player2.decrementScore();
        player1.addCardsToHand(List.of(card1, card2));
        System.out.println("Player 1: " + player1.getScore());
        System.out.println("Player 2: " + player2.getScore());
player1.describeHand(); ///////////////////////////////these two lines for comparing score and cards in each player's deck
player2.describeHand(); ///////////////////////////////
        System.out.println("*******************************************************************************");
        System.out.println("");
    }


//////////////////////////////Preforms same function as above method except in favor of player 2////////////////////////////////////////////
    private static void player2Wins(Player player1, Player player2, Card card1, Card card2) 
    {
        System.out.println("\nPlayer 2 wins the flip!");
        System.out.println("\nSCORES:");
        player2.incrementScore();
        player1.decrementScore();
        player2.addCardsToHand(List.of(card1, card2));
        System.out.println("Player 1: " + player1.getScore());
        System.out.println("Player 2: " + player2.getScore());
player1.describeHand();
player2.describeHand();
        System.out.println("*******************************************************************************");
        System.out.println("");
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////Method for war//////////////////////////////////////////////////////////////////////////
    private static void resolveWar(Player player1, Player player2, Card card1, Card card2, int warPoints) 
    {
        System.out.println("\nthis... Means.. WAR!\nwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

        List<Card> player1WarCards = new ArrayList<>();
        List<Card> player2WarCards = new ArrayList<>();

        //////////////////////////////////////Each player places four cards////////////////////////////////////////////////
        for (int j = 0; j < 4; j++) 
        {
            Card warCard1 = player1.flip();
            Card warCard2 = player2.flip();

///////////////////If player has enough cards to play, game will proceed. Otherwise sysout
            if (warCard1 != null) 
            {
                player1WarCards.add(warCard1);
            } 
                else 
                {
                    System.out.println("Player 1 has no more cards.");
                    System.out.println("Player 2 wins!");
                    determineWinner(player1, player2);
                    return;
                }

            if (warCard2 != null) 
            {
                player2WarCards.add(warCard2);
            } 
                else 
                {
                    System.out.println("Player 2 has no more cards.");
                    System.out.println("Player 1 wins!");
                    determineWinner(player1, player2);
                    return;
                }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////// Displays the cards laid down by each player
        System.out.println("\nPlayer 1 lays down...");
        for (Card warCard : player1WarCards) 
        {
            warCard.describe();
        }

        System.out.println("\nPlayer 2 lays down...");
        for (Card warCard : player2WarCards) 
        {
            warCard.describe();
        }

////////////////////Will compare the last cards of each player drawn during war///////////////////////////////////////////////////////////////////////////
        Card warCard1 = player1WarCards.get(player1WarCards.size() - 1);
        Card warCard2 = player2WarCards.get(player2WarCards.size() - 1);


///////////////////////////////////////////////////////////////////Parameters for war//////////////////////////////////////////////////////////////////////
        if (warCard1.getValue() > warCard2.getValue()) 
        {
            for (int b = 0; b < 5; b++) /////////alters scores by 5 for each player
            {
                player1.incrementScore();
                player2.decrementScore();
            }

            System.out.println("\nPlayer 1 wins the war!");

            player1.addCardsToHand(player1WarCards);
            player1.addCardsToHand(player2WarCards);
            player1.addCardsToHand(List.of(card1, card2));
            System.out.println("\nSCORES:");
            System.out.println("Player 1: " + player1.getScore());
            System.out.println("Player 2: " + player2.getScore());
    player1.describeHand();
    player2.describeHand();
            System.out.println("*******************************************************************************");
            System.out.println("");
        } 


            else if (warCard1.getValue() < warCard2.getValue()) 
            {
                for (int a = 0; a < 5; a++) /////////alters scores by 5 for each player   
                {
                player2.incrementScore();
                player1.decrementScore();
                }

            System.out.println("\nPlayer 2 wins the war!");

            player2.addCardsToHand(player1WarCards);
            player2.addCardsToHand(player2WarCards);
            player2.addCardsToHand(List.of(card1, card2));
            System.out.println("\nSCORES:");
            System.out.println("Player 1: " + player1.getScore());
            System.out.println("Player 2: " + player2.getScore());
    player1.describeHand();
    player2.describeHand();
            System.out.println("*******************************************************************************");
            System.out.println("");
            } 

            else 
            {
                System.out.println("Oh boy. Here we go again.."); ////////////////////////recycles the war method. THIS IS LIKELY WHERE THE PROBLEM IS
                resolveWar(player1, player2, card1, card2, 10);
            }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static void determineWinner(Player player1, Player player2) 
    {
        if (player1.hasAllCards()) 
        {
            System.out.println("Player 1 wins!");
        } 
            else if (player2.hasAllCards()) 
            {
            System.out.println("Player 2 wins!");
            }


    System.out.println("\nFinal Score:");
    System.out.println("Player 1: " + player1.getScore());
    System.out.println("Player 2: " + player2.getScore());
    }
}


//Score and card count do not match/ irratic outputs. Likely problem from war resulting in draw. Program may not be recycling war method.
