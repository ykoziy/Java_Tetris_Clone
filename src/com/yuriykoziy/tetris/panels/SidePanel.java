package com.yuriykoziy.tetris.panels;

import com.yuriykoziy.tetris.logic.Board;
import com.yuriykoziy.tetris.piece.Tetromino;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * This class implements JPanel to display game information such as:
 * - Score
 * - Number of lines cleared so far
 * - Current game level
 * - Display next tetromino shape
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class SidePanel extends JPanel {
    private JLabel score;
    private JLabel level;
    private JLabel lines;
    private final PreviewPanel next;
    
    private static BufferedImage img;

    /**
     * Constructs a new JPanel.
     * 
     * @param width JPanel width
     * @param height JPanel height
     */
    public SidePanel(int width, int height) {
        loadImage();
        
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);
        setOpaque(true);
        
        createLabels();
        add(score);        
        add(level);
        add(lines);
        
        next = new PreviewPanel(70, 70);
        next.setLocation(45, 270);
        
        add(next);
    }
    
    /**
     * Sets property change listener. 
     * @param board tetris board object.
     */
    public void setListener(Board board) {
       board.addPropertyChangeListener(new BoardPropertyListener());
    } 
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, 150, 405, this);           
    }
        
    private void loadImage() {
        try {    
            img = ImageIO.read(getClass().getResource("/com/yuriykoziy/tetris/resources/TetrisSidePanel.png"));
        } catch (IOException ex) {
            Logger.getLogger(SidePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createLabels() {
        Font fontSerif = new Font("Serif", Font.BOLD, 17);
        
        score = new JLabel(".");
        score.setFont(fontSerif);
        score.setForeground(Color.BLACK);
        score.setLocation(34, 52);
        score.setSize(82, 23);
        score.setHorizontalAlignment(0);
        
        level = new JLabel(".");
        level.setFont(fontSerif);
        level.setForeground(Color.BLACK);        
        level.setLocation(34, 122);
        level.setSize(82, 23);
        level.setHorizontalAlignment(0);
        
        lines = new JLabel(".");
        lines.setFont(fontSerif);
        lines.setForeground(Color.BLACK);        
        lines.setLocation(34, 195);
        lines.setSize(82, 23);
        lines.setHorizontalAlignment(0);           
    }

    private class BoardPropertyListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if("score".equals(pce.getPropertyName())) {
                score.setText(pce.getNewValue().toString());
            }
            if("level".equals(pce.getPropertyName())) {
                int boardLevel = (int)pce.getNewValue()+1;
                level.setText(Integer.toString(boardLevel));
            }
            if("lines".equals(pce.getPropertyName())) {
                lines.setText(pce.getNewValue().toString());
            }
            if("type".equals(pce.getPropertyName())) {
                next.setTetrominoType((Tetromino)pce.getNewValue());
                next.repaint();
            }              
        }      
    }
}

