public abstract class Playable
{
    int totalScore;
    String name;

    Playable()
    {
        totalScore = 0;
    }

    void addToScore(int numToAdd){
        totalScore += numToAdd;
    }

    void setScore(int numToSet) {
        totalScore = numToSet;
    }

    int getTotalScore() {
        return totalScore;
    }

    String getName() {return name;}

    void printScore () {
        System.out.println(name + "'s score: " + getTotalScore());
    }
}