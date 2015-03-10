package me.jackmo.gameoflife;

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Creates a <code>JMenuBar</code> for the applet
 * 
 * @version 2.67
 */
public class Menu extends JMenuBar
{
    private Scanner in;
    private String output = new String();

    private final String MENU_TITLE = "Menu";
    private final String HELP_TITLE = "Help";
    private final String ABOUT_TITLE = "About";

    private final String ERROR_TEXT = "The file is missing.";
    private final String ERROR_TITLE = "Error";

    /**
     * Creates a <code>JMenuBar</code> object
     */
    public JMenuBar Menu()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU_TITLE);

        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem(HELP_TITLE);
        menuItem.addActionListener(new ActionListener()
            {  
                public void actionPerformed(ActionEvent e)
                {
                    //When Help is pressed, it opens the GOAL Help file in a JOptionPane MessageDialog
                    try
                    {
                        InputStream in = getClass().getResourceAsStream("GOAL Help.txt");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String next = reader.readLine();
                        while(next != null)
                        {                        
                            output += next + "\n\r";
                            next = reader.readLine();
                        }
                        reader.close();
                        JOptionPane.showMessageDialog(null,output,HELP_TITLE,JOptionPane.PLAIN_MESSAGE);
                        output =  new String();
                    }
                    catch(Exception one)
                    {
                        JOptionPane.showMessageDialog(null,
                            ERROR_TEXT,
                            ERROR_TITLE,
                            JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        );
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(ABOUT_TITLE);
        menuItem.addActionListener(new ActionListener()
            {  
                public void actionPerformed(ActionEvent e)
                {
                    //When About is pressed, it opens the GOAL About file in a JOptionPane MessageDialog
                    try
                    {
                        InputStream in = getClass().getResourceAsStream("GOAL About.txt");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String next = reader.readLine();
                        while(next != null)
                        {
                            output += next + "\n\r";
                            next = reader.readLine();
                        }
                        reader.close();
                        JOptionPane.showMessageDialog(null,output,ABOUT_TITLE,JOptionPane.INFORMATION_MESSAGE);
                        output = new String();
                    }
                    catch(Exception one)
                    {
                        JOptionPane.showMessageDialog(null,
                            ERROR_TEXT,
                            ERROR_TITLE,
                            JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        );
        menu.add(menuItem);
        return menuBar;
    }
}
