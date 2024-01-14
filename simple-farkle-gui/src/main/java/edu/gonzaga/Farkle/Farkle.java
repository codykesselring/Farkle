/*
 * Cody Kesselring
 * Farkle HW4
 * 12/08/2023
 This program plays 1 round of farkle through the GUI, when you press "roll dice" the initial roll is instantiated and the player can check the boxes they want melded
 the player can bank theyre points and choose to reroll if they'd like. if there is no meldable dice, a window stating there is a farkle appears and the program ends when the box is closed
 */
package edu.gonzaga.Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// import com.github.dnsev.identicon.Identicon;

// Java Swing based Farkle frontend
// Add/edit/change as you see fit to get it to play Yahtzee!
class Farkle {
    // If you have your HW3 code, you could include it like this, as needed
    String player;
    Meld playerMeld;
    int[] diceArr = new int[6];
    int numDice = 6;
    Boolean isFarkle = false;
    //Meld meld;
    //Hand hand;

    // Main game GUI window and two main panels (left & right)
    JFrame mainWindowFrame;
    JFrame endGameFrame;
    JPanel controlPanel;
    JPanel scorecardPanel;

    // Dice view, user input, reroll status, and reroll button
    JTextField diceValuesTextField;
    JTextField diceKeepStringTextField;
    JButton diceRerollBtn;
    JTextField rerollsLeftTextField;

    // Player name - set it to your choice
    JTextField playerNameTextField = new JTextField();

    // Buttons for showing dice and checkboxes for meld include/exclude
    ArrayList<JButton> diceButtons = new ArrayList<>();
    ArrayList<JCheckBox> meldCheckboxes = new ArrayList<>();

    JButton calcMeldButton = new JButton("Bank Points");
    JTextField diceDebugLabel = new JTextField();
    JLabel meldScoreTextLabel = new JLabel();
    JLabel meldRoundScoreTextLabel = new JLabel();
    JButton rollButton = new JButton();

    JPanel playerInfoPanel = new JPanel();
    JPanel diceControlPanel = new JPanel();
    JPanel meldControlPanel = new JPanel();

    DiceImages diceImages = new DiceImages("media");


    public static void main(String [] args) {
        Farkle app = new Farkle();    // Create, then run GUI
        app.runGUI();
    }

    // Constructor for the actual Farkle object
    public Farkle() {
        this.playerMeld = new Meld();
        // Create any object you'll need for storing the game:
        // Player, Scorecard, Hand/Dice
    }

    // Sets up the full Swing GUI, but does not do any callback code
    void setupGUI() {
        // Make and configure the window itself
        this.mainWindowFrame = new JFrame("Cody's GUI Farkle");
        this.mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainWindowFrame.setLocation(100,100);
        
        // Player info and roll button panel
        this.playerInfoPanel = genPlayerInfoPanel();

        // Dice status and checkboxes to show the hand and which to include in the meld
        this.diceControlPanel = genDiceControlPanel();

        // The bottom Meld control panel
        this.meldControlPanel = genMeldControlPanel();

        mainWindowFrame.getContentPane().add(BorderLayout.NORTH, this.playerInfoPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.CENTER, this.diceControlPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.SOUTH, this.meldControlPanel);
        mainWindowFrame.pack();
    }

    /**
     * Generates and returns a JPanel containing components for meld control.
     *
     * This method creates a JPanel with a flow layout. It includes components such as a label
     * for meld score, a button to calculate meld, and a label to display the meld score. 
     *
     * @return A JPanel containing components for meld control.
     */
    private JPanel genMeldControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());

        JLabel meldScoreLabel = new JLabel("Meld Score:");
        this.meldScoreTextLabel.setText("0");

        JLabel meldRoundScoreLabel = new JLabel("Total Score:");
        this.meldRoundScoreTextLabel.setText("0");
        
        JButton rerollButton = new JButton("Reroll");
        JTextField totalScoreTextField = new JTextField("0", 5);
        totalScoreTextField.setEditable(false);

        newPanel.add(rerollButton);
        
        newPanel.add(calcMeldButton);
        newPanel.add(meldScoreLabel);
        newPanel.add(this.meldScoreTextLabel);
        newPanel.add(meldRoundScoreLabel);
        newPanel.add(this.meldRoundScoreTextLabel);

        rerollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerMeld.clearMelds();
                meldScoreTextLabel.setText("0");
                for (int i = 0; i < meldCheckboxes.size(); i++) {
                    JCheckBox checkBox = meldCheckboxes.get(i);
                    if (checkBox.isSelected()) {
                        // Dice was not banked, so decrement the numDice and update the display
                        numDice--;
                        diceButtons.get(i).setText("");
                        diceButtons.get(i).setIcon(null);
                        checkBox.setSelected(false);

                    }
                    else if (!checkBox.isSelected() || diceButtons.get(i).getIcon() == null) {
                        // Dice was not banked, so reroll it
                        Random random = new Random();
                        diceArr[i] = random.nextInt(6) + 1;
                        diceButtons.get(i).setText(String.valueOf(diceArr[i]));
                        diceButtons.get(i).setIcon(diceImages.getDieImage(diceArr[i]));
                        diceButtons.get(i).setText(String.valueOf(diceArr[i]));
                        playerMeld.addToMeld(diceArr[i]);
                    }
                }
                playerMeld.combineDice();
                isFarkle = playerMeld.isFarkle();
                playerMeld.clearMelds();
                if(isFarkle){
                    endGameFrame = new JFrame("Farkle! Game Over");
                    endGameFrame.setSize(300, 150);
                    endGameFrame.setLocationRelativeTo(mainWindowFrame);
                    endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                    JLabel messageLabel = new JLabel("Farkle! No scoring combinations. Game over.");
                    endGameFrame.add(messageLabel);
                }
                endGameFrame.setVisible(true);
            }
        });
    
        calcMeldButton.addActionListener(new ActionListener() { //this is for banking, just repurposed the button that was already there
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle bank logic
                // This can include updating the total score, clearing the meld, and starting a new round
                meldRoundScoreTextLabel.setText(Integer.toString(playerMeld.bankedScore()));
                playerMeld.clearTempScore();
            }
        });

        return newPanel;
    }


    /**
     * Generates and returns a JPanel containing components for dice control.
     *
     * This method creates a JPanel with a black border and a grid layout (3 rows, 7 columns).
     * It includes components such as labels for dice values and meld options, buttons for each
     * dice, and checkboxes for melding. The dice buttons and meld checkboxes are added to
     * corresponding lists for further manipulation.
     *
     * @return A JPanel containing components for dice control.
     */
    private JPanel genDiceControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new GridLayout(3, 7, 1, 1));
        JLabel diceLabel = new JLabel("Dice Vals:");
        JLabel meldBoxesLabel = new JLabel("Meld 'em:");
    
        newPanel.add(new JPanel());  // Upper left corner is blank
        for (int index = 0; index < 6; index++) {
            JLabel colLabel = new JLabel(Character.toString('A' + index), SwingConstants.CENTER);
            newPanel.add(colLabel);
        }
        newPanel.add(diceLabel);
    
        for (int index = 0; index < numDice; index++) {
            JButton diceStatusButton = new JButton("D" + index);
            this.diceButtons.add(diceStatusButton);
            newPanel.add(diceStatusButton);
            newPanel.setPreferredSize(new Dimension(40, 120));
        }
    
        newPanel.add(meldBoxesLabel);
        for (int index = 0; index < 6; index++) {
            JCheckBox meldCheckbox = new JCheckBox();
            meldCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
            this.meldCheckboxes.add(meldCheckbox);
            newPanel.add(meldCheckbox);
        }
    
        return newPanel;
    }

    /**
     * Generates and returns a JPanel containing player information components.
     *
     * This method creates a JPanel with a black border and a horizontal flow layout.
     * It includes components such as a JLabel for player name, a JTextField for entering
     * the player name, a JButton for rolling dice, and a debug label for dice information.
     * The player name text field, dice debug label, and roll button are added to the panel
     * with appropriate configurations.
     *
     * @return A JPanel containing components for player information.
     */
    private JPanel genPlayerInfoPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new FlowLayout());    // Left to right

        JLabel playerNameLabel = new JLabel("Player name:");
        playerNameTextField.setColumns(20);
        diceDebugLabel.setColumns(6);
        diceDebugLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        rollButton.setText("Roll Dice");


        newPanel.add(playerNameLabel);   // Add our player label
        newPanel.add(playerNameTextField); // Add our player text field
        newPanel.add(rollButton);        // Put the roll button on there
        newPanel.add(this.diceDebugLabel);

        return newPanel;
    }


    /*
     *  This is a method to show you how you can set/read the visible values
     *   in the various text widgets.
     */
    private void putDemoDefaultValuesInGUI() {
        // Example setting of player name
        this.playerNameTextField.setText("DEFAULT_PLAYER");
        this.player = playerNameTextField.getText();
        // Example Dice debug output
        this.diceDebugLabel.setText("");
    }

    /*
     * This is a demo of how to add callbacks to the buttons
     *  These callbacks can access the class member variables this way
     */
    private void addDemoButtonCallbackHandlers() {
        // Example of a button callback - just prints when clicked
        this.rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("They clicked the roll button!");
                diceButtons.get(2).setText("Rolled");
                String diceValueLable = "";
                Random random = new Random();                
                for(int i=0; i<numDice; i++){
                    diceArr[i] = random.nextInt(6) + 1;
                    diceValueLable += diceArr[i];
                }
                diceDebugLabel.setText(diceValueLable);

                for(int i = 0; i < 6; i++) {
                    diceButtons.get(i).setText(String.valueOf(diceArr[i]));
                }
                
                //adds the images to dice
                for (int i = 0; i < 6; i++) {
                    JButton diceButton = diceButtons.get(i);
                    diceButton.setIcon(diceImages.getDieImage(diceArr[i]));
                    diceButtons.get(i).setIcon(diceImages.getDieImage(diceArr[i]));
                }
                
                //for automatically calculating melds when checkbox is pressed
                for (int i = 0; i < meldCheckboxes.size(); i++) {
                    final int index = i;
                    JCheckBox checkBox = meldCheckboxes.get(i);
                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (checkBox.isSelected()) {
                                // Checkbox is checked
                                int diceValue = diceArr[index];
                                playerMeld.addToMeld(diceValue);
                            } else {
                                // Checkbox is unchecked
                                int diceValue = diceArr[index];
                                playerMeld.removeFromMeld(diceValue);
                            }
            
                            playerMeld.combineDice();
                            playerMeld.checkMeld();
                            System.out.println("Setting meld score text");
                            meldScoreTextLabel.setText(Integer.toString(playerMeld.getTempScore()));
                        }
                    });
                }
            }
        });

        // Example of another button callback
        // Reads the dice checkboxes and counts how many are checked (selected)
        // Sets the meldScoreTextLabel to how many of the checkboxes are checked
        /*this.calcMeldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer boxCheckCount = 0;
                for (int i = 0; i < meldCheckboxes.size(); i++) {
                    JCheckBox checkBox = meldCheckboxes.get(i);
                    if (checkBox.isSelected()) {
                        int diceValue = diceArr[i];  // Assuming diceArr[] contains your dice values
                        playerMeld.addToMeld(diceValue);
                    }
                }
                playerMeld.combineDice();
                playerMeld.checkMeld();
                System.out.println("Setting meld score text");
                meldScoreTextLabel.setText(Integer.toString(playerMeld.getScore()));
            }
        });*/

        // Example of a checkbox handling events when checked/unchecked
        JCheckBox boxWithEvent = this.meldCheckboxes.get(1);
        boxWithEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(boxWithEvent.isSelected()) {
                    System.out.println("Checkbox is checked");
                } else {
                    System.out.println("Checkbox is unchecked");
                }
            }
        });
    }

    /*
     *  Builds the GUI frontend and allows you to hook up the callbacks/data for game
     */
    void runGUI() {
        System.out.println("Starting GUI app");
        setupGUI();

        // These methods are to show you how it works
        // Once you get started working, you can comment them out so they don't
        //  mess up your own code.
        putDemoDefaultValuesInGUI();
        addDemoButtonCallbackHandlers();

        // Right here is where you could methods to add your own callbacks
        // so that you can make the game actually work.

        // Run the main window - begins GUI activity
        mainWindowFrame.setVisible(true);
        System.out.println("Done in GUI app");
    }

}
