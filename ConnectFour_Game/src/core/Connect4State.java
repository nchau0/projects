/*
 * Cadilnis Chau
 * COP 3330 
 * University of Central Florida
 */
package core;

import java.util.ArrayList;
import userInterface.RoundButton;



public interface Connect4State 
{
    /**
    * Gets a 2-D array representing the board.
    * The first subscript is the row number and the second the column number.
    * The bottom of the board is row 0 and the top is row ROWS-1.
    * The left side of the board is column 0 and the right side is column COLS-1.
    * @return the board
    */
    
    public RoundButton [][] getBoard();

    /**
    * Gets an array holding 2 Player objects
    * @return the players
    */
    public ArrayList<Player> getPlayers();
    
    /**
    * Gets the number of the player whose move it is
    * @return the number of the player whose move it is
    */
    public int getPlayerNum();

    /**
    * Gets the Player whose turn it is to move
    * @return the Player whose turn it is to move
    */
    public Player getPlayerToMove();

    /**
    * Is this move valid?
    * @param col column where we want to move
    * @return true if the move is valid
    */
    public boolean isValidMove(int col);

    /**
    * Make a move, dropping a checker in the given column
    * @param col the column to get the new checker
    */
    public void makeMove(int col);

    /**
    * Is the board full?
    * @return true if the board is full
    */
    public boolean isFull();

    /**
    * Decides if the game is over
    * @return true if the game is over
    */
    public boolean gameIsOver();
}