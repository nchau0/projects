/*
 * Cadilnis Chau
 * COP 3330 
 * University of Central Florida
 */
package connectfour;

import core.AiPlayer;
import core.HumanPlayer;
import core.Player;
import core.Constants;
import core.Connect4Game;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userInterface.Connect4Ui;


public class ConnectFour 
{
    private static ArrayList<Player> players; 
    private static Connect4Ui frame;

    public static void main(String[] args) 
    {
            

        
        //build the UI 
        frame = new Connect4Ui(getPlayers());  
        // create the players
        makePlayers();
        //play method
        play();
    }

    public static void makePlayers() 
    {
        // instantiate the players object
        players = new ArrayList<Player>();
        
        // get human player name
        String name = JOptionPane.showInputDialog("Enter your name");
        // instantiate the human player
        Player humanPlayer = new HumanPlayer(name);
        
        // instantiate the computer player
        // default value for now
        int depth = 0;

        Player aiPlayer = new AiPlayer("Computer", depth);
        
        // add players to ArrayList
        players.add(humanPlayer);
        players.add(aiPlayer);
    }        

    private static ArrayList<Player> getPlayers() {
        return players;
    }
    
    private static void play() {

       //human goes first 
        Player currentPlayer = players.get(Constants.TWO);         
        //hold current game state 
        Connect4Game state = new Connect4Game(0, players, frame.getBoardPanel().getButtonBoard());                
        //current player is human, change to computer        
        while (!state.gameIsOver())
        {
              //current player is human 
              if(currentPlayer == players.get(Constants.ONE))
            {
                currentPlayer = players.get(Constants.TWO);
                JOptionPane.showMessageDialog(null, "Computer's turn!");
                int move = state.getPlayerToMove().getMove(state);
        System.out.println("ConnectFour calling makeMove");
                state.makeMove(move);
            }
                else
                {  
                  currentPlayer = players.get(Constants.ONE);
                  JOptionPane.showMessageDialog(null, "Player's turn!");    
                } 
                try
                {
                    Thread.sleep(2000);
                }
                
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }

        JOptionPane.showMessageDialog(null, ("Game Over"));
        if (state.isFull()){
            JOptionPane.showMessageDialog(null, "Draw Game");}
        else
        {
         JOptionPane.showMessageDialog(null, (currentPlayer.getName() + " wins!"));
        }
        
    }
}