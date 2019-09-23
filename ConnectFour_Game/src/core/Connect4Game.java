/*
 * Karin Whiting
 * (File provided by Professor)
 * COP 3330 Object Oriented Programming
 * University of Central Florida
 */
package core;

import java.awt.Color;
import java.util.ArrayList;
import userInterface.RoundButton;

/**
 *
 * @author kwhiting
 */
public class Connect4Game implements Connect4State
{
    private RoundButton [][] board;
    private int playerToMoveNum; // 0 or 1 for first and second player
    private ArrayList<Player> players; // Array of the two players

    /**
    * Constructs a game given a board
    * @param playerNum the player whose move it is
    * @param thePlayers the player objects
    * @param initBoard the board to copy into this state
    */
    public Connect4Game(int playerNum, ArrayList<Player> thePlayers,            
                        RoundButton [][] initBoard) 
    {
        board = initBoard;
        
        playerToMoveNum = playerNum;
        players = thePlayers;
    }
    
    /**
    * Gets a 2-D array representing the board.
    * The first subscript is the row number and the second the column number.
    * The bottom of the board is row 0 and the top is row Constants.ROW-1.
    * The left side of the board is column 0 and the right side is column COLS-1.
    * @return the board
    */
    public RoundButton [][] getBoard() 
    {
        return board;
    }

    public Connect4State getGame()
    {
        return this;
    }
    
    /**
    * Gets an array holding 2 Player objects
    * @return the players
    */
    public ArrayList<Player> getPlayers() 
    {
        return players;
    }
    
    /**
    * Gets the number of the player whose move it is
    * @return the number of the player whose move it is
    */
    public int getPlayerNum () 
    {
        return playerToMoveNum;
    }
    
    /**
    * Gets the Player whose turn it is to move
    * @return the Player whose turn it is to move
    */
    public Player getPlayerToMove() 
    {
        return players.get(playerToMoveNum);
    }
    
    /**
    * Is this move valid?
    * @param col column where we want to move
    * @return true if the move is valid
    */
    public boolean isValidMove(int col) 
    {
        if (board[Constants.ROW-1][col].getClientProperty("color") == Constants.EMPTY)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    /**
    * Make a move, dropping a checker in the given column
    * @param col the column to get the new checker
    */
    public void makeMove(int col) 
    {
        int r = Constants.ROW - 1;
        
        while (board[r][col].getClientProperty("color") != Constants.EMPTY && r < (Constants.ROW)) 
        {
            if(r == 0)
            {
                if(col < Constants.COL - 1)
                {
                    col++;
                }

                r = 5;
            }   
            else
                r--;
        }
    
        // update buttonBoard
        board[r][col].putClientProperty("color", Constants.COMPUTER);
        // update UI
        board[r][col].setBackground(Constants.COMPUTER);
    }
    
    /**
    * Is the board full?
    * @return true if the board is full
    */
    public boolean isFull() 
    {
        boolean full = false;
        int cell = 0;
        
        for (int c = 0; c < Constants.COL; c++) 
        {
            for (int r = 0; r < Constants.ROW; r++)
            {
                if (board[r][c].getClientProperty("color") != Constants.EMPTY) 
                {
                    cell++;
                }
            }
        }
            
        if(cell == 42)
            full = true;
        
        return full;
    }
    
    /**
    * Decides if the game is over
    * @return true if the game is over
    */
    public boolean gameIsOver() 
    {
        boolean gameOver = false;
    
        if (isFull()) 
        {
            gameOver = true;
        }
        else if (connectFourAnywhere()) 
        {
            gameOver = true;
        }
        return gameOver;
    }
        /**
    * Get the score of a board
    */
    public int score()
    {
        int score = 0;
        
        for (int r = 0; r < Constants.ROW; r++) 
        {
            if (r <= Constants.ROW - 4) 
            {
                for (int c = 0; c < Constants.COL; c++) 
                {
                    score += score(r, c);
                }
            }
            else 
            {
                for (int c = 0; c <= Constants.COL - 4; c++) 
                {
                    score += score(r, c);
                }
            }
        }
    
        return score;
    }
    
    /**
    * Helper method to get the score of a board
    */ 
    public int score(int row, int col)
    {
        int score = 0;
        boolean unblocked = true;
        int tally = 0;
        //int r, c;
        if (row < Constants.ROW - 3) 
        {
            //check up
            unblocked = true;
            tally = 0;

            for (int r = row; r < row + 4; r++) 
            {
                // if the cell is occupied by human color
                if (board[r][col].getClientProperty("color") == Constants.HUMAN)
                {
                    unblocked = false;
                }
        
                // if the cell is occupied by computer color
                if (board[r][col].getClientProperty("color") == Constants.COMPUTER) 
                {
                    tally ++;
                }
            }
        
            if (unblocked == true) 
            {
                score = score + (tally * tally * tally * tally);
            }
        
            if (col < Constants.COL - 3) 
            {
                //check up and to the right
                unblocked = true;
                tally = 0;

                for (int r = row, c = col; r < row + 4; r++, c++) 
                {
                    // if the cell is occupied by human color
                    if (board[r][col].getClientProperty("color") == Constants.HUMAN)
                    {
                        unblocked = false;
                    }

                    // if the cell is occupied by computer color
                    if (board[r][col].getClientProperty("color") == Constants.COMPUTER) 
                    {
                        tally ++;
                    }
                }

                if (unblocked == true) 
                {
                    score = score + (tally * tally * tally * tally);
                }
            }
        }
        
        if (col < Constants.COL - 3) 
        {
            //check right
            unblocked = true;
            tally = 0;
            
            for (int c = col; c < col + 4; c++) 
            {
                // if the cell is occupied by human color
                if (board[row][c].getClientProperty("color") == Constants.HUMAN)
                {
                    unblocked = false;
                }
            
                // if the cell is occupied by computer color
                if (board[row][c].getClientProperty("color") == Constants.COMPUTER) 
                {
                    tally ++;
                }
            }
            
            if (unblocked == true) 
            {
                score = score + (tally * tally * tally * tally);
            }
            
            if (row > 2) 
            {
                //check down and to the right
                unblocked = true;
                tally = 0;
                for (int r = row, c = col; c < col + 4; r--, c++) 
                {
                    // if the cell is occupied by human color
                    if (board[r][col].getClientProperty("color") == Constants.HUMAN)
                    {
                        unblocked = false;
                    }
            
                    // if the cell is occupied by computer color
                    if (board[row][c].getClientProperty("color") == Constants.COMPUTER) 
                    {
                        tally ++;
                    }
                }
            
                if (unblocked == true) 
                {
                    score = score + (tally * tally * tally * tally);
                }
            }
        }
        return score;
    }
    /**
    * Check the board to see if there is a Connect Four anywhere
    * @return true if there is a connect 4 somewhere
    */
    public boolean connectFourAnywhere() 
    {
        boolean connect4 = false;
        for (int r= 0; r < Constants.ROW && connect4 == false; r++) 
        {
            if (r <= Constants.ROW - 4) 
            {
                for (int c = 0; c < Constants.COL && connect4 == false; c++) 
                {
                    connect4 = connectFour(r, c);
                }
            }
            else 
            {
                for (int c = 0; c <= Constants.COL - 4 && connect4 == false; c++) 
                {
                    connect4 = connectFour(r, c);
                }
            }
        }
        return connect4;
    }

    /**
    * Given a row and column, check for a connect 4 from that position
    * @return true if there is a connect 4.
    *
    */
    public boolean connectFour(int row, int col) 
    {
        boolean c4 = false;
        //int r, c;
        if (row < Constants.ROW - 3) 
        {
            //check up
            c4 = true;
            for (int r = row; r < row + 4; r++) 
            {
                if (board[r][col].getClientProperty("color") == Color.WHITE) 
                {
                    c4 = false;
                }
                else if(r < row + 3)
                {
                    if(board[r][col].getClientProperty("color") != board[r + 1][col].getClientProperty("color") )
                    {
                        c4 = false;
                    }
                }
            }
        
            if (col < Constants.COL - 3 && c4 == false) 
            {
                //check up and to the right
                c4 = true;
                
                for (int r = row, c = col; r < row + 4; r++, c++) 
                {
                    if (board[r][c].getClientProperty("color") == Color.WHITE) 
                    {
                        c4 = false;
                    }
                else if(r < row + 3)
                {
                    if(board[r][c].getClientProperty("color") != board[r + 1][c + 1].getClientProperty("color") )
                    {
                        c4 = false;
                    }
                }
                }
            }
        }
        
        if (col < Constants.COL - 3 && c4 == false) 
        {
            //check right
            c4 = true;
            for (int c = col; c < col + 4; c++) 
            {
                if (board[row][c].getClientProperty("color") == Color.WHITE) 
                {
                    c4 = false;
                }
                else if(c < col + 3)
                {
                    if(board[row][c].getClientProperty("color") != board[row][c + 1].getClientProperty("color") )
                    {
                        c4 = false;
                    }
                }
            }
        
            if (row > 2 && c4 == false) 
            {
                //check down and to the right
                c4 = true;
                
                for (int r = row, c = col; c < col + 4; r--, c++) 
                {
                    if (board[r][c].getClientProperty("color") == Color.WHITE) 
                    {
                        c4 = false;
                    }
                    else if(r > 0 && c < col + 3)
                    {
                        if(board[r][c].getClientProperty("color") != board[r - 1][c + 1].getClientProperty("color") )
                        {
                            c4 = false;
                        }
                    }
                }
            }
        }
    
        return c4;
    }
}