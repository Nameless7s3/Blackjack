import java.util.Random;

public class Deck
{
    int[][] cardDeck;

    int numSuits;
    int numCards;


    //Deck constructor
    Deck(int givenNumSuits, int givenNumCards)
    {
        numSuits = givenNumSuits;
        numCards = givenNumCards;

        cardDeck = new int[numSuits][numCards];

        fillDeck();
    }

    //Fill the deck with numbers
    void fillDeck()
    {
        for(int i=0; i<numSuits; i++)
        {
            for(int j=0; j<numCards; j++)
            {
                cardDeck[i][j] = j+1;
            }
        }
    }

    int getRandomCard()
    {
        int randomSuit;
        int randomCard;
        int cardValue = -1;

        boolean foundValidCard = false;

        while (!foundValidCard)
        {
            randomSuit = getRandInt(numSuits);
            randomCard = getRandInt(numCards);

            if(cardDeck[randomSuit][randomCard] > 0)
            {
                cardValue = cardDeck[randomSuit][randomCard];
                cardDeck[randomSuit][randomCard] = 0;

                foundValidCard = true;
            }
        }

        return cardValue;
    }

    private int getRandInt (int max)
    {
        Random rand = new Random();

        return rand.nextInt(max-1) + 1;
    }
}
