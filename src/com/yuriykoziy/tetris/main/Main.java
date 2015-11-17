package com.yuriykoziy.tetris.main;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.SwingUtilities;

/**
 * Launches actual game window.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class Main {
    
    /**
     * Creates JFrame and displays it.
     */
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        // create and set up window
        JFrame frame = new JFrame("Tetris Clone");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        //create and set up content pane
        Game game = new Game();
        frame.setContentPane(game.getGamePanel());
        
        //display window
        frame.pack();
        frame.setVisible(true);        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });    
    }    
}
