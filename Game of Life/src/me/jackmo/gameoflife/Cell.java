package me.jackmo.gameoflife;

/**
 * A class to model a cell in a grid.
 * 
 * @version 2.31
 */
public class Cell
{
    /**
     * Cell default constructor.
     */
    public Cell()
    {
        life = false;
    }
    
    /**
     * Cell constructor.
     * @param aIn     state of cell
     */
    public Cell(boolean aIn)
    {
        life = aIn;
    }
    
    /**
     * A method to make cell live.
     */
    public void live()
    {
        life = true;
    }
    
    /**
     * A method to kill a cell.
     */
    public void die()
    {
        life = false;
    }
    
    /**
     * A method to set the state of the cell.
     * @param aIn     state of cell
     */
    public void setState(boolean aIn)
    {
        life = aIn;
    }
    
    /**
     * Checks if the cell is live.
     * @return       state of cell
     */
    public boolean isLive()
    {
        return life;
    }
    
    private boolean life;
}