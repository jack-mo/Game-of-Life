package me.jackmo.gameoflife;

import javax.swing.*;
import java.awt.*;
/**
 * The applet for the class.
 * 
 * @version 204
 */
public class Goal extends JApplet implements Runnable
{
        /**
         * Intializes the GUI and game.
         */
        private void createGame()
        {
            game = new Game(DEFAULT_SQUARE_CELL_LENGTH, DEFAULT_SQUARE_CELL_LENGTH);
            add(game.addGUI());
            
            Menu menuBar = new Menu();
            setJMenuBar(menuBar.Menu());
        }
        
        /**
         * @Override
         */
        public void init()
        {
            try
            {
                javax.swing.SwingUtilities.invokeAndWait(new Runnable() 
                    {
                        public void run()
                        {
                            createGame();
                        }
                    }
                );
            }
            catch(Exception e)
            {
                System.err.println("Something messed up!");
            }
        }
        
        /**
         * @Override
         */
        public void start()
        {
            if(runner == null)
            {
                runner = new Thread(this);
                runner.start();
            }
        }
        
        public void run()
        {
            Thread thisThread = Thread.currentThread();
            while(runner == thisThread)
            {
                game.runProgram();
                
                try
                {
                    Thread.sleep(PROGRAM_SLEEP_TIME);
                }
                catch(InterruptedException e)
                {
                    System.err.println("This didn't work...");
                }
            }
        }
        
        /**
         * @Override
         */
        public void stop()
        {
            //Changes thread to stop running
            if(runner != null)
                runner = null;
        }
        
        /**
         * @Override
         */
        public void destory()
        {
            //Destroys the thread
            try
            {
                runner.join();
            }
            catch(InterruptedException e)
            {
                System.err.println("This didn't work...");
            }
        }
        
    //private GoalGUI gui;
    private Game game;
    private final int DEFAULT_SQUARE_CELL_LENGTH = 100;
    private volatile Thread runner;
    
    //Constants
    private final int PROGRAM_SLEEP_TIME = 150;
}