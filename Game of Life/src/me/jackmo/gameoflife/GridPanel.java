package me.jackmo.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This <code>GridPanel</code> class represents the main grid of the game. 
 * 
 * @version 2.67
 */
public class GridPanel extends JPanel
{       
        /**
         * Creates a <code>GridPanel</code> object with default sizes and a grid set.
         * 
         * @param gIn       the grid for this panel
         */
        public GridPanel(PlayGrid gIn)
        {
            //Intializing the cell size, the grid, and the grid panel
            super(); 
            setPreferredSize(new Dimension(DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE));
            
            cellSize = DEFAULT_CELL_SIZE;
            
            grid = gIn;
            
            wCellNum = grid.getWidth();
            hCellNum = grid.getHeight();
            
            curRow = 0;
            curCol = 0;
            
            tileCount = 0;            
            
            //Adding and enabling mouse listeners to the grid panel
            ears = new MListener();
            addMouseListener(ears);
            addMouseMotionListener(ears);
            
            enableListener = true;
        }
        
        /**
         * Sets a new <code>PlayGrid</code> for the panel.
         * 
         * @param gIn       new grid being set into this panel
         */
        public void setNewGrid(PlayGrid gIn)
        {
            grid = gIn;
        }
        
        /**
         * Change the size of each <code>Cell</code>.
         * <br><b>Precondition</b>: 1000 >= <code>cSize</code> > 0
         * 
         * @param cSize     size of each cell
         */
        public void setCellSize(int cSize)
        {
            //Checking if cSize is greater than 0 and less than or equal to 1000
            if(cSize > 0 && cSize <= 1000)
            {
                cellSize = cSize;
                
                wCellNum = DEFAULT_GRID_SIZE / cellSize;
                hCellNum = DEFAULT_GRID_SIZE / cellSize;
                
                grid = new PlayGrid(wCellNum, hCellNum);
            }
        }
        
        /**
         * Updates the gui's call for painting. This method is called by <code>paint</code>.
         * <br>NOTE: Doing this prevents the clearing of the background <code>update</code> originally had (stopping any flickering).
         * 
         * @Override
         */
        public void update(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            drawGridLines(g2);
            
            //Length of the area of the square within a cell that is being filled
            int fillLength = cellSize - 1;
            
            //Resetting the previous tile count
            tileCount = 0;
            
            //Square filling algorithm
            for(int i = 0; i < grid.getHeight(); i++)
            {
                for(int j = 0; j < grid.getWidth(); j++)
                {
                    //Setting colour
                    if(grid.getState(i, j))
                    {
                        g2.fillRect((j * cellSize + 1), (i * cellSize + 1), fillLength, fillLength);
                        tileCount++; //Counting alive tiles...
                    }
                }
            }
        }
        
        /**
         * Paints the entire grid by calling <code>update</code>.
         * 
         * @Override 
         */
        public void paint(Graphics g)
        {
            update(g);
        }
        
        /**
         * Draws grid lines for the grid. <code>paint</code> calls this method.
         */
        public void drawGridLines(Graphics2D g2)
        {
            int posLine = 0;
            
            //Painting the background
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);
            
            //Drawing the column lines
            g2.setColor(Color.BLACK);
            for(int i = 0; i <= wCellNum; i++)
            {
                posLine = i * cellSize;
                g2.drawLine(posLine, 0, posLine, DEFAULT_GRID_SIZE);
                //g2.drawLine(0, posLine, DEFAULT_GRID_SIZE, posLine);
            }
            
            //Drawing the row lines
            for(int i = 0; i <= hCellNum; i++)
            {
                posLine = i * cellSize;
                g2.drawLine(0, posLine, DEFAULT_GRID_SIZE, posLine);
            }
        }
        
        /**
         * Changes the en/disabling of the mouse listener (should be false if the game is "running").
         */
        public void changeEnableListener()
        {
            enableListener = !(enableListener);
        }
        
        public int getCurrentRow()
        {
            return curRow;
        }
        
        public int getCurrentCol()
        {
            return curCol; 
        }
        
        /**
         * Returns the tile count for the grid.
         * 
         * @return <code>tileCount</code>
         */
        public int getTileCount()
        {
            return tileCount;
        }
        
        /**
         * <code>MListener</code> provides a mouse listener for <code>GridPanel</code>.
         */
        class MListener extends MouseAdapter
        {
            /**
             * Corresponds the mouse's position in the grid to the actual grid position for the grid.
             * <br>This method also changes that cell's state and calls <code>repaint</code>.
             * 
             * @Override
             */
            public void mousePressed(MouseEvent e)
            {
                if(enableListener) //Checks if listener should be enabled
                {
                    //Getting location corresponding on the grid
                    int row = e.getY() / cellSize;
                    int col = e.getX() / cellSize;
                    
                    //Changing state of the cell
                    grid.setCell(row, col, !(grid.getState(row, col)));
                    
                    repaint(); //This line provides faster painting capabilities when the user is clicking quickly
                }
            }
            
            public void mouseMoved(MouseEvent e)
            {
                curRow = e.getY() / cellSize;
                curCol = e.getX() / cellSize;
                setToolTipText("(" + curCol + ", " + curRow + ")");
            }
        }
  
    
    private PlayGrid grid;
    private int cellSize;
    
    private int wCellNum;
    private int hCellNum;
    
    private MListener ears;
    private boolean enableListener; //The mouse listener gets disabled if the program starts "running"
    
    private volatile int curRow;
    private volatile int curCol;
    
    private int tileCount; //Counting tiles in the grid, it counts as the grid is being drawn

    //Constants
    private final static int DEFAULT_GRID_SIZE = 1000;
    private final static int DEFAULT_CELL_SIZE = 10;
}