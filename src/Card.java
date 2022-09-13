
public class Card {
    //Value?
    //Suit
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    //Face
    public enum Face {
        DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private final Suit suit;
    private final Face face;
    private boolean faceUp;

    public Card(Face face, Suit suit){
        this.face = face;
        this.suit = suit;
        faceUp = true;
    }

    //getFace
    public Face getFace(){
        return face;
    }

    //getSuit
    public Suit getSuit(){
        return suit;
    }

    //getValue
    public int getValue(){
        //when face == *case* do this
        switch(face){
            case ACE:
                return 11;
            case KING:
            case QUEEN:
            case JACK:
                return 10;
            default:
                return face.ordinal() + 2;
        }
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    public void setFaceUp(boolean faceUp){
        this.faceUp = faceUp;
    }

    public void flip(){
        faceUp = !isFaceUp();
    }

    @Override
    public String toString(){
        return face + " OF " + suit;
    }
}