import java.util.Random;
public class Dealer extends Playable
{
    double standProbability;

    Dealer(String givenName) {
        name = givenName;
        standProbability = totalScore / 21;
    }

    void updateStandProbability() {
        standProbability = totalScore / 21;
    }

    public boolean willStand () {
        Random r = new Random();
        double standChance = r.nextDouble(1);

        if(standChance <= standProbability) {
            System.out.println("The dealer has decided to stand.");
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    void printScore() {
        System.out.println(name + " score: " + getTotalScore());
    }


}
