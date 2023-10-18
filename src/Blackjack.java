import java.util.Random;
import java.util.Scanner;

public class Blackjack
{
    static Blackjack b;

    static Playable[] players;
    static Deck deck;

    public static void main (String[] args)
    {
        Random random = new Random();

        b = new Blackjack();
        players = new Playable[]{new Player("Player"), new Dealer("Dealer")};
        deck = new Deck(4,13);

        b.mainMenu();
    }

    //Main menu loop
    void mainMenu()
    {
        launchGreeting();

        int choice = -1;

        while (choice != 2)
        {
            int menuChoice = Integer.parseInt(getInput("Select an option: 1: Play 2: Quit"));

            switch (menuChoice)
            {
                case 1:
                    mainGame();
                    break;
                    //play
                case 2:
                    System.out.println("You have quit the game.");
                    break;
                    //quit
                default:
                    System.out.println("Invalid option, choose from 1-2.");
                    break;
                    //invalid option
            }

        }
    }

    void mainGame()
    {
        boolean gameRunning = true;

        for (Playable player: players) {
            player.setScore(0);
        }

        while(gameRunning)
        {
            boolean dealerStanding = getDealerStand();

            for (Playable player: players) {
                player.printScore();
            }

            System.out.println();
            int playerChoice = Integer.parseInt(getInput("Next choice? 1: Hit 2: Stand"));

            switch (playerChoice) {
                case 1: //Hit
                    //Draw random cards for player and dealer
                    if(!dealerStanding) {
                        gameRunning = drawCards(2, 0);
                    }
                    else {
                        gameRunning = drawCards(1,0);
                    }

                    break;

                case 2:
                    if(!dealerStanding) {
                        drawCards(2, 1);
                    }
                    gameRunning = false;
                    break;
            }

            players[1] = updateDealerStandProbability((Dealer) players[1]);
        }

        //Match end
        endMatch();

        //Reset the deck
        deck.resetDeck();
    }

    boolean getDealerStand() {
        Dealer dealer = (Dealer) players[1];
        return dealer.willStand();
    }

    Dealer updateDealerStandProbability(Dealer dealer) {
        dealer.updateStandProbability();
        return dealer;
    }

    boolean drawCards(int numPlayers, int startPlayer) {
        int[] drawnCards = new int[numPlayers];
        boolean hasNotBust = false;

        for (int i=startPlayer; i<numPlayers; i++)
        {
            drawnCards[i] = deck.getRandomCard();
            updatePlayerAndPrint(players[i], players[i].getName(), drawnCards[i]);
            hasNotBust = evaluateScore(players[i]);

            if(!hasNotBust) {
                break;
            }
        }

        return hasNotBust;
    }

    void endMatch ()
    {
        Playable winner = getWinner();
        if(winner != null) {
            System.out.println(winner.getName() + " is the winner with a score of " + winner.getTotalScore());
        }
        else {
            System.out.println("It's a draw");
        }
    }

    Playable getWinner ()
    {
        int playerScore = players[0].getTotalScore();
        int dealerScore = players[1].getTotalScore();

        if(playerScore > 21) {
            return players[1];
        }
        else if(dealerScore > 21) {
            return players[0];
        }
        else if(playerScore == 21 || playerScore > dealerScore) {
            return players[0];
        }
        else if (dealerScore == 21 || dealerScore > playerScore) {
            return players[1];
        }
        else {
            return null;
        }
    }

    void updatePlayerAndPrint (Playable player, String name, int cardValue) {
        System.out.println(name + " drew a " + cardValue);
        player.addToScore(cardValue);
        System.out.println(name + "'s score is: " + player.getTotalScore());
        System.out.println();
    }

    boolean evaluateScore(Playable player) {
        if(player.getTotalScore() < 21) {
            return true;
        }
        else {
            return false;
        }
    }

    private void launchGreeting ()
    {
        System.out.println("Welcome Luke's Blackjack!");
    }

    private String getInput(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
