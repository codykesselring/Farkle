enter the folder farkle-command-line\src\main\java\edu\gonzaga\Farkle, within this folder you will you find a file named 'Farkle.Java' which contains the main function where the code can be run.
Here is the scoring guide to Farkle:
![Scoring](https://github.com/codykesselring/Farkle/assets/115512973/0112d30b-2f8e-4647-b2bb-0fa0799415a2)

Initialization: The game starts by initializing variables such as the round number, number of dice, and creating objects for managing the game, such as Meld and Combos. The player is prompted to set a point limit and enter their name.

Main Game Loop: The main game loop runs until the player's score exceeds the specified point limit. Within this loop:
        The player's current hand of dice is displayed, and they're prompted to select dice to add to their meld.
        The player can choose dice to add to their meld by entering the index of the dice they want to select.
        If the player chooses to end their turn early or if they roll a Farkle (a combination of dice that scores no points), the appropriate action is taken.
        After each turn, the player is given the option to either end their turn and bank their points or reroll the remaining dice.
        if the dice are rerolled, the previously melded dice are removed from the dice pool, and if there are no possible meldable dice in the new hand, the round ends and the player is rewarded no points.
        If the player chooses to end their turn and bank their points, they can decide whether to start a new round with all dice or end the game.

Game End: The game ends when the player's score exceeds the specified point limit. They are then notified that they have won the game.
