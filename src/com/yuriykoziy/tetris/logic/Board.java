package com.yuriykoziy.tetris.logic;

import com.yuriykoziy.tetris.piece.Tetromino;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * This class implements Tetris game logic.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */

public class Board {
    public static final int TETROMINO_SIZE = 4; 
    
    public int field[][];
    
    private static final String SCORE = "score";
    private static final String LEVEL = "level";
    private static final String LINES = "lines";
    private static final String TYPE = "type"; 
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private int score;
    private int linesCleared;
    private int level; 
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private boolean isGameRunning;
    
    private Queue<Tetromino> tetrominoesList;
    
    private final SwingPropertyChangeSupport pcs;
   
    /**
     * Constructs Tetris game board of given width and height, also initial level is specified.
     * 
     * @param width  game board width.
     * @param height game board width.
     * @param level  game level (0-8).
     */
    public Board(int width, int height, int level) {
        isGameRunning = true;
        BOARD_WIDTH = width;
        BOARD_HEIGHT = height;
        field = new int[BOARD_WIDTH][BOARD_HEIGHT];
        score = 0;
        linesCleared = 0;
        pcs = new SwingPropertyChangeSupport(this);
        this.level = level;
        tetrominoesList = generateAndShuffleTetrominoesList();
    }
    
    /**
     * Make sure that initial game state gets updated in side panel.
     */
    public void startGame()
    {
        addTetromino();
        updateSidePanelLabels();
    }
    
    /**
     * @return game status.
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }    
        
    /**
     * 
     * @return current game level.
     */
    public int getLevel()
    {
        return level;
    }
    
    /**
     * 
     * @return current Tetromino object.
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    } 

    /**
     * Move tetromino while calculating "soft drop" score, 
     * side panel gets updated when tetromino lands.
     */   
    public void fall() {
        if(isTetrominoColliding(0,1,currentTetromino)) {
            landTetromino(currentTetromino);
            updateSidePanelLabels();
        } else {
            score++;
            currentTetromino.setYposition(currentTetromino.getYposition() + 1);       
        }
    }
    
    /**
     * Move tetromino shape to the right while checking for collisions.
     */        
    public void moveRight() {
        if(!isTetrominoColliding(1,0,currentTetromino)) {
            currentTetromino.setXposition(currentTetromino.getXposition() + 1);
        }
    }
    
    /**
     * Move tetromino shape to the left while checking for collisions.
     */        
    public void moveLeft() {
        if(!isTetrominoColliding(-1,0,currentTetromino)) {
            currentTetromino.setXposition(currentTetromino.getXposition() - 1);
        }
    }    
    
    /**
     * Rotate tetromino shape while checking for collisions.
     */
    public void rotate() {
        Tetromino tmp = new Tetromino(currentTetromino);
        tmp.rotate();
        if(!isTetrominoColliding(0,1,tmp)) {
            currentTetromino.rotate();    
        }
    }
    
    /**
     * Instantly drop tetromino while calculating "hard drop" score, 
     * side panel gets updated when tetromino lands.
     */    
    public void instantDrop() {
        while (true) {
            if (!isTetrominoColliding(0,1,currentTetromino))
            {
                currentTetromino.setYposition(currentTetromino.getYposition() + 1);
                score += 2;
            } else {
                landTetromino(currentTetromino);
                updateSidePanelLabels();
                break;
            }           
        }        
    }

    /**
     * Adds property change listener to the game board.
     * @param listener property change listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
       pcs.addPropertyChangeListener(listener);
    }
    
    /**
     * Removes property change listener from the game board.
     * @param listener property change listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
       pcs.removePropertyChangeListener(listener);
    }
    
    /**
     * Adds initial tetromino shape to the board and sets next piece.
     */
    private void addTetromino() {
        currentTetromino = tetrominoesList.remove();
        nextTetromino = tetrominoesList.remove();
        pcs.firePropertyChange(TYPE, null, nextTetromino);
    }
    
    /**
     * Adds next tetromino to the board and pick another one from tetromino list..
     */    
    private void addNextTetromino() {
        currentTetromino = nextTetromino;
        if(tetrominoesList.peek() == null) {
            tetrominoesList = generateAndShuffleTetrominoesList();
            nextTetromino = tetrominoesList.remove();
        } else {
            nextTetromino = tetrominoesList.remove();
        }

        pcs.firePropertyChange(TYPE, null, nextTetromino);
        
        // chek if we can move into initial position
        if(isTetrominoColliding(0,0,currentTetromino)) {
             currentTetromino.clearTetromino();
            isGameRunning = false;
        }
    }

    private LinkedList<Tetromino> generateAndShuffleTetrominoesList()
    {
        LinkedList<Tetromino> list = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            list.add(new Tetromino(i, 3, -1));
        }
        Collections.shuffle(list, new Random(System.nanoTime()));
        return list;
    }
    
    /**
     * Checks if tetromino shape is colliding, before it actually collides.
     * 
     * @param dx distance from vertical boundary.
     * @param dy distance from horizontal boundary.
     * @param p Tetromino object.
     * @return boolean value.
     */
    private boolean isTetrominoColliding(int dx, int dy, Tetromino p) {
        int[][] tmp = p.getTetromino();
        int y = currentTetromino.getYposition();
        int x = currentTetromino.getXposition();        
        boolean collision = false;
        
        for(int i = 0; i < TETROMINO_SIZE; i++) {
            for(int j = 0; j < TETROMINO_SIZE; j++) {
                if(tmp[i][j] != 0) {
                    int newX = x + j + dx;
                    int newY = y + i + dy;
                    if((newY >= field[i].length) || (newX >= field.length) || (newX < 0) || (newY < 0) || (field[newX][newY] != 0)) {
                        collision = true;
                    }
                }
            }
        }
        return collision;
    }
    
    /**
     * Lands tetromino shape at the bottom of the game board.
     * 
     * @param tetro Tetromino object.
     */
    private void landTetromino(Tetromino tetro) {
        int[][] tmp = tetro.getTetromino();
        int y = tetro.getYposition();
        int x = tetro.getXposition(); 

        for(int i = 0; i < TETROMINO_SIZE; i++) {
            for(int j = 0; j < TETROMINO_SIZE; j++) {
                if(tmp[i][j] != 0) {
                    field[x + j][y + i] = tmp[i][j];
                }
            }
        }
        addNextTetromino();
        checkRows();
    }
    
    /**
     * Check for completed rows and collapse them.
     */
    private void checkRows() {
        ArrayList<Integer> fullRows = new ArrayList<>();
        for(int i = 0; i < field[0].length; i++) {
            boolean isClear = true;
            for (int[] field1 : field) {
                if (field1[i] == 0) 
                {
                    isClear = false;
                }                
            }
            
            if(isClear) {
                fullRows.add(i);
            }
        }
        
        if(!fullRows.isEmpty()) {
            for(int i : fullRows) {
                for (int[] field1 : field) {
                    field1[i] = 0;
                }
            }
            collapseRows(fullRows);
        }
    }
    
    /**
     * Collapses all full rows calculates score and updates side panel.
     * 
     * @param fullRows ArrayList of all the full rows.
     */
    private void collapseRows(ArrayList<Integer> fullRows) {
        for(int i : fullRows) {
            for(int j = i; j > 0; j--) {
                for (int[] field1 : field) {
                    field1[j] = field1[j-1];
                }
            }
            linesCleared++;
        }
        calculateScore(fullRows.size());
        level = increaseLevel(level);
        updateSidePanelLabels();   
    }

    /*
     * ======================================
     * ==== Scoring logic follows below ===== 
     * ======================================
     */
    
    private void calculateScore(int linesCleared)  {
        switch(linesCleared) {
            case 1:
                score += 40*(level + 1);
                break;
            case 2:
                score += 100*(level + 1);
                break;
            case 3:
                score += 300*(level + 1);
                break;
            case 4:
                score += 1200*(level + 1);
                break;
            default:
                throw new IndexOutOfBoundsException("Invalid line number: " + linesCleared);
        }
    }

    private int increaseLevel(int currentLevel) {
        level = (linesCleared / 10);
        if(level > currentLevel)
        {
            return level;
        }
        return currentLevel;
    }

    private void updateSidePanelLabels() {
        pcs.firePropertyChange(LINES, null, linesCleared);
        pcs.firePropertyChange(SCORE, null, score);
        pcs.firePropertyChange(LEVEL, null, level);
    }        
}