package edu.gonzaga.Farkle;

import java.util.ArrayList;

/**
 * Manages scoring combinations in a Farkle game.
 * Calculates and keeps track of the score.
 */
public class Combos {
   
    private boolean farkle = true;
    private int score = 0;

    public int getScore(){
        return score;
    }

    public Boolean getFarkle(){
        return farkle;
    }

    public void fullHouse(ArrayList<Integer> diceCount){
        boolean hasThree = false;
        boolean hasTwo = false;
        int index1=0, index2=0;
        for(int i = 1; i<diceCount.size(); i++){
            if (diceCount.get(i) >= 3) {
                hasThree = true;
                index1 = i;
            } else if (diceCount.get(i) >= 2) {
                hasTwo = true;
                index2 = i;
            }
        }
        if(hasThree && hasTwo){
            score = score + 1500;
            diceCount.set(index1, diceCount.get(index1)-3);
            diceCount.set(index2, diceCount.get(index2)-2);
        }
    }

    /**
     * Calculates and updates the score for triple dice combinations.
     * param: diceCount a list containing the count of each die value.
     */
    public void triples(ArrayList<Integer> diceCount){
        for(int i = 1; i<diceCount.size(); i++){
            if(diceCount.get(i) >= 3){
                if(i == 1){
                    score+=1000;
                    diceCount.set(i, diceCount.get(i)-3);
                    if(diceCount.get(i)>0){
                        score = score + (100*i);
                        diceCount.set(i, 0);
                        farkle = false;
                    }
                }
                else{
                    int tripleScore = 100*i;
                    score+= tripleScore;
                    diceCount.set(i, diceCount.get(i)-3);
                    farkle = false;
                    if(diceCount.get(i)>0){
                        score = score + (tripleScore*diceCount.get(i));
                        diceCount.set(i, 0);
                    }
                }
            } 
        }
    } 

    /**
     * Calculates and updates the score for three pairs combination.
     */
    public void threePairs(ArrayList<Integer> diceCount){
        int pairCount = 0;
        for(int i = 1; i<diceCount.size(); i++){
            if(diceCount.get(i) >= 2){
                pairCount++;
            }
        }
        if(pairCount ==3){
            score+=750; 
            farkle = false;
            for(int i = 1; i<diceCount.size(); i++){
                diceCount.set(i, 0);
            }
        }
    }

     /**
     * Calculates and updates the score for a straight combination.
     */
    public void straight(ArrayList<Integer> diceCount){
        int count = 0;
        for(int i =1; i<diceCount.size(); i++){
            if(diceCount.get(i) == 1){
                count++;
            }
        }
        if(count == 6){
            score+=1000;
            farkle = false;
            for(int i =1; i<diceCount.size(); i++){
                diceCount.set(i, 0);
            }
        }
    }

    //Calculates and updates the score for single ones combination.
    public void singleOnes(ArrayList<Integer> diceCount){
        if(diceCount.get(1) <=2){
            int add = (100*diceCount.get(1));
            score = score + add;
            farkle = false;
            diceCount.set(1, 0);
        }
    }

    //Calculates and updates the score for single fives combination.
    public void singleFives(ArrayList<Integer> diceCount){
        if(diceCount.get(5) <=2){
            int add = (50*diceCount.get(5));
            score = score + add;
            farkle = false;
            diceCount.set(5, 0);
        }
    }

    //Checks for scoring combinations in the given dice count and updates the score.
    public void check(ArrayList<Integer> diceCount){
        score = 0;
        int diceCountAmount = 0;
        for(int i =1; i<diceCount.size(); i++){
            diceCountAmount += diceCount.get(i);
        }
        
        //this checks if there are 5 or 6 dice in the meld 
        if(diceCountAmount == 5){
            fullHouse(diceCount);
            triples(diceCount);
        }
        else{
            triples(diceCount);
            fullHouse(diceCount);
        }


        threePairs(diceCount);

        straight(diceCount);

        singleOnes(diceCount);

        singleFives(diceCount);
    }

    //Same as check() but sets the score to 0 so nothing is effected when actually checking the melds in game
    public void farkleCheck(ArrayList<Integer> diceCount){
        farkle = false;
        score = 0;

        fullHouse(diceCount);

        triples(diceCount);

        threePairs(diceCount);

        straight(diceCount);

        singleOnes(diceCount);

        singleFives(diceCount);

        if(score == 0){
            farkle = true;
        }
        setScore(0);
    }

    //gives the user the ability to reset the score
    public void setScore(int newScore){
        score = newScore;
    }
    
    //function for debugging purposes, displays the dicecount array
    public void displayHand(ArrayList<Integer> diceCount){
        for (int i = 2; i <= diceCount.size(); i++) { 
            System.out.print(diceCount.get(i - 1) + ", "); 
        }
        System.out.println();
    }

}
