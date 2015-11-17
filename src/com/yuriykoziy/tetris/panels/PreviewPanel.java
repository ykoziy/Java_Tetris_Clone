package com.yuriykoziy.tetris.panels;

import com.yuriykoziy.tetris.piece.Tetromino;
import com.yuriykoziy.tetris.texture.BlockTexture;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * This class implements tetromino preview JPanel, which display next tetromino shape.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class PreviewPanel extends JPanel {   
    private static final int SQUARE_DIMENSION = 15;
    private final BlockTexture block;
    private static int[][] currentTetrominoArray = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
    
    /**
     * Constructs new tetromino preview JPanel.
     * 
     * @param width JPanel width
     * @param height JPanel height
     */
    public PreviewPanel(int width, int height) {
        setLayout(null);
        setSize(new Dimension(width, height));
        setOpaque(false);
        block = new BlockTexture();
    }
      
    /**
     * 
     * @param tetro tetromino object.
     */
    public void setTetrominoType(Tetromino tetro) {
        currentTetrominoArray = tetro.getTetromino();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTetromino(g);
    } 
    private void drawTetromino(Graphics g) {
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                int type = currentTetrominoArray[row][col]; 
                if(type != 0) {
                    g.drawImage(block.getBlockTexture(type), (col)*SQUARE_DIMENSION, (row)*SQUARE_DIMENSION, SQUARE_DIMENSION, SQUARE_DIMENSION, null);
                }
            }
        }  
    }
}
