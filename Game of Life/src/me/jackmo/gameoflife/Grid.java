package me.jackmo.gameoflife;

//Yes... I'm making grid comparable...
/**
 * A grid class to make a grid of Cells and operate on the cells.
 * 
 * @version 2.31
 */
public class Grid implements Comparable<Grid>
{
    /**
     * Grid default constructor.
     *
     */
    public Grid()
    {
        cells = null;
    }
    
    /**
     * Grid constructor, creates a grid
     * @param aIn     Grid height
     * @param bIn     Grid width
     */
    public Grid(int aIn, int bIn)
    {
        cells = new Cell[aIn][bIn];
        for(int i = 0; i < aIn; i++)
        {
            for(int j = 0; j < bIn; j++)
            {
                cells[i][j] = new Cell();
            }
        }
    }
    
    /**
     * Another Grid constructor, takes in a cell array.
     * @param cIn     Cell array
     */
    public Grid(Cell[][] cIn)
    {
        cells = cIn;
    }
    
    /**
     * Returns the width of the grid.
     * @return        grid width
     */
    public int getWidth()
    {
        return cells[0].length;
    }
    
    /**
     * Returns the height of the grid.
     * @return        grid height
     */
    public int getHeight()
    {
        return cells.length;
    }
    
    /**
     * Returns the number of cells on the grid.
     * @return       number of cells
     */
    public int getNumCells()
    {
        return cells.length * cells[0].length;
    }
    
    /**
     * Returns the state of a indicated cell in the grid.
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @return        boolean of state of cell
     */
    public boolean getState(int aIn, int bIn)
    {
        return cells[aIn][bIn].isLive();
    }
    
    /**
     * Returns the cell at the indicated address
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @return        Cell at location
     */
    public Cell getCell(int aIn, int bIn)
    {
        return cells[aIn][bIn];
    }
    
    /**
     * Sets the state of the indicated cell.
     * @param aIn     cell row reference
     * @param bIn     cell column reference
     * @param cIn     cell state
     */
    public void setCell(int aIn, int bIn, boolean cIn)
    {
        cells[aIn][bIn].setState(cIn);
    }
    
    /**
     * Returns the cell array.
     * @returns         the array of cells
     */
    public Cell[][] getGrid()
    {
        return cells;
    }
    
    /**
     * Sets the cell array to an inputed cell array.
     * @param aIn     new cell array
     */
    public void setGrid(Cell[][] aIn)
    {
        cells = aIn;
    }
    
    /**
     * Counts the number of alive cells in the grid.
     */
    public int getTileCount()
    {
        int tileCount = 0;
        
        int row = getHeight();
        int col = getWidth();
        
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if(getState(i, j))
                    tileCount++;
            }
        }
        
        return tileCount;
    }
    
    /**
     * Compares this and another <code>Grid</code> object.
     * <br><b>Precondition</b>: g (or its 2-D <code>Cell</code>) array cannot be <code>null</code>.
     * <br><br> The following <code>compareTo</code> compares such that:
     * <br>     -   the sizes are first compared (row takes higher priority)
     * <br>     -   the co-ordinates of living cells are compared after (row takes higher priority)
     * <br>     This method first checks for the differemce in their areas.
     * <br>     The method then checks for the difference in widths.
     * <br>     Lastly, the method checks for the <b>first occuring</b> difference in a cell's state (reading through rows first).
     * <br>     <b>NOTE</b>: For the last check, a co-ordinate would be represented as a combined x and y as an integer value
     * <br>     (if x and y were 0, the returning value would be <code>1</code>). (Return a negative if the cell of <code>g</code> was true for last check.)
     * 
     * @param g the grid being compared with
     */
    public int compareTo(Grid g)
    {
        int compareValue = 0;
        
        //First check
        compareValue = (getNumCells()) - (g.getNumCells());
        
        if(compareValue == 0)
        {
            //Second check
            compareValue = getWidth() - g.getWidth();
    
            if(compareValue == 0)
            {
                //Final check
                if(getState(0, 0) != g.getState(0, 0))
                    compareValue = 1;
                else
                {
                    int row = getHeight();
                    int col = getWidth();
                    boolean firstOccurance = false;
                    
                    for(int i = 0; i < row; i++)
                    {
                        for(int j = 0; j < col; j++)
                        {
                            if(!firstOccurance)
                            {
                                if(getState(i, j) != g.getState(i, j))
                                {
                                    if(i == 0 && j == 0)
                                        compareValue = 1;
                                    else
                                        compareValue = Integer.parseInt(new Integer(j).toString() + new Integer(i).toString());
                                    
                                    if(g.getState(i, j))
                                        compareValue = -(compareValue);
                                        
                                    firstOccurance = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return compareValue;
    }
    
    /**
     * Uses the implementation of <code>compareTo</code> to define <code>equalsTo</code>.
     * 
     * @Override
     */
    public boolean equals(Object g)
    {
        boolean equals = false;
        
        //Checking for correct object followed by comparison
        if(g instanceof Grid)
            if(compareTo((Grid)g) == 0)
                equals = true;
        
        return equals;
    }
    
    protected Cell[][] cells;
}