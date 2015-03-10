package me.jackmo.gameoflife;

import javax.swing.JOptionPane;
import java.io.*;

/**
 * Provides the game functionality of G.O.A.L.
 * 
 * @version 2.67
 */
public class Game
{
    /**
     * Creates a blank game. 
     */
    public Game()
    {
        this(0, 0);
    }
    
    /**
     * Sets a games with a preset grid; along with the database and gui.
     */
    public Game(int aIn, int bIn)
    {
        grid = new PlayGrid(aIn, bIn);
        initGrid = new PlayGrid(aIn, bIn);
        base = new Database();
        gui = new GoalGUI(grid);
        
        generationCount = GoalGUI.FIRST_GENERATION;
        
        initialGridFlag = true;
    }
    
    /**
     * Calculates and changes the current grid to the next generation.
     */
    public void run()
    {
        PlayGrid temp = new PlayGrid(grid.getWidth(), grid.getHeight());
        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getHeight(); j++)
            {
                temp.setCell(i,j,grid.calc(i,j));
            }
        }
        grid = temp;
        
        generationCount++;
    }
    
    /**
     * Updates <code>initGrid</code> before the actual grid runs.
     */
    public void updateInitialGrid()
    {
        PlayGrid temp = new PlayGrid(grid.getWidth(), grid.getHeight());
        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getHeight(); j++)
            {
                temp.setCell(i, j, grid.getState(i, j));
            }
        }
        initGrid = temp;
    }
    
    /**
     * Resets the current grid to the previous initial grid before the start function is called.
     */
    public void reset()
    {
        PlayGrid temp = new PlayGrid(grid.getWidth(), grid.getHeight());
        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getHeight(); j++)
            {
                temp.setCell(i,j,initGrid.getState(i,j));
            }
        }
        grid = temp;
        
        generationCount = GoalGUI.FIRST_GENERATION;
    }
    
    /**
     * Clears the current and initial grid entirely.
     */
    public void clear()
    {
        initGrid = new PlayGrid(grid.getWidth(), grid.getHeight());
        grid = new PlayGrid(initGrid.getWidth(), initGrid.getHeight());
        
        generationCount = GoalGUI.FIRST_GENERATION;
    }
    
    /**
     * The save function for the game.
     */
    public void save()
    {        
        //Getting the first set of points
        int[] p1 = gui.displayGetCoordinateDialog(GoalGUI.OPTION_1); 
        
        //Checking for the cancel option (-1, -1) cancels the save
        if(p1[0] >= 0) 
        {
            int[] p2 = gui.displayGetCoordinateDialog(GoalGUI.OPTION_2); 
            if(p2[0] >=0)
            {
                String gridName = gui.displayGetNameDialog();
                
                //Obtaining the x-y values of the points
                int x1 = Math.min(p1[X], p2[X]);
                int y1 = Math.min(p1[Y], p2[Y]);
                int x2 = Math.max(p1[X], p2[X]);
                int y2 = Math.max(p1[Y], p2[Y]);
                
                //Creating save grid
                Grid save = new Grid((y2 - y1) + 1, (x2 - x1) + 1);
                
                //Copying over
                for(int i = y1; i <= y2; i++)
                {
                    for(int j = x1; j <= x2; j++)
                    {
                        save.setCell((i - y1), (j - x1), grid.getState(i, j));
                    }
                }
                
                //Adding to database
                SaveGrid savedGrid = new SaveGrid(save.getGrid(), gridName);
                add(savedGrid);
                gui.updateDataTable(base.getDatabaseInfo());
            }
        }
    }
    
    /**
     * The copy function for the game.
     */
    public void copy()
    {
        //Checks to see if the data table is even selected
        if(!gui.isDataTableNotSelected())
        {
            SaveGrid copy = get(gui.getDataTableIndex());
            
            //Setting loop variables
            int x1 = grid.getWidth(); //x1 and y1 make the top-left corner coordinate that the saved grid will copy over
            int y1 = grid.getHeight();
            boolean tryAgain = false;
            while((x1 + copy.getWidth()) > grid.getWidth() || (y1 + copy.getHeight()) > grid.getHeight())
            {
                int[] p = gui.displayGetCoordinateDialog(GoalGUI.OPTION_3);
                
                //Checking to see if the coordinate is valid
                if((p[0] + copy.getWidth()) > grid.getWidth() || (p[1] + copy.getHeight()) > grid.getHeight())//If not...
                {
                    tryAgain = gui.displayDoesNotAddUp();
                    
                    //If user doesn't want to try again, set x1 and y1 to default value (-1)
                    if(!tryAgain)
                    {
                        x1 = GoalGUI.DEFAULT_COORDINATE_NUM;
                        y1 = GoalGUI.DEFAULT_COORDINATE_NUM;
                    }
                }
                else
                {
                    x1 = p[X];
                    y1 = p[Y];
                }
            }
            
            //Check if user wants to continue (default won't pass through this check)
            if(x1 >= 0 && y1 >= 0)
            {
                //Copying save grid onto main grid
                for(int i = 0; i < copy.getHeight(); i++)
                {
                    for(int j = 0; j < copy.getWidth(); j++)
                    {
                        grid.setCell((i + y1),(j + x1), copy.getState(i, j));
                    }
                }
            }
        }
    }
    
    /**
     * Provides the import mechanism for this game.
     */
    public void importFile()
    {
        //Getting file
        File temp = gui.chooseFile();
        if(temp != null)
        {
            base.importFile(temp,KEY);
        }
        
        //Updating the data table
        gui.updateDataTable(base.getDatabaseInfo());
    }
    
    /**
     * Provides the export mechanism for this game.
     */
    public void exportFile()
    {
        //Checking if data table is selected
        if(!gui.isDataTableNotSelected())
        {
            int index = gui.getDataTableIndex();
            base.exportFile(index, KEY);
        }
    }
    
    /**
     * Adds a <code>SaveGrid</code> to the database.
     * 
     * @param the grid being saved into
     */
    public void add(SaveGrid aIn)
    {
        base.add(aIn);
    }
    
    /**
     * Removes a <code>SaveGrid</code> from the database.
     * 
     * @param the index corresponding to the <code>SaveGrid</code> in the database being removed
     */
    public void remove(int aIn)
    {
        base.remove(aIn);
    }
    
    /**
     * Gets a <code>SaveGrid</code> from the database.
     * 
     * @param the index within the database to get the <code>SaveGrid</code>
     */
    public SaveGrid get(int aIn)
    {
         return base.get(aIn);
    }
    
    /**
     * The HEART of G.O.A.L. Starts the program for the applet.
     */
    public void runProgram()
    {
        //Checking for start function, no other function should be running with this
        if(gui.getStartState())
        {
            //Initial grid gets updated once before the program runs 
            if(initialGridFlag)
            {
                initialGridFlag = false;
                updateInitialGrid();
            }
            run();
        }
        else
        {
            if(gui.getStepState())
                run();
            else if(gui.getResetState())
                reset();
            else if(gui.getClearState())
                clear();
            else if(gui.getSaveState())
                save();
            else if(gui.getCopyState())
                copy();
            else if(gui.getImportState())
                importFile();
            else if(gui.getExportState())
                exportFile();
            
            initialGridFlag = true;
        }
        
        gui.update(grid, generationCount);
        gui.disableFunctions();
    }
    
    /**
     * Used to add the gui to the applet.
     * 
     * @return <code>gui</code>
     */
    public GoalGUI addGUI()
    {
        return gui;
    }
    
    PlayGrid initGrid;
    PlayGrid grid;
    GoalGUI gui;
    Database base;
    
    
    int generationCount;
    boolean initialGridFlag; /* This variable is used for updating the initial grid when the program runs.
                              * It assists in timing the reset function for this program.
                              */
    private final String KEY = "12";
    private final int X = 0;
    private final int Y = 1;
                             
}	