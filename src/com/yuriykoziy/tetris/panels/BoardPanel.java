package com.yuriykoziy.tetris.panels;

import com.yuriykoziy.tetris.logic.Board;
import com.yuriykoziy.tetris.piece.Tetromino;
import com.yuriykoziy.tetris.texture.BlockTexture;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class implements graphical representation of Tetris game logic, 
 * and also controls animation and handles all the necessary keyboard input.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class BoardPanel extends JPanel implements ActionListener {
    private static final int SQUARE_DIMENSION = 20;
    
    private final Timer timer;
    private final Board board;
    private final BlockTexture block;
    private final int width;
    private final int height;
    private final int currentDelay;
    private int[] speedLevels;
    
    private boolean isPaused = false;
    
    private JLabel gamePausedLabel;
    
    /**
     * Constructs JPanel to display Tetris board graphically.
     * @param width  board width
     * @param height board height
     * @param board  Tetris board object.
     */    
    public BoardPanel(int width, int height, Board board) {
        this.width = width;
        this.height = height;
        this.board = board;
        
        //setListener(board);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));  
        setFocusable(true);
        setOpaque(true); 

        block = new BlockTexture();
        calculateLevelSpeeds();

        currentDelay = speedLevels[board.getLevel()];
        timer = new Timer(currentDelay, this);
        timer.start(); 
        
        addKeyListener(new InputListener());
        createStatusLabels();        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {    
        board.fall();         
        if(!board.isGameRunning() || (board.getLevel() > 98)) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "GAME OVER", "Game Starus",  JOptionPane.INFORMATION_MESSAGE);
        } else {
            repaint();   
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawFallingTetromino(g);
        drawTetrominoesOnGround(g);
        g.dispose();
    }

    /**
     * Calculates timer delays for levels 0-9.
     */
    // best value = 5
    private void calculateLevelSpeeds() {
        speedLevels = new int[10];
        int curSpeed = 1000;
        for(int i = 0; i < 10; i++) {
            speedLevels[i] = curSpeed;
            int speedChange = curSpeed/9;
            curSpeed = curSpeed - speedChange;
         } 
    }
            
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        // draw vertical lines
        for(int i = 0; i <= 10; i++) {
            g.drawLine(i*SQUARE_DIMENSION, 0, i*SQUARE_DIMENSION, height-5);
        }
        // draw horizontal lines
        for(int i = 0; i <= 20; i++) {
            g.drawLine(0, i*SQUARE_DIMENSION, width-5, i*SQUARE_DIMENSION);
        }
    }
    
    private void drawTetrominoesOnGround(Graphics g) {
        for(int row = 0; row < board.field.length; row++) {
            for(int col = 0; col < board.field[row].length; col++) {
                int type = board.field[row][col]; 
                if(type != 0) {
                    g.drawImage(block.getBlockTexture(type), row*SQUARE_DIMENSION, col*SQUARE_DIMENSION, null);
                }
            }
        }   
    }
    
    private void drawFallingTetromino(Graphics g) {
        Tetromino tetro = board.getCurrentTetromino();
        int x = tetro.getXposition();
        int y = tetro.getYposition();
        int[][] tmp = tetro.getTetromino();
        
        for(int row = 0; row < Board.TETROMINO_SIZE; row++) {
            for(int col = 0; col < Board.TETROMINO_SIZE; col++) {
                int type = tmp[row][col]; 
                if(type != 0) {
                    g.drawImage(block.getBlockTexture(type), (x+col)*SQUARE_DIMENSION, (y+row)*SQUARE_DIMENSION, null);
                }
            }
        }           
    }

    private void pause() {
        isPaused = !isPaused;
        if (isPaused) {
            gamePausedLabel.setOpaque(true);
            timer.stop();
        } else {
            gamePausedLabel.setOpaque(false);
            timer.start();
        }
        repaint();
    }
    
    private void createStatusLabels() {
        
        Font fontSerif = new Font("Serif", Font.BOLD, 35);
        
        gamePausedLabel = new JLabel("PAUSED");
        gamePausedLabel.setFont(fontSerif);
        gamePausedLabel.setForeground(Color.RED);
        gamePausedLabel.setBackground(new Color(0, 0, 0, 0));
        gamePausedLabel.setSize(20, 20);
        gamePausedLabel.setLocation(0, 0);
        gamePausedLabel.setHorizontalAlignment(0);
        gamePausedLabel.setOpaque(false);
        add(gamePausedLabel);        
    }
    
    private class InputListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            if(ke.getKeyCode() == KeyEvent.VK_P) {
                pause();
                return;
            }
            if(isPaused) {
                return;
            }
            switch(ke.getKeyCode()) 
            {
                case KeyEvent.VK_DOWN: board.fall(); repaint(); break;
                case KeyEvent.VK_LEFT: board.moveLeft(); repaint(); break;
                case KeyEvent.VK_RIGHT: board.moveRight(); repaint(); break;
                case KeyEvent.VK_UP: board.rotate(); repaint(); break;
                case KeyEvent.VK_SPACE:
                    if(board.isGameRunning()) {
                        board.instantDrop();                        
                    }
                    repaint(); 
                break; 
                default: break;
            }
        }
    }      
}
