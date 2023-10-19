import java.util.Scanner;

public class Blackjack
{
    static Blackjack b;

    static Playable[] players;
    static Deck deck;

    public static void main (String[] args)
    {
        b = new Blackjack();
        players = new Playable[]{new Player("Player"), new Dealer("Dealer")};
        deck = new Deck(4,13);

        b.mainMenu();
    }

    //Main menu loop
    void mainMenu()
    {
        launchGreeting();

        int menuChoice = -1;

        while (menuChoice != 2)
        {
            menuChoice = Integer.parseInt(getInput("Select an option: 1: Play 2: Quit"));

            switch (menuChoice) {
                case 1 -> mainGame();

                //play
                case 2 -> System.out.println("You have quit the game.");

                //quit
                default -> System.out.println("Invalid option, choose from 1-2.");

                //invalid option
            }

        }
    }

    void mainGame()
    {
        boolean gameRunning = true;
        boolean dealerStanding = false;

        resetGame();

        while(gameRunning)
        {
            if(!dealerStanding) {
                dealerStanding = getDealerStand();
            }

            printScores();
            System.out.println();
            //Get player's next choice
            gameRunning = getPlayerNextChoice(dealerStanding);

            players[1] = updateDealerStandProbability((Dealer) players[1]);
        }

        //Match end
        endMatch();

    }

    boolean getPlayerNextChoice (boolean dealerStanding) {
        boolean gameRunning = true;
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
        return gameRunning;
    }

    void printScores () {
        for (Playable player: players) {
            player.printScore();
        }
    }

    void resetGame () {
        for (Playable player: players) {
            player.setScore(0);
        }

        players[1] = updateDealerStandProbability((Dealer) players[1]);

        //Reset the deck
        deck.resetDeck();
    }

    boolean getDealerStand() {
        Dealer dealer = (Dealer) players[1];
        System.out.println(dealer.getStandProbability());
        return dealer.willStand(players[0]);
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
        return player.getTotalScore() <=21;
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
