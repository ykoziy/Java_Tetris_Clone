package com.yuriykoziy.tetris.main;

import com.yuriykoziy.tetris.logic.Board;
import com.yuriykoziy.tetris.panels.BoardPanel;
import com.yuriykoziy.tetris.panels.SidePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * This class creates an instance of tetris game and additional JPanels.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class Game {
    private final JPanel gamePanel;
    private final Board board;
    
    public Game() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.LIGHT_GRAY);
        gamePanel.setPreferredSize(new Dimension(355, 405));
        
        board = new Board(10, 20, 0); 

        BoardPanel boardPanel = new BoardPanel(205, 405, board);  

        SidePanel sidePanel = new SidePanel(150, 405);
        sidePanel.setListener(board);
        
        board.startGame();
        
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(sidePanel, BorderLayout.EAST);   
    }
    
    /**
     * 
     * @return game JPanel.
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }
}
