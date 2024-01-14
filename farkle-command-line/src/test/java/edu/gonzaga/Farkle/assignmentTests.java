package edu.gonzaga.Farkle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class assignmentTests {
    @Test
    void testFullHouse(){
        Combos combos = new Combos();
        ArrayList<Integer> diceCount = new ArrayList<>();
        
        diceCount.add(0);
        diceCount.add(3);
        diceCount.add(2);

        combos.fullHouse(diceCount);

        assertTrue(combos.getScore() == 1500);
    }

    @Test
    void testStraight(){
        Combos combos = new Combos();
        ArrayList<Integer> diceCount = new ArrayList<>();
        
        diceCount.add(1);
        diceCount.add(1);
        diceCount.add(1);
        diceCount.add(1);
        diceCount.add(1);
        diceCount.add(1);
        diceCount.add(1);

        combos.straight(diceCount);

        assertTrue(combos.getScore() == 1000);
    }

    @Test
    void testThreePair(){
        Combos combos = new Combos();
        ArrayList<Integer> diceCount = new ArrayList<>();
        
        diceCount.add(0);
        diceCount.add(2);
        diceCount.add(2);
        diceCount.add(2);

        combos.threePairs(diceCount);

        assertTrue(combos.getScore() == 750);
    }

    @Test
    void farkleTest(){
        Combos combos = new Combos();
        ArrayList<Integer> diceCount = new ArrayList<>();

        diceCount.add(0);
        diceCount.add(0);
        diceCount.add(0);
        diceCount.add(0);
        diceCount.add(0);
        diceCount.add(0);
        diceCount.add(0);

        combos.farkleCheck(diceCount);

        assertTrue(combos.getFarkle());
    }

    @Test
    void nameTest(){ //used stack overflow to figure out ByteArrayInputStream, java.io.ByteArrayOutputStream, InputStream, and PrintStream;
        String name = "Cody";
        Meld meld = new Meld();
        
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        System.setIn(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        meld.playerName();

        String consoleOutput = outputStream.toString().trim();
        String expectedOutput = "Insert player name: Cody, it's your turn! Rolling dice...";
        assertEquals(expectedOutput, consoleOutput);
    }

    @Test
    void noNameTest(){ 
        String name = "\n";
        Meld meld = new Meld();
        
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        System.setIn(inputStream);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        meld.playerName();

        String consoleOutput = outputStream.toString().trim();
        String expectedOutput = "Insert player name: Unknown Name, it's your turn! Rolling dice...";
        assertEquals(expectedOutput, consoleOutput);
    }
}