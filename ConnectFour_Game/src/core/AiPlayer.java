/*
 * Karin Whiting
 * (File provided by Professor)
 * COP 3330 Object Oriented Programming
 * University of Central Florida
 */
package core;

import userInterface.RoundButton;

/**
 *
 * @author kwhiting
 */
public class AiPlayer extends Player 
{
    private int depth; // Look-ahead depth

    /**
    * Constructs a computer player that uses alpha-beta pruning
    * @param name
    * @param maxDepth
    */
    public AiPlayer(String name, int maxDepth) 
    {
        super(name);
        depth = maxDepth;
        this.setColor(Constants.COMPUTER);

    }
    
    @Override
    public int getMove(Connect4State state) 
    {
        // Returns the computer play's choice using alpha-beta search
        int move = pickMove(state, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE).getMove();
        return move;
    }

    /**
    * Uses game tree search with alpha-beta pruning to pick player's move
    * low and high define the current range for the best move.
    * The current player has another move choice which will get him at least low,
    * and his opponent has another choice that will hold his losses to high.
    *
    * @param state current state of the game
    * @param depth number of moves to look ahead in game tree search
    * @param low a value that the player can achieve by some other move
    * @param high a value that the opponent can force by a different line of play
    * @return the move chosen
    */
    private Connect4Move pickMove(Connect4State state, int depth, int low, int high) 
    {
        // Hold current move and its value
        Connect4Move currentMove; 
        // Hold best move found and its value
        Connect4Move bestMove; 
        // grab the board from the state object
        RoundButton[][] board = state.getBoard();
        // get the current player (really just AiPlayer)
        int playerToMove = state.getPlayerNum();
        // A dummy move that will be replaced when a real move 
        // is evaluated, so the column number is irrelevant
        bestMove = new Connect4Move(Integer.MIN_VALUE, 0);
        
        // Run through possible moves
        for (int c = 0; c < Constants.COL; c++) 
        {
            // do I have a valid column?
            if (state.isValidMove(c)) 
            { 
                // See if legal move
                // Make a scratch copy of state
                Connect4Game copy = new Connect4Game(playerToMove, state.getPlayers(), board);
                 // Make the move on the copy to evaluate 
                copy.makeMove(c);
        
                // check if the game is over on the copy, if yes
                // then make this the currenet move using max instead
                // of min on the best move
                if (copy.gameIsOver()) 
                {
                    currentMove = new Connect4Move(Integer.MAX_VALUE, c);
                }
                else if (playerToMove == copy.getPlayerNum()) 
                { 
                    // Did current player change?
                    currentMove = pickMove(copy, depth, low, high); 
                    // No, so no depth change
                    currentMove.setMove(c);
                    // Remember move made
                }
                else if (depth > 0) 
                { 
                    // Player changed, so reduce search depth
                    currentMove = pickMove(copy, depth - 1, -high, -low);
                    currentMove.setValue(-currentMove.getValue()); 
                    // Good for opponent is bad for me
                    currentMove.setMove(c);
                    // Remember move made
                }
                else
                {
                    // Depth exhausted, so estimate who is winning by comparing kalahs
                    currentMove = new Connect4Move(copy.score(), c);
                }
                    
                if (currentMove.getValue() > bestMove.getValue()) 
                { 
                    // Found a new best move?
                    bestMove = currentMove;
                    low = Math.max(low, bestMove.getValue()); 
                    // Update the low value, also
                }
            }
        }
        return bestMove;
    }
}
