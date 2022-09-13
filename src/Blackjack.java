import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Blackjack extends GraphicsProgram {

    // data about the game
    private int wager = 0;
    private int balance = 1000;
    private int bank = 10000000;

    // labels to display info to the player
    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private GLabel blackjack;

    // buttons for controls
    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton quitButton;

    // objects we are playing with
    private Deck deck; // duh
    private GHand player;
    private GHand dealer;
    private boolean roundOver;


    @Override
    public void init() {
        Color background = new Color(90, 100, 200);
        this.setBackground(Color.GREEN);

        deck = new Deck();
        roundOver = false;

        // set up our buttons
        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");


        // add buttons to the screen
        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        // TODO: handle initial button visibility
        wagerButton.setVisible(true);
        quitButton.setVisible(true);
        playButton.setVisible(false);
        hitButton.setVisible(false);
        stayButton.setVisible(false);

        addActionListeners();

        // TODO: set up your GLabels
        bankLabel = new GLabel("bank: "+bank);
        wagerLabel = new GLabel("Wager: " + wager);
        balanceLabel = new GLabel("balance: "+balance);
        blackjack = new GLabel("Black Jack");

        add(bankLabel,10,20);
        add(wagerLabel,10,40);
        add(balanceLabel,10,30);
        add(blackjack,getWidth()/2,getHeight()/2);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Wager":
               wager();
                // TODO
                break;

            case "Play":
                play();
                // TODO
                break;

            case "Hit":
               hit();
               break;
                // TODO
            case "Stay":
              stay();

                // TODO
                break;

            case "Quit":
                quit();
                // TODO
                break;

            default:
                System.out.println("I do not recognize that action command. Check your button text.");
        }
    }
    private void quit(){
        exit();
    }
    private void stay(){
        dealer.flipCard(0);
        playButton.setVisible(true);
        while (dealer.getTotal()<17){
            dealer.hit();
        }
        for (int i = 0; i < 21; i++) {
            if(dealer.getTotal()-(21-i)==0){
                lose();
                return;
            }else if(player.getTotal()-(21-i)==0){
                win();
                return;
            }else if(dealer.getTotal()-(21+i)==0){
                lose();
                return;
            }else if(player.getTotal()-(21+i)==0){
                win();
                return;
            }
        }

    }
    private void hit(){
        if(player.getCount()<7) {
            player.hit();

        }else {
            Dialog.showMessage("You have 7 cards all ready you have to Stay");
            stay();
        }
    }
    private void play(){
        if(!roundOver) {
            if (Dialog.getString("Y to lose your wager and return to menu N to continue this run")== "Y") {
                removeAll();

                wagerButton.setVisible(true);
                quitButton.setVisible(true);
                playButton.setVisible(false);
                hitButton.setVisible(false);
                stayButton.setVisible(false);
                add(wagerButton, SOUTH);
                add(playButton, SOUTH);
                add(hitButton, SOUTH);
                add(stayButton, SOUTH);
                add(quitButton, SOUTH);

                bankLabel = new GLabel("bank: " + bank);
                wagerLabel = new GLabel("Wager: " + wager);
                balanceLabel = new GLabel("balance: " + balance);
                blackjack = new GLabel("Black Jack");

                add(bankLabel, 10, 20);
                add(wagerLabel, 10, 40);
                add(balanceLabel, 10, 30);
                add(blackjack, getWidth() / 2, getHeight() / 2);
                roundOver = false;
                return;
            }
        }else {
            removeAll();

            wagerButton.setVisible(true);
            quitButton.setVisible(true);
            playButton.setVisible(false);
            hitButton.setVisible(false);
            stayButton.setVisible(false);
            add(wagerButton, SOUTH);
            add(playButton, SOUTH);
            add(hitButton, SOUTH);
            add(stayButton, SOUTH);
            add(quitButton, SOUTH);

            bankLabel = new GLabel("bank: " + bank);
            wagerLabel = new GLabel("Wager: " + wager);
            balanceLabel = new GLabel("balance: " + balance);
            blackjack = new GLabel("Black Jack");

            add(bankLabel, 10, 20);
            add(wagerLabel, 10, 40);
            add(balanceLabel, 10, 30);
            add(blackjack, getWidth() / 2, getHeight() / 2);
            roundOver = false;
        }
    }
    private void wager(){
        wager = Dialog.getInteger("Wager?");
        if ( wager <= balance) {
            balance -= wager;
            blackjack.setVisible(false);
            hitButton.setVisible(true);
            stayButton.setVisible(true);
            player = new  GHand( new Hand(deck,false));
            dealer = new  GHand( new Hand(deck,true));
            add(dealer,100,10);
            add(player,100,getHeight()-this.getHeight()/2);
            bankLabel.setLabel("Bank: "+bank);
            wagerLabel.setLabel("Wager: " + wager);
            balanceLabel.setLabel("balance: "+balance);
            wagerButton.setVisible(false);


        }else {Dialog.showMessage("invalid Wager");}
    }
    private void lose(){
        roundOver = true;
        bank += wager;
        wager = 0;
        bankLabel.setLabel("Bank: "+bank);
        wagerLabel.setLabel("Wager: " + wager);
        balanceLabel.setLabel("balance: "+balance);
        hitButton.setVisible(false);
        stayButton.setVisible(false);

        Dialog.showMessage("You lose " +
                "press play to play again");
        if(balance==0){
            Dialog.showMessage("You have gone bankrupted now leave.");
            quit();
        }
    }
    private void win(){
        roundOver = true;
        bank -= wager;
        balance += (wager *2);
        wager = 0;
        bankLabel.setLabel("Bank: "+bank);
        wagerLabel.setLabel("Wager: " + wager);
        balanceLabel.setLabel("balance: "+balance);
        hitButton.setVisible(false);
        stayButton.setVisible(false);

        Dialog.showMessage("You win " +
                "Press play to play again");
        if(bank <=0){
            Dialog.showMessage("You win " +
                    "You made the casino go Bankrupt please don't come back");
            quit();
        }
    }


    @Override
    public void run() {
        //GHand hand = new GHand(new Hand(deck, true));
        //add(hand, 100, 100);
        //hand.hit();
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}
