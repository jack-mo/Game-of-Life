package me.jackmo.gameoflife;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * A content pane acting as the GUI class for G.O.A.L.
 * 
 * @version 2.67
 */
public class GoalGUI extends JPanel
{   
        /**
         * Creates a <code>GoalGUI</code> object.
         */
        public GoalGUI(PlayGrid gIn)
        {
            //Initializing the 3 panels with scroll panes
            mainGrid = new GridPanel(gIn);
            JScrollPane mScrollPane = new JScrollPane(mainGrid);
            mScrollPane.setPreferredSize(new Dimension(
            DEFAULT_SCROLLPANE_GRID_PANEL_W,
            DEFAULT_SCROLLPANE_GRID_PANEL_H));
            
            dataTablePanel();
            JScrollPane dScrollPane = new JScrollPane(dataTable);
            dScrollPane.setPreferredSize(new Dimension(
            DEFAULT_SCROLLPANE_DATA_TABLE_W,
            DEFAULT_SCROLLPANE_DATA_TABLE_H));
            
            infoPanel();
            
            //Intializing the layout constraints and positioning the 3 panels
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridheight = LINE_TWO;
            
            c.anchor = GridBagConstraints.LINE_START;
            c.gridx = LINE_ZERO;
            c.gridy = LINE_ZERO;
            add(mScrollPane, c);
            
            c.anchor = GridBagConstraints.LAST_LINE_END;
            c.gridx = LINE_ONE;
            c.gridy = LINE_ONE;
            add(dScrollPane, c);
            
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.gridx = LINE_ONE;
            add(info, c);
        }
        
        /**
         * Initializes the data table panel for <code>GoalGUI</code>.
         */
        private void dataTablePanel()
        {
            //Creating list
            dataTable = new JList();
        }
        
        /**
         * Initializes the info panel for <code>GoalGUI</code>.
         */
        private void infoPanel()
        {
            //Creates panel along with layout for buttons and labels
            info = new JPanel(new GridBagLayout());
            
            info.setBackground(Color.WHITE);
            
            info.setPreferredSize(new Dimension(DEFAULT_INFO_W, DEFAULT_INFO_H));
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_START;
            
            //Intializing and adding the labels
            c.insets = new Insets(INSETS_1, INSETS_1, 0, 0);
            
            generation = new JLabel(GENERATION_TEXT + FIRST_GENERATION);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_ZERO;
            info.add(generation, c);
            
            xPos = new JLabel(X_TEXT + "0");
            c.gridx = LINE_ONE;
            c.gridy = LINE_ZERO;
            info.add(xPos, c);
            
            tiles = new JLabel(TILES_TEXT + 0);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_ONE;
            info.add(tiles, c);
            
            yPos = new JLabel(Y_TEXT + "0");
            c.gridx = LINE_ONE;
            c.gridy = LINE_ONE;
            info.add(yPos, c);
            
            //Intializing and adding the buttons
            //Start button
            start = new JButton(START_TEXT);
            start.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            start.addActionListener(new ActionListener() //Action listners! Joy!!!...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        startState = !(startState);
                        if(startState)
                            start.setText(PAUSE_TEXT);
                        else
                            start.setText(START_TEXT);
                            
                        mainGrid.changeEnableListener();
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_1, 0, INSETS_2);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_TWO;
            info.add(start, c);
            
            //Step button
            JButton step = new JButton(STEP_TEXT);
            step.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            step.addActionListener(new ActionListener() //Yes, one for EACH button...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        stepState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_2, 0, INSETS_1);
            c.gridx = LINE_ONE;
            c.gridy = LINE_TWO;
            info.add(step, c);
            
            //Reset button
            JButton reset = new JButton(RESET_TEXT);
            reset.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            reset.addActionListener(new ActionListener() //Perhaps a bit lengthy...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        resetState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_1, 0, INSETS_2);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_THREE;
            info.add(reset, c);
            
            //Clear button
            JButton clear = new JButton(CLEAR_TEXT);
            clear.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            clear.addActionListener(new ActionListener() //YAY!!! LAST ONE!!!
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        clearState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_2, 0, INSETS_1);
            c.gridx = LINE_ONE;
            c.gridy = LINE_THREE;
            info.add(clear, c);
            
            //Save button
            JButton save = new JButton(SAVE_TEXT);
            save.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            save.addActionListener(new ActionListener() //NOPE!!! I LIED!!!
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        saveState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_1, 0, INSETS_2);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_FOUR;
            info.add(save, c);      
            
            //Copy button
            JButton copy = new JButton(COPY_TEXT);
            copy.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            copy.addActionListener(new ActionListener() //KKs... Last one, for real, seriously...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        copyState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_2, 0, INSETS_1);
            c.gridx = LINE_ONE;
            c.gridy = LINE_FOUR;
            info.add(copy, c);
            
            //Import button
            JButton importFile = new JButton(IMPORT_TEXT);
            importFile.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            importFile.addActionListener(new ActionListener() //Dammit!! And I thought I wasn't going to lie again... I apologize...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        importState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_1, 0, INSETS_2);
            c.gridx = LINE_ZERO;
            c.gridy = LINE_FIVE;
            info.add(importFile, c);    
            
            //Export button
            JButton exportFile = new JButton(EXPORT_TEXT);
            exportFile.setPreferredSize(new Dimension(DEFAULT_BUTTON_W, DEFAULT_BUTTON_H));
            exportFile.addActionListener(new ActionListener() //I'm not even going to say last one anymore... again, I apologize...
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        exportState = true;
                    }
                }
            );
            c.insets = new Insets(INSETS_1, INSETS_2, 0, INSETS_1);
            c.gridx = LINE_ONE;
            c.gridy = LINE_FIVE;
            info.add(exportFile, c);    
        }  
        
        /**
         * Displays a <code>JOptionPane</code> message dialog containing two text fields.
         * 
         * @param option the option number which the message dialog is setup
         * @return A <code>int[]</code> object representing the co-ordinates of the grid. 
         * <br> (NOTE: <code>{-1, -1}</code> is returned if user wants to cancel.)
         */
        public int[] displayGetCoordinateDialog(int option)
        {
            //Determining message being displayed based on option
            String cMessage = new String();
            switch(option)
            {
                case 1:     cMessage = "first co-ordinate (x first then y).";
                            break;
                case 2:     cMessage = "second co-ordinate (x first then y).";
                            break;
                case 3:     cMessage = "co-ordinate (x first then y)." + 
                                        "\n(MUST be such that the saved grid WILL fit inside the main grid.).";
                            break;
            }
            
            //Creating the text fields
            JFormattedTextField x = new JFormattedTextField(createFormatter(MAX_DIGITS_IN_POINT));
            JFormattedTextField y = new JFormattedTextField(createFormatter(MAX_DIGITS_IN_POINT));
            
            //Placing them together
            Object[] components = {DISPLAY_GET_POINT + cMessage, x, y};
            
            //Variables for loop
            int[] coordinate = null;
            int xPos = DEFAULT_COORDINATE_NUM;
            int yPos = DEFAULT_COORDINATE_NUM;
            String xText = new String();
            String yText = new String();
            
            int optionNum = 0;
            
            //A loop where a coordinate must be set before exiting
            while(coordinate == null)
            {
                JOptionPane.showMessageDialog(
                null, components, DISPLAY_GET_NAME_TITLE, JOptionPane.PLAIN_MESSAGE);
                
                //Getting user input
                xText = x.getText().trim();
                yText = y.getText().trim();
                
                //If user input was empty
                if(xText.isEmpty() || yText.isEmpty())
                {
                    optionNum = JOptionPane.showConfirmDialog(
                    null, INVALID_COORDINATE + TRY_AGAIN_MESSAGE, NOTICE_TITLE, JOptionPane.YES_NO_OPTION);
                    
                    if(optionNum != JOptionPane.YES_OPTION) //Cancel option
                    {
                        coordinate = new int[X_Y_COORDINATE];
                        coordinate[0] = xPos;
                        coordinate[1] = yPos;
                    }
                }
                else //Otherwise... set coordinate
                {
                    xPos = Integer.parseInt(xText);
                    yPos = Integer.parseInt(yText);
                    
                    coordinate = new int[X_Y_COORDINATE];
                    coordinate[0] = xPos;
                    coordinate[1] = yPos;
                }
            }
            
            return coordinate;
        }
        
        /**
         * Used only when <code>OPTION_3</code> is used in the previous <code>displayGetCoordinateDialog</code>.
         * <br>In other words, this method is used for the copy function.
         * <br>In the case that the user did choose an invalid co-ordinate, an notice message will appear.
         * <br>In the same message, a try again message will appear asking if user wants to try again.
         * 
         * @return A <code>boolean</code> with <code>true</code> if user wants to try again.
         */
        public boolean displayDoesNotAddUp()
        {
            boolean tryAgain = false;
            
            int optionNum = JOptionPane.showConfirmDialog(
            null, DOES_NOT_ADD_UP + TRY_AGAIN_MESSAGE, NOTICE_TITLE, JOptionPane.YES_NO_OPTION);
            
            if(optionNum == JOptionPane.YES_OPTION)
                tryAgain = true;
                
            return tryAgain;
        }
        
        /**
         * Displays a <code>JOptionPane</code> input dialog requesting a <code>String</code> from the user.
         * 
         * @return A <code>String</code> representing the name of the saved grid.
         */
        public String displayGetNameDialog()
        {
            String gridName = new String();
            boolean finished = false; //Condition to see if user wants a no name
            int optionNum = 0; //User's option for choosing if they want the name empty
            
            //Creating text field for user input 
            JTextField nameInput = new JTextField();
            
            //Placing them together
            Object[] components = {DISPLAY_GET_NAME, nameInput};
            
            //A loop where user has to agree to a no name or enters a name 
            while(!finished)
            {
                //Showing user the dialog box
                JOptionPane.showMessageDialog(
                null, components, DISPLAY_GET_NAME_TITLE, JOptionPane.PLAIN_MESSAGE);
                
                //Getting inputted name
                gridName = nameInput.getText();
                
                //Checking if name is empty
                if(!gridName.isEmpty())
                    finished = true;
                else
                {
                    optionNum = JOptionPane.showConfirmDialog(
                    null, NO_NAME, NOTICE_TITLE, JOptionPane.YES_NO_OPTION);
                    
                    if(optionNum != JOptionPane.NO_OPTION)
                        finished = true;
                }
            }
            
            return gridName;
        }
        
        /**
         * Sets the <code>MaskFormatter</code> for the formatted text fields.
         * <br>Used in <code>dispayGetCoordinateDialog</code>
         * 
         * @param s format style
         * @Override
         */
        protected MaskFormatter createFormatter(String s) 
        {
            MaskFormatter formatter = null;
            try 
            {
                formatter = new MaskFormatter(s);
            } 
            catch (java.text.ParseException exc) 
            {
                System.err.println("Something went wrong: " + exc.getMessage());
            }
            return formatter;
        }
        
        /**
         * Sets the step, reset, clear, save, copy, import, and export states back to <code>false</code> (Disabling their functions).
         */
        public void disableFunctions()
        {
            stepState = false;
            resetState = false;
            clearState = false;
            saveState = false;
            copyState = false;
            importState = false;
            exportState = false;
        }
        
        /**
         * Updates the gui main grid and info panel based on the grid and generation given.
         * 
         * @param newGrid   the grid being used for updating the gui
         * @param genIn     the generation count in the game
         */
        public void update(PlayGrid newGrid, int genIn)
        {
            //Repainting the main grid
            mainGrid.setNewGrid(newGrid);
            mainGrid.repaint();
            
            //Updating the tile and generation labels
            generation.setText(GENERATION_TEXT + genIn);
            tiles.setText(TILES_TEXT + mainGrid.getTileCount());
            
            xPos.setText(X_TEXT + mainGrid.getCurrentCol());
            yPos.setText(Y_TEXT + mainGrid.getCurrentRow());
        }
        
        /**
         * Updates the data table after each save.
         * 
         * @param info      the information being placed into the list
         */
        public void updateDataTable(String[] info)
        {
            dataTable.setListData(info);
        }
        
        /**
         * Returns the index of the selected element in the data table.
         */
        public int getDataTableIndex()
        {
            return dataTable.getSelectedIndex();
        }
        
        /**
         * Returns if the data table has been selected or not.
         */
        public boolean isDataTableNotSelected()
        {
            return dataTable.isSelectionEmpty();
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the start function.  
         * 
         * @return <code>startState</code>
         */
        public boolean getStartState()
        {
            return startState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the step function.  
         * 
         * @return <code>stepState</code>
         */
        public boolean getStepState()
        {
            return stepState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the reset function.  
         * 
         * @return <code>resetState</code>
         */
        public boolean getResetState()
        {
            return resetState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the clear function.  
         * 
         * @return <code>clearState</code>
         */
        public boolean getClearState()
        {
            return clearState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the save function.  
         * 
         * @return <code>saveState</code>
         */
        public boolean getSaveState()
        {
            return saveState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the copy function.  
         * 
         * @return <code>copyState</code>
         */
        public boolean getCopyState()
        {
            return copyState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the import function.  
         * 
         * @return <code>importState</code>
         */
        public boolean getImportState()
        {
            return importState;
        }
        
        /**
         * Gets the <code>boolean</code> state of prompting the export function.  
         * 
         * @return <code>exportState</code>
         */
        public boolean getExportState()
        {
            return exportState;
        }
        
        /**
         * 
         * @return the file to be imported
         */
        public File chooseFile()
        {
            JFileChooser fc = new JFileChooser();
            File out = null;
            try
            {
                if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    out = fc.getSelectedFile();
                }
            }
            catch(Exception e)
            {
                return null;
            }
            return out;
        }
        
    //The 2 main panels
    private GridPanel mainGrid;
    private JPanel info;
    
    //The main list with list model
    private JList dataTable;
    //private 
    
    //Buttons, labels, lists, and numerical/boolean stats for the info panel    
    private JLabel generation, tiles, xPos, yPos;
    
    private JButton start;
    private boolean startState, stepState, resetState, clearState, saveState, copyState, importState, exportState;
    
    
    //Sizing constants
    private final int DEFAULT_GUI_W = 854;
    private final int DEFAULT_GUI_H = 480;
    
    private final int DEFAULT_SCROLLPANE_GRID_PANEL_W = 600;
    private final int DEFAULT_SCROLLPANE_GRID_PANEL_H = 457;
    
    private final int DEFAULT_DATA_TABLE_W = 230;
    private final int DEFAULT_DATA_TABLE_H = 400;
    
    private final int DEFAULT_SCROLLPANE_DATA_TABLE_W = 250;
    private final int DEFAULT_SCROLLPANE_DATA_TABLE_H = 257;
    
    private final int DEFAULT_INFO_W = 250;
    private final int DEFAULT_INFO_H = 200;
    
    private final int DEFAULT_BUTTON_W = 90;
    private final int DEFAULT_BUTTON_H = 20;
    
    //Positioning constants
    private final int INSETS_1 = 10;
    private final int INSETS_2 = 5;
    
    private final int LINE_ZERO = 0;
    private final int LINE_ONE = 1;
    private final int LINE_TWO = 2;
    private final int LINE_THREE = 3;
    private final int LINE_FOUR = 4;
    private final int LINE_FIVE = 5;
    
    //Text constants
    private final String GENERATION_TEXT = "Generation: ";
    private final String TILES_TEXT = "Tiles: ";
    private final String X_TEXT = "X: ";
    private final String Y_TEXT = "Y: ";
    
    private final String START_TEXT = "Start";
    private final String PAUSE_TEXT = "Pause";
    private final String STEP_TEXT = "Step";
    private final String RESET_TEXT = "Reset";
    private final String CLEAR_TEXT = "Clear";
    private final String SAVE_TEXT = "Save";
    private final String COPY_TEXT = "Copy";
    private final String IMPORT_TEXT = "Import";
    private final String EXPORT_TEXT = "Export";
        
    private final String NOTICE_TITLE = "Notice!";
    private final String TRY_AGAIN_MESSAGE = "Would you like to try again?";
    
    private final String DISPLAY_GET_POINT_TITLE = "Enter co-ordinate.";
    private final String DISPLAY_GET_POINT = "Using TWO digits, Enter the ";
    private final String INVALID_COORDINATE = "Invalid coordinate was entered.\n";
    private final String MAX_DIGITS_IN_POINT = "##"; /* This is used for the for formatter text fields;
                                                      * since the default is 100 by 100, the co-ordinates must be at most 2 digits each value
                                                      */
    private final String DOES_NOT_ADD_UP = "The copying grid will exceed the main grid with this coordinate...\n";
    
    private final String DISPLAY_GET_NAME_TITLE = "Enter name.";
    private final String DISPLAY_GET_NAME = "Enter the name of the saved grid.";
    private final String NO_NAME = "No name was entered... Leave it empty?";
    
    //Other constants
    private final int X_Y_COORDINATE = 2;
    
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    
    public static final int DEFAULT_COORDINATE_NUM = -1;
    public static final int FIRST_GENERATION = 1;
}