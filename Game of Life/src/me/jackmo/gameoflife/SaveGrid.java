package me.jackmo.gameoflife;

/**
 * Represents a saved grid stored within the game.
 * 
 * @version 2.31
 */
public class SaveGrid extends Grid
{
        /**
         * A default <code>SaveGrid</code> with a <code>null</code> 2-D <code>Cell</code> array, no name.
         */
        public SaveGrid()
        {
            super();
            name = new String();
        }
        
        /**
         * A <code>SaveGrid</code> taking in a 2-D <code>Cell</code> array, no name.
         */
        public SaveGrid(Cell[][] cIn)
        {
            this(cIn, new String());
        }

        
        /**
         * A <code>SaveGrid</code> taking in a 2-D <code>Cell</code> array, with a name.
         */
        public SaveGrid(Cell[][] cIn, String nIn)
        {
            super(cIn);
            name = nIn;
        }
        
        /**
         * Returns the name of this grid.
         */
        public String getName()
        {
            return name;
        }
        
        /**
         * A string representation of this <code>SaveGrid</code>.
         * 
         * @Override
         */
        public String toString()
        {
            String info = new String();
            
            info += "<html>Tiles: " + getTileCount() + "<br>";
            info += "Name: " + name + "<br>";
            info += "Size: " + getWidth() + " x " + getHeight() + "<br></html>";
            
            return info;
        }
        
        /**
         * This <code>compareTo</code> method compares the tile count (first) and the names before the grid.
         * <br><b>Precondition</b>: g (or its 2-D <code>Cell</code>) array cannot be <code>null</code>.
         * <br><br>-    First, the method checks the difference in tile count.
         * <br>-    Then, the method calls the <code>String compareTo</code> for check.
         * <br>-    Lastly, the method calls the <code>Grid compareTo</code> for check.
         * 
         * 
         * @param g the saved grid being compared with
         */
        public int compareTo(SaveGrid g)
        {
            int compareValue = 0;
            
            //First check
            compareValue = getTileCount() - g.getTileCount();
            
            if(compareValue == 0)
            {
                //Second check
                compareValue = name.compareTo(g.getName());
                
                //Final check
                if(compareValue == 0)
                    compareValue = super.compareTo(g);
            }
            
            return compareValue;
        }
    
    private String name;
}