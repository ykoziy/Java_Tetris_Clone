import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class implements graphical representation of Tetris game logic, 
 * and also controls animation and handles all the necessary keyboard input.
 * 
 * @author Yuriy Koziy
 * @version 1.0
 */
public class Level extends JPanel implements ActionListener {
    private static final int SQUARE_DIMENSION = 20;
    
    private final Timer timer;
    private final Board board;
    private Image[] blocks;
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
    public Level(int width, int height, Board board) {
        this.width = width;
        this.height = height;
        this.board = board;
        
        setListener(board);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));  
        setFocusable(true);
        setOpaque(true); 
        
        loadBlockIcons();
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
     * Sets property change listener. 
     * @param board tetris board object.
     */    
    private void setListener(Board board) {
        board.addPropertyChangeListener(new BoardPropertyListener());
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
                    g.drawImage(blocks[type-1], row*SQUARE_DIMENSION, col*SQUARE_DIMENSION, null);
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
                    g.drawImage(blocks[type-1], (x+col)*SQUARE_DIMENSION, (y+row)*SQUARE_DIMENSION, null);
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
    
    private void loadBlockIcons() {
        blocks = new Image[7];
        blocks[0] = new ImageIcon(getClass().getResource("/resources/cyan_block.png")).getImage();
        blocks[1] = new ImageIcon(getClass().getResource("/resources/red_block.png")).getImage();
        blocks[2] = new ImageIcon(getClass().getResource("/resources/green_block.png")).getImage();
        blocks[3] = new ImageIcon(getClass().getResource("/resources/magenta_block.png")).getImage();
        blocks[4] = new ImageIcon(getClass().getResource("/resources/yellow_block.png")).getImage();
        blocks[5] = new ImageIcon(getClass().getResource("/resources/blue_block.png")).getImage();
        blocks[6] = new ImageIcon(getClass().getResource("/resources/orange_block.png")).getImage();        
    }
    
    private void createStatusLabels() {
        
        Font fontSerif = new Font("Serif", Font.BOLD, 35);
        
        gamePausedLabel = new JLabel("PAUSED");
        gamePausedLabel.setFont(fontSerif);
        gamePausedLabel.setForeground(Color.BLACK);
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
                case KeyEvent.VK_SPACE: board.instantDrop(); repaint(); break; 
                default: break;
            }
        }
    }
    
    private class BoardPropertyListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if("level".equals(pce.getPropertyName())) {
                int level = (int)pce.getNewValue();
                if(level < 10) {
                    int timerDelay = speedLevels[(int)pce.getNewValue()];
                    if(timerDelay != currentDelay) {
                        timer.setDelay(speedLevels[(int)pce.getNewValue()]);   
                    }
                }
            }            
        }      
    }    
}
