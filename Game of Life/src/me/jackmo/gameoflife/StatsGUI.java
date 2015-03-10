package me.jackmo.gameoflife;

import java.awt.*;
import javax.swing.*;
/**
 * Write a description of class Stats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatsGUI extends JPanel //implements ActionListener
{
        private JLabel generation;
        private JLabel tiles;
        private JLabel time;
        private JButton start;
        private JButton step;
        private JButton reset;
        private JButton clear;

    public StatsGUI(int g, int i, double t)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        generation = new JLabel("Generation " + g);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10,10,0,0);
        c.gridx = 0;
        c.gridy = 0;
        add(generation,c);

        tiles = new JLabel("Tiles " + i);
        c.gridx = 0;
        c.gridy = 1;
        add(tiles,c);

        time = new JLabel("Time " + t);
        c.gridx = 0;
        c.gridy = 2;
        add(time,c);

        start = new JButton("Start");
        start.setPreferredSize(new Dimension(90, 30));
        c.insets = new Insets(10,10,0,5);
        c.gridx = 0;
        c.gridy = 3;
        add(start,c);

        
        step = new JButton("Step");
        step.setPreferredSize(new Dimension(90, 30));
        c.insets = new Insets(10,5,0,10);
        c.gridx = 1;
        c.gridy = 3;
        add(step,c);

        reset = new JButton("Reset");
        reset.setPreferredSize(new Dimension(90, 30));
        c.insets = new Insets(10,10,0,5);
        c.gridx = 0;
        c.gridy = 4;
        add(reset,c);

        clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(90, 30));
        c.insets = new Insets(10,5,0,10);
        c.gridx = 1;
        c.gridy = 4;
        add(clear,c);
    }
}
