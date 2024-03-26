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
        int recursiveWarPoints = warPoints + 4;


//////////////////////////////////////////////////////////////Creates parameters for gameplay and scoring//////////////////////////////////////////////////////
        while (true) 
        {
            Card card1 = player1.flip(player1);
            Card card2 = player2.flip(player2);

            turnCount++;

            System.out.println("*******************************************************************************");
            System.out.println("\t\t\t\tTurn " + (turnCount));
            System.out.println("*******************************************************************************");

            if (card1 != null) 
            {
                System.out.println("\nPlayer 1:");
                card1.describe();
            } 
                else 
                {
                    endGame(player1, player2);
                }

            if (card2 != null)
            {
                System.out.println("\nPlayer 2:");
                card2.describe();
            }
                else        
                {
                    endGame(player1, player2);
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
                        resolveWar(player1, player2, card1, card2, warPoints, recursiveWarPoints);
                    }


            if (player1.hasAllCards())
            {
                System.out.println("Game Over. Player 1 wins!\n");
                endGame(player1, player2);
            }
            if (player2.hasAllCards())
            {
                System.out.println("Game Over. Player 2 wins!\n");
                endGame(player1, player2);
            }
        }
    }


////////////////////////////////////////////Creates a deck of 52 cards and divides it between the two players///////////////////////////////////////////////
    private static void dealInitialCards(Player player1, Player player2, Deck deck) 
    {
        for (int i = 0; i < 26; i++) 
        {
            player1.draw(deck);
            player2.draw(deck);
        }
    }


//////////////////////////////Player 1 wins the turn. Score adjusted, cards added to player 2 deck. Score printed////////////////////////////////////////////
    private static void player1Wins(Player player1, Player player2, Card card1, Card card2) 
    {
        System.out.println("\nPlayer 1 wins!");
        player1.incrementScore(); 
        player2.decrementScore();  
        player1.addCardsToHand(List.of(card1, card2));
        determineScore(player1, player2);

        System.out.println("");
    }


//////////////////////////////Player 2 wins the turn. Score adjusted, cards added to player 2 deck. Score printed////////////////////////////////////////////
    private static void player2Wins(Player player1, Player player2, Card card1, Card card2) 
    {
        System.out.println("\nPlayer 2 wins!");
        player2.incrementScore();
        player1.decrementScore();
        player2.addCardsToHand(List.of(card1, card2));
        determineScore(player1, player2);
        System.out.println("");
    }


///////////////////////////////////////////////////////////////////Method for war//////////////////////////////////////////////////////////////////////////
    private static void resolveWar(Player player1, Player player2, Card card1, Card card2, int warPoints, int recursiveWarPoints) 
    {

        List<Card> player1WarCards = new ArrayList<>();
        List<Card> player2WarCards = new ArrayList<>();


///////////////////If player has enough cards to play, game will proceed. Otherwise get score and end game
       if (player1.getHandSize() < 4) 
        {
            System.out.println("\nGame over. Player 1 can not continue.");
            endGame(player1, player2);
        }
        if (player2.getHandSize() < 4) 
        {
            System.out.println("\nGame over. Player 2 can not continue.");
            endGame(player1, player2);
        }
        System.out.println("\nthis... Means.. WAR!\n");


//////////////////////////////////////Each player places four cards. Will end game if a player hasn't enough cards
        for (int j = 0; j < 4; j++) 
        {
            Card warCard1 = player1.flip(player1);
            Card warCard2 = player2.flip(player2);


            if (warCard1 != null) 
            {
                player1WarCards.add(warCard1); //adds four cards to list of player1WarCards
            } 
            else 
            {
                System.out.println("\nGame over. Player 1 can not continue.");
                endGame(player1, player2); 
            }

            if (warCard2 != null) 
            {
                player2WarCards.add(warCard2); //adds four cards to list of player2WarCards
            } 
            else 
            {
                System.out.println("\nGame over. Player 2 can not continue.");
                endGame(player1, player2);
            }
        }
        
////////////////////////// Displays the cards used by each player
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


////////////////////Will compare the fourth and eigth cards drawn during war
        Card warCard1 = player1WarCards.get(player1WarCards.size() - 1);
        Card warCard2 = player2WarCards.get(player2WarCards.size() - 1);


////////////////////Point system and card exchange for war
            if (warCard1.getValue() > warCard2.getValue()) 
            {
                System.out.println("\nPlayer 1 wins the war!");

                for (int b = 0; b < warPoints; b++) //warPoints = 5
                {
                    player1.incrementScore();
                    player2.decrementScore(); 
                }

                player1.addCardsToHand(player1WarCards); 
                player1.addCardsToHand(player2WarCards);
                player1.addCardsToHand(List.of(card1, card2));//adds first two cards played to winner deck
                determineScore(player1, player2); 
                System.out.println("");
            }         

            
            if (warCard1.getValue() < warCard2.getValue()) 
            {
                System.out.println("\nPlayer 2 wins the war!");

                for (int a = 0; a < warPoints; a++)  
                {
                player2.incrementScore();
                player1.decrementScore();
                }

                player2.addCardsToHand(player1WarCards); 
                player2.addCardsToHand(player2WarCards); 
                player2.addCardsToHand(List.of(card1, card2)); //first two cards played
                determineScore(player1, player2);
                System.out.println("");
            } 
            if (warCard1.getValue() == warCard2.getValue())        
            {

/////////////////////////////////////////////////////////Recursive war. Essentially resolveWar boiler plate
                List<Card> player1WarCardsSet2 = new ArrayList<>();
                List<Card> player2WarCardsSet2 = new ArrayList<>();

///////////////////If player has enough cards to play, game will proceed. Otherwise get score and end game.
                if (player1.getHandSize() < 4) 
                    {
                        System.out.println("\nGame over. Player 1 can not continue.");
                        endGame(player1, player2);
                    }
                    if (player2.getHandSize() < 4) 
                    {
                        System.out.println("\nGame over. Player 2 can not continue.");
                        endGame(player1, player2);
                    }
                    System.out.println("\nOh boy... Here we go again!\n");

//////////////////////////////////////Each player places another four cards
                for (int j = 0; j < 4; j++) 
                {
                    Card warCard3 = player1.flip(player1);
                    Card warCard4 = player2.flip(player2);


                    if (warCard3 != null) 
                    {
                        player1WarCardsSet2.add(warCard3); //adds four cards to list of player1WarCards
                    } 
                    else 
                    {
                        System.out.println("\nGame over. Player 1 can not continue.");
                        endGame(player1, player2); 
                    }

                    if (warCard4 != null) 
                    {
                        player2WarCardsSet2.add(warCard4); //adds four cards to list of player2WarCards
                    } 
                    else 
                    {
                        System.out.println("\nGame over. Player 2 can not continue.");
                        endGame(player1, player2);
                    }
                }
        
////////////////////////// Displays the cards used by each player
                System.out.println("\nPlayer 1 lays down...");
                for (Card warCard : player1WarCardsSet2) 
                {
                    warCard.describe();
                }
                    System.out.println("\nPlayer 2 lays down...");
                for (Card warCard : player2WarCardsSet2) 
                {
                    warCard.describe();
                }


////////////////////Will compare the fourth and eigth cards drawn during war
                Card warCard3 = player1WarCardsSet2.get(player1WarCardsSet2.size() - 1);
                Card warCard4 = player2WarCardsSet2.get(player2WarCardsSet2.size() - 1);


///////////////////////////////////////////////////////////////////Scoring and card exchange for war
                if (warCard3.getValue() > warCard4.getValue()) 
                {
                    System.out.println("\nPlayer 1 wins the war!");

                    for (int b = 0; b < recursiveWarPoints; b++) /////////alters scores by 9 for each player
                    {
                        player1.incrementScore();
                        player2.decrementScore();
                    }

                    player1.addCardsToHand(player1WarCards); //first set of war cards
                    player1.addCardsToHand(player2WarCards); 
                    player1.addCardsToHand(player1WarCardsSet2); //second set of war cards
                    player1.addCardsToHand(player2WarCardsSet2);
                    player1.addCardsToHand(List.of(card1, card2));//first two cards played
                    determineScore(player1, player2); 
                    System.out.println("");
                }         

                
                if (warCard3.getValue() < warCard4.getValue()) 
                {
                    System.out.println("\nPlayer 2 wins the war!");

                    for (int a = 0; a < recursiveWarPoints; a++) /////////alters scores by 9 for each player   
                    {
                    player2.incrementScore();
                    player1.decrementScore();
                    }

                    player2.addCardsToHand(player1WarCards); 
                    player2.addCardsToHand(player2WarCards); 
                    player2.addCardsToHand(player1WarCardsSet2); //war cards
                    player2.addCardsToHand(player2WarCardsSet2); //war cards
                    player2.addCardsToHand(List.of(card1, card2)); //first two cards played
                    determineScore(player1, player2);
                    System.out.println("");
                }             
            }
    }
    

    
////////////////////////////////////////////////////////////Scoring method////////////////////////////////////////////////////////////////////////////////
    private static void determineScore(Player player1, Player player2) 
    {
        System.out.println("\nScores:");
        System.out.println("Player 1: " + player1.getScore()); 
        System.out.println("Player 2: " + player2.getScore());
    }

/////////////////////////////////////////////////////////////////////////////End game and program////////////////////////////////////////////////////////
    private static void endGame(Player player1, Player player2)
    {
        determineScore(player1, player2);
        System.exit(0);
    }

}
