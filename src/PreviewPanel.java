import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class implements tetromino preview JPanel, which display next tetromino shape.
 * 
 * @author Yuriy Koziy
 * @version 1.0
 */
public class PreviewPanel extends JPanel {   
    private static final int SQUARE_DIMENSION = 15;

    private static int[][] currentTetrominoArray = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
    private Image blockIcon = null; 
    
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
                    getBlockIcon(type);
                    g.drawImage(blockIcon, (col)*SQUARE_DIMENSION, (row)*SQUARE_DIMENSION, null);
                }
            }
        }  
    }
    
    private void getBlockIcon(int type) {
        switch(type) {
            case 1:
                blockIcon = new ImageIcon(getClass().getResource("/resources/cyan_block.png")).getImage();
                break;
            case 2:
                blockIcon = new ImageIcon(getClass().getResource("/resources/red_block.png")).getImage();                
                break;
            case 3:
                blockIcon = new ImageIcon(getClass().getResource("/resources/green_block.png")).getImage();                
                break;
            case 4:
                blockIcon = new ImageIcon(getClass().getResource("/resources/magenta_block.png")).getImage();                
                break;
            case 5:
                blockIcon = new ImageIcon(getClass().getResource("/resources/yellow_block.png")).getImage();                
                break;
            case 6:
                blockIcon = new ImageIcon(getClass().getResource("/resources/blue_block.png")).getImage();                
                break;
            case 7:
                blockIcon = new ImageIcon(getClass().getResource("/resources/orange_block.png")).getImage();                
                break;
            default:
                throw new IllegalArgumentException("Invalid tetromino type: " + type);
        }
    }    
}
