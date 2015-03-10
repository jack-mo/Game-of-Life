package me.jackmo.gameoflife;

/**
 * A subclass of Grid, such that it is used for the playing field.
 * 
 * @version 2.31
 */
public class PlayGrid extends Grid
{
    /**
     * PlayGrid default constructor.
     */
    public PlayGrid()
    {
        super();
    }
    
    /**
     * PlayGrid constructor with given dimenstions.
     * @param aIn     grid height
     * @param bIn     grid width
     */
    public PlayGrid(int aIn, int bIn)
    {
        super(aIn, bIn);
    }
    
    /**
     * Checks if a given cell is valid, or is it on the screen.
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @return        true if on the grid, false otherwise
     */
    public boolean isValid(int aIn, int bIn)
    {
        try
        {
            Cell temp = cells[aIn][bIn];
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    /**
     * Returns the number of neighbors of the indicated cell.
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @return        Integer of neighbors
     */
    public int getNeighbors(int aIn, int bIn)
    {
        int num = 0;
        if(isValid(aIn-1, bIn-1) && cells[aIn-1][bIn-1].isLive())
        {
            num ++;
        }
        if(isValid(aIn-1, bIn) && cells[aIn-1][bIn].isLive())
        {
            num ++;
        }
        if(isValid(aIn-1, bIn+1) && cells[aIn-1][bIn+1].isLive())
        {
            num ++;
        }
        if(isValid(aIn, bIn-1) && cells[aIn][bIn-1].isLive())
        {
            num ++;
        }
        if(isValid(aIn, bIn+1) && cells[aIn][bIn+1].isLive())
        {
            num ++;
        }
        if(isValid(aIn+1, bIn-1) && cells[aIn+1][bIn-1].isLive())
        {
            num ++;
        }
        if(isValid(aIn+1, bIn) && cells[aIn+1][bIn].isLive())
        {
            num ++;
        }
        if(isValid(aIn+1, bIn+1) && cells[aIn+1][bIn+1].isLive())
        {
            num ++;
        }
        return num;
    }
    
    /**
     * Calculates the state of the indicated cell in the next generation by these rules:
     * <br>     if it is surrounded by 3 cells, it is live.
     * <br>     if it is not surrounded by 2 or 3 cells, it dies
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @return        cell state for next generation
     */
    public boolean calc(int aIn, int bIn)
    {
        boolean temp = cells[aIn][bIn].isLive();
        if(getNeighbors(aIn, bIn) == 3)
        {
            temp = true;
        }
        else if(getNeighbors(aIn, bIn) != 2)
        {
            temp = false;
        }
        return temp;
    }
}