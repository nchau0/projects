/*
 * Cadilnis Chau
 * COP 3330 
 * University of Central Florida
 */
package userInterface;

import core.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Connect4Ui extends JFrame 
{
    // Menu components
    private static ArrayList<Player> players; 
    private Player currentPlayer; 
    
    
    private static JMenuBar menuBar;    
    private static JMenu gameMenu;
    private static JMenuItem playerPlayer;
    private static JMenuItem playerComputer;
    private static JMenuItem computerComputer;	
    private static JMenuItem exit;	

    // event handlers
    private GameListener gameListener;

    // Connect 4 JPanel
    private Connect4Panel boardPanel;
    
    
    public Connect4Ui(ArrayList<Player> players)
    {
        this.players = players; 
        initComponents();   
    }
    
    public Connect4Ui()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        this.setTitle("Connect Four");		
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600, 600));
        this.setBackground(Color.BLUE);

        //create menubar, menu, submenu, and menuitems
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);

        //game menu
        gameListener = new GameListener();
        
        playerPlayer = new JMenuItem("Player vs. Player");		
        // registering the action listener
        playerPlayer.addActionListener(gameListener);
        playerPlayer.setEnabled(false);
        gameMenu.add(playerPlayer);

        playerComputer = new JMenuItem("Player vs. Computer");
        playerComputer.addActionListener(gameListener);
        playerComputer.setSelected(true);
        gameMenu.add(playerComputer);

        computerComputer = new JMenuItem("Computer vs. Computer");
        computerComputer.addActionListener(gameListener);
        computerComputer.setEnabled(false);
        gameMenu.add(computerComputer);

        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);	

        // create the JPanel for the buttons
        boardPanel = new Connect4Panel();
        
        this.add(getBoardPanel(), BorderLayout.CENTER);
        this.setVisible(true);
    }


    public Connect4Panel getBoardPanel() {
       return boardPanel;
    }

    // exit menu item selected
    private class ExitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int response = JOptionPane.showConfirmDialog(null, "Exit Connect Four?", 
                    "Exit?", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION)
                System.exit(0);	
        }	
    }

    //listener for game menu selection		
    private class GameListener implements ActionListener
    {	
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Player vs. Player"))
            {
            }
            else if(e.getActionCommand().equals("Player vs. Computer"))
            {
            }
            else if(e.getActionCommand().equals("Computer vs. Computer"))
            {
            }
        }	
    }	

}
