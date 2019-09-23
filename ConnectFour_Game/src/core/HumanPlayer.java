/*
 * Cadilnis Chau
 * COP 3330 
 * University of Central Florida
 */
package core;

import java.awt.Color; 


public class HumanPlayer extends Player 
{
    /**
    * @param name name of the human player
    */
    public HumanPlayer (String name) 
    {
        super(name);
        this.setColor(Constants.HUMAN);
    }
    

    @Override
    public int getMove(Connect4State state) 
    {
        // Get a move for the user
        return 0;
    }
}
