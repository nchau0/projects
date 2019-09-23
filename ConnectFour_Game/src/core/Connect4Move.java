/*
 * Cadilnis Chau
 * COP 3330 Object Oriented Programming
 * University of Central Florida
 */
package core;


public class Connect4Move 
{
    private int value; // Game value of this move
    private int move; // Number of pit to be emptied

    public Connect4Move(int value, int move)
    {
        this.value = value;
        this.move = move;
    }    

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the move
     */
    public int getMove() {
        return move;
    }

    /**
     * @param move the move to set
     */
    public void setMove(int move) {
        this.move = move;
    }
}
