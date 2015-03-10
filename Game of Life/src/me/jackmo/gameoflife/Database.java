package me.jackmo.gameoflife;

import java.util.ArrayList;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * This database class stores an ArrayList of SaveGrid objects, each one a sperate section of
 * the grid that is saved.
 * Save files can be made with encryption and decrypted, and brought into the database.
 * 
 * @version 2.67
 */
public class Database
{
    /**
     * Default, and only, Database constructor.
     */
    public Database()
    {
        base = new ArrayList<SaveGrid>();
    }
    
    /**
     * Adds a SaveGrid to the database.
     * @param aIn     input SaveGrid
     */
    public void add(SaveGrid aIn)
    {
        base.add(aIn);
    }
    
    /**
     * Removes the indicated SaveGrid reference from the databse.
     * @param aIn     indicated SaveGrid reference
     */
    public void remove(int aIn)
    {
        base.remove(aIn);
    }

    /**
     * Returns the indicated SaveGrid by reference.
     * @param aIn     indicated SaveGrid reference
     * @return        indicated SaveGrid
     */
    public SaveGrid get(int aIn)
    {
        return base.get(aIn);
    }

    /**
     * Reterns the size of the database.
     * @return       size of databse
     */
    public int getSize()
    {
        return base.size();
    }

    /**
     * Sorts this array of <code>SaveGrid</code> (ascending order) based on "natural ordering".
     */
    public void sort()
    {
        //Uses a selection sort

    }

    /**
     * Imports an indicated save file with a given key. Adds the contained SaveGrid to the database.
     * @param aIn     File to import
     * @param bIn     Key
     */
    public void importFile(File aIn, String bIn)
    {
        try
        {
            Encryption en = new Encryption(bIn);
            BufferedReader br = new BufferedReader(new FileReader(aIn));
            String name = en.decrypt(br.readLine());//decrypts the name
            int dim1 = Integer.parseInt(en.decrypt(br.readLine()));//decrypts the dimensions.
            int dim2 = Integer.parseInt(en.decrypt(br.readLine()));
            Cell[][] temp = new Cell[dim2][dim1];//creates the new cell array
            for(int i = 0; i < dim2; i++)
            {
                for(int j = 0; j < dim1; j++)
                {
                    temp[i][j] = new Cell();
                }
            }//populates array
            String next = br.readLine();
            while(next != null)//while the file has information
            {
                String coordinates = en.decrypt(next);
                int in1 = Integer.parseInt(coordinates.substring(0,2));
                int in2 = Integer.parseInt(coordinates.substring(2,4));
                temp[in1][in2].live();
                next = br.readLine();
            }//decrypts unit, then sets the indicated cell as live.
            add(new SaveGrid(temp, name));
            br.close();
        }
        catch(Exception e)
        {
            System.out.println("Error.");
            System.exit(0);
        }
    }

    /**
     * Exports a SaveGrid to a save file, named after the name given to the SaveGrid. Encrypts using
     * the given key
     * @param aIn     The SaveGrid to export
     * @param bIn     Key
     */
    public void exportFile(SaveGrid aIn, String bIn)
    {
        try
        {
            FileWriter fw = new FileWriter(new File(aIn.getName() + ".goal"));
            Encryption en = new Encryption(bIn);
            fw.write(en.encrypt(aIn.getName())+ "\r");//Encrypts name.
            fw.write(en.encrypt(Integer.toString(aIn.getWidth())) + "\r");//Encrypts dimensions.
            fw.write(en.encrypt(Integer.toString(aIn.getHeight())) + "\r");
            for(int i = 0; i < aIn.getHeight(); i++)
            {
                for(int j = 0; j < aIn.getWidth(); j++)
                {
                    if(aIn.getState(i,j))
                    {
                        String row = Integer.toString(i);
                        String col = Integer.toString(j);
                        if(i < 10)
                        {
                            row = "0" + Integer.toString(i);
                        }
                        if(j < 10)
                        {
                            col = "0" + Integer.toString(j);
                        }
                        fw.write(en.encrypt(row + col) + "\r");
                    }
                }
            }//Encrypts live cells' coordinates and writes to file.
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Error.");
            System.exit(0);
        }
    }
    
    /**
     * Exports a SaveGrid to a save file, named after the name given to the SaveGrid. Encrypts using
     * the given key
     * @param aIn     The SaveGrid to export
     * @param bIn     Key
     */
    public void exportFile(int cIn, String bIn)
    {
        try
        {
            SaveGrid aIn = get(cIn);
            File f = new File(aIn.getName() + ".goal");
            if(f.exists())
            {
                JOptionPane.showMessageDialog(null,"exists!");
            }
            BufferedWriter bw = new BufferedWriter (new FileWriter(f, true));
            Encryption en = new Encryption(bIn);
            bw.write(en.encrypt(aIn.getName())+ "\r");//Encrypts name.
            bw.write(en.encrypt(Integer.toString(aIn.getWidth())) + "\r");//Encrypts dimensions.
            bw.write(en.encrypt(Integer.toString(aIn.getHeight())) + "\r");
            for(int i = 0; i < aIn.getHeight(); i++)
            {
                for(int j = 0; j < aIn.getWidth(); j++)
                {
                    if(aIn.getState(i,j))
                    {
                        String row = Integer.toString(i);
                        String col = Integer.toString(j);
                        if(i < 10)
                        {
                            row = "0" + Integer.toString(i);
                        }
                        if(j < 10)
                        {
                            col = "0" + Integer.toString(j);
                        }
                        bw.write(en.encrypt(row + col) + "\r");
                    }
                }
            }//Encrypts live cells' coordinates and writes to file.
            bw.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "HI3");
            System.out.println("Error.");
            System.exit(0);
        }
    }
    
    public String[] getDatabaseInfo()
    {
        String[] info = new String[base.size()];
        
        for(int i = 0; i < info.length; i++)
            info[i] = base.get(i).toString();
        
        return info;
    }

    ArrayList<SaveGrid> base;
}