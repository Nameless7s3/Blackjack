public class Blackjack
{
    public static void main (String[] args)
    {
        Blackjack b = new Blackjack();
        b.main();
    }

    void main ()
    {
        Deck deck = new Deck(4,13);
        System.out.println(deck.getRandomCard());
    }
}
