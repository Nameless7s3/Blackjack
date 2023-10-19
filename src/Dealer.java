import java.util.Random;
public class Dealer extends Playable
{
    double standProbability;

    Dealer(String givenName) {
        name = givenName;
        standProbability = (double) totalScore / (double) 21;
    }

    void updateStandProbability() {
        standProbability = (0.000001 * Math.pow(totalScore, 6)) / (double) 21;
        if(standProbability > 1) {standProbability = 1;}
    }

    double getStandProbability() {return standProbability;}

    public boolean willStand (Playable player) {
        Random r = new Random();
        double standChance = r.nextDouble(1);

        if(standChance <= standProbability && player.getTotalScore() < getTotalScore()) {
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
