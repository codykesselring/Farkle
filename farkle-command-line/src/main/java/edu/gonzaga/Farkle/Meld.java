package edu.gonzaga.Farkle;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Meld of dice combinations in a Farkle game.
 * Manages and calculates the score of the Meld.
 */
public class Meld {
    
    //use Collections.sort(arraylist<int...>);
    ArrayList<Die> melds = new ArrayList<>();
    ArrayList<Integer> diceCount = new ArrayList<>();
    Combos combo = new Combos();

    ArrayList<Integer> score = new ArrayList<>();
    public static int numDice = 6;
    
    //Initializes a new instance of the Meld class.
    public Meld(){
        score.add(0);
    }

    //sets the score for the round to 0
    public void clearScore(){
        for(int i = 0; i<score.size();i++){
            score.set(i, 0);
        }
    }

    //adds a die to meld class
    public void addToMeld(Die die){
        melds.add(die);
    }

    //clears the melds array
    public void clearMelds(){
        melds.clear();
    }
    
    //clears diceCount array
    public void clearDiceCount(){
        diceCount.clear();
    }

    /**
     * Checks the Meld for valid scoring combinations.
     * Updates the score based on the combinations found.
     */
    public void checkMeld(int round){
        combo.check(diceCount);
        score.set(round, combo.getScore());
    }

     /**
     * Combines the dice in the Meld and calculates the count of each die value.
     * Used to check for scoring combinations.
     */
    public void combineDice() {
        diceCount.clear();
        while (diceCount.size() <= numDice) {
            diceCount.add(0);
        }
        for (int i = 0; i < melds.size(); i++) {
            Die myDie = melds.get(i); // Use melds ArrayList
            Integer dieValue = myDie.getSideUp();
            
            // Check if the diceCount ArrayList has enough elements, and add more if needed
            
            diceCount.set(dieValue, diceCount.get(dieValue) + 1);
        }
    
        displayMeld(diceCount);
    }
    
    //Displays the current Meld and the count of each die value.
    public void displayMeld(ArrayList<Integer> diceCount){ // **********change this so it displays a variable for testing purposes******** string x = System.out.print(diceCount.get(i - 1) + "  ";
        System.out.print("Meld hand:    ");
        for (int i = 2; i <= diceCount.size(); i++) { // Start at index 2 and go up to the size of the array
            System.out.print(diceCount.get(i - 1) + "  "); // Subtract 2 to access the correct index
        }
        System.out.print("\nDie side up:  ");
        for (int i = 1; i < diceCount.size(); i++){
            System.out.print(i + "  ");
        }
         System.out.println("\n-------------------------------");
    }

    //Adds leftover dice within the Meld arraylist and adds them to the dice arraylist (the users hand)
    public void addLeftoverDice(ArrayList<Die> dice) {
        for (int i = 1; i < diceCount.size(); i++) {
            int count = diceCount.get(i);
            while (count > 0) {
                for (int j = 0; j < dice.size(); j++) {
                    if (dice.get(j).getSideUp() == 0) {
                        dice.set(j, new Die(1, i)); // Add a die with value i
                        count--;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if the current Meld is a Farkle (no scoring combinations).
     *
     * takes dice as a param: A list of dice to check for Farkle.
     * returns true if it's a Farkle, false otherwise.
     */
    public boolean isFarkle(ArrayList<Die> dice) {
        
        ArrayList<Die> copyOfDice = new ArrayList<>(dice);
        int[] counts = new int[7];
        for (Die die : copyOfDice) {
            int sideUp = die.getSideUp();
            counts[sideUp]++;
        }

        Combos combo = new Combos();
        ArrayList<Integer> diceCount = new ArrayList<>();
        for (int count : counts) {
            diceCount.add(count);
        }
        combo.farkleCheck(diceCount);
        return combo.getFarkle();
    }

    //returns the size of the melds array
    public Integer size(){
        return melds.size();
    }

    //returns the total score of all rounds
    public Integer getScore(){
        int totalScore = 0;
        for(int i = 0; i<score.size(); i++){
            totalScore+= score.get(i);
        }
        return totalScore;
    }

    //getScore override for a specific round
    public Integer getScore(int round){
        int roundScore = 0;
        roundScore = score.get(round);
        return roundScore;
    }

    //gives the ability to reset the score
    public void setScore(int newScore, int index){
        score.set(index, newScore);
    }

    //this adds a score, used to resize the arraylist so a setting a value doesn't produce an out of bounds error
    public void addScore(int newScore){
        score.add(newScore);
    }

    //displays game banner to console
    public void gameBanner(){
        
        System.out.println("************************************************************************\n*            Zag Farkle by Cody Kesselring!            \n*            Copyright: 2023            \n************************************************************************\n\n");
    }
    
    //allows user to name themselves in the game
    public void playerName(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert player name: ");
        String playerName = scanner.nextLine();
        if(playerName.trim().isEmpty()){
            playerName = "Unknown Name";
        }
        String playerPhrase = playerName + ", it's your turn! Rolling dice...";
        System.out.println(playerPhrase);
     }
     
     public int scoreThreshold(){
        int scoreThreshold;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many points would you like to play to?");
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            scoreThreshold = 10000;
        } else {
            scoreThreshold = Integer.parseInt(input);
        }
        return scoreThreshold;
     } 

     //converts the letters into indexable numbers to later be converted into ints in main
     public String numToString(String index){
        if(index.equalsIgnoreCase("A")){
            index = "0";
        }
        else if(index.equalsIgnoreCase("B")){
            index = "1";
        } 
        else if(index.equalsIgnoreCase("C")){
            index = "2";
        }
        else if(index.equalsIgnoreCase("D")){
            index = "3";
        }
        else if(index.equalsIgnoreCase("E")){
            index = "4";
        }
        else if(index.equalsIgnoreCase("F")){
            index = "5";
        }
        
        return index;
     }
}
