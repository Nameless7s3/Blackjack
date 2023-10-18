import java.util.Scanner;

public class Player extends Playable
{
    Player(String givenName) {
        name = givenName;
    }

    @Override
    void printScore() {
        System.out.println(name + " score: " + getTotalScore());
    }

    private String getInput(String message)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
