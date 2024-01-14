/*
 * Cody Kesselring
 * Farkle HW3
 * 10/29/2023
 * This program lets the user play a game of farkle. Start by setting a point limit and then a name, it will then enter the main game loop, 
 * where the user can add dice to the meld to be scored. The user continues to make melds until the point limit is reached and the game ends.
 */

package edu.gonzaga.Farkle;

import java.util.ArrayList;
import java.util.Scanner;
/*
*  This is the main class for the Farkle project.
*  It really should just instantiate another class and run
*   a method of that other class.
*/

/** Main program class for launching Farkle program. */
public class Farkle {
    // This main is where your Farkle game starts execution for general use.
    public static void main(String[] args) {
        int round = 0;
        int numDice = 6;
        int dicePosition = 0;
        String stringDicePos;
        int numberOfZeros = 0;
        int scoreThreshold;
        Boolean farkle = false;
        ArrayList<Die> dice = new ArrayList<>();
        Meld meldA = new Meld();
        Combos combos = new Combos();
        Scanner scanner = new Scanner(System.in);

        meldA.gameBanner();
        scoreThreshold = meldA.scoreThreshold();
        meldA.playerName();

        while(meldA.getScore()<scoreThreshold){    //main game loop
            //initializes dice
            System.out.println("Round: " + round+1);
            farkle = false;
           
            meldA.clearMelds();
            meldA.clearDiceCount();

            for (int i = 0; i < numDice; i++) {
                dice.add(new Die(6));
            }

            System.out.print("Current hand: ");
            //rolling the dice
            for (int i = 0; i < dice.size(); i++) {
                dice.get(i).roll();
                System.out.print(dice.get(i) + ", ");
            }
            
            System.out.print("\nIndex:        A  B  C  D  E  F\nEnter an index: ");
            
            //checks for farkle and returns if true
            if (meldA.isFarkle(dice)) {
                System.out.println("FARKLE!");
                meldA.setScore(0, round); // Resets the score for the round to 0
                System.out.println("\nTotal score: " + meldA.getScore()); // Retrieve the total score, which has been updated after the farkle
                farkle = true;
                dicePosition = -1;
            }
            
    
            //while loop for adding die to meld array
            while(!farkle){
                stringDicePos = scanner.nextLine();
                stringDicePos = meldA.numToString(stringDicePos);
                dicePosition = Integer.parseInt(stringDicePos);
                System.out.println(dicePosition + " " + stringDicePos);
                if(dicePosition==-1 || dicePosition==-2){
                    break;
                }
                System.out.println("\n\n\n");
                meldA.addToMeld(dice.get(dicePosition));
                dice.set(dicePosition, new Die(0, 0));
                meldA.combineDice();
    
                System.out.print("Current hand: ");
                for(int i = 0; i<dice.size();i++){
                    System.out.print(dice.get(i) + "  ");
                }
                System.out.println("\nIndex:        A  B  C  D  E  F");
                System.out.print("\n\nEnter index to add die to meld or (-1) stop adding die and calculate score");
                System.out.println("Meld score: " + meldA.getScore(round));
            }
            
            //if the user enters -1 this code calculates the score and asks if they want to reroll remaining dice or end their turn
            if(dicePosition == -1 && !farkle){
                meldA.checkMeld(round);//calculates score
                meldA.addLeftoverDice(dice); // this puts the unused meld dice back into your hand
        
                System.out.print("Leftover hand: "); //this is the unmelded dice that the player can reroll
                for(int i = 0; i<dice.size();i++){
                    System.out.print(dice.get(i) + "  ");
                }
        
                System.out.println("\nScore: " + meldA.getScore());
                System.out.println("\nTarget core: " + scoreThreshold);
                
                numberOfZeros = 0;
                for(int i = 0; i<dice.size(); i++){ // this for loop looks for 0's in array and subtracts 1 from numdice for each 0 there is, this is so we can change the number of dice being reroll
                    if(dice.get(i).getSideUp() == 0){
                        numDice -=1;
                        numberOfZeros++;
                    }
                }
                if(numberOfZeros == 6){ //numberOfZeros == 6 means that 6 dice were added and all were melded, meaning it was a hot hand
                    numDice = 6;
                    System.out.println("*******************\nHOT HAND!!! LESSS GOOOOO!");
                }
                if(meldA.getScore() == 0){ //sees if the user quit before adding any meldable dice
                    System.out.println("Ending turn VERY early...");
                    return;
                }
            }

            if(!farkle){
                System.out.println("Enter -1 to end turn early and bank the points or 0 to reroll");
                stringDicePos = scanner.nextLine();
                stringDicePos = meldA.numToString(stringDicePos);
                dicePosition = Integer.parseInt(stringDicePos);
                System.out.println("\n\n");
    
                dice.clear();
            }

            if (dicePosition == -1 && meldA.getScore()<scoreThreshold) {
                System.out.println("Do you want to reroll all dice and start a new round? (Y/N)");
                String rerollChoice = scanner.nextLine();
        
                if (rerollChoice.equalsIgnoreCase("Y")) {
                    numDice = 6;
                    round++;
                    meldA.addScore(0);
                    continue;         
                } 
                else {
                    break;
                }
            }
        }

        if(meldA.getScore()>=scoreThreshold){
            System.out.println("You have exceeded the point threshold!! Les goooo!");
        }
    }
}
