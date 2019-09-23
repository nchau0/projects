/*
 * Cadilnis Chau
 * COP 3330 
 * University of Central Florida
 */
package core;

import java.awt.Color; 


public abstract class Player 
{
    private final String playerName;
    private Color color; 
    
    /**
    * @param name player's name
    */
    public Player (String name) 
    {
        playerName = name;
          
    }
    
    /**
    * @return the player's name
    */
    public String getName() 
    {
        return playerName;
    }
    
    /**
    * Gets and returns the player's choice of move
    * @param state current game state
    * @return move chosen by the player, in the range
    * 0 to Connect4State-1
    */
    public abstract int getMove(Connect4State state);

    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    

}    