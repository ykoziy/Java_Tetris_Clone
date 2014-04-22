
import java.util.Random;

/**
 * This class represents each individual tetromino piece and allows to control each piece individually.
 * 
 * @author Yuriy Koziy
 * @version 1.0
 */

public class Tetromino {  
    private int[][] shape = new int[4][4];
    private int type;
    private int rotation;
    private int xPosition, yPosition;
    
    private final int[][][][] tetrominoes = {
        //I shape
        {
            {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
            },
            {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
            }
        },
        //Z shape
        {
            {
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 2},
                {0, 0, 2, 2},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 2},
                {0, 0, 2, 2},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }            
        },
        //S shape
        {
            {
                {0, 0, 0, 0},
                {0, 0, 3, 3},
                {0, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 3, 0},
                {0, 0, 3, 3},
                {0, 0, 0, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 0},
                {0, 0, 3, 3},
                {0, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 3, 0},
                {0, 0, 3, 3},
                {0, 0, 0, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }
        },
        //T shape
        {
            {
                {0, 0, 0, 0},
                {4, 4, 4, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 4, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 4, 0, 0},
                {4, 4, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 4, 0, 0},
                {4, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }            
        },
        //O shape
        {
            {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }
        },
        //J shape
        {
            {
                {0, 0, 0, 0},
                {0, 6, 6, 6},
                {0, 0, 0, 6},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 6, 6},
                {0, 0, 6, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 6, 0, 0},
                {0, 6, 6, 6},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 6, 0},
                {0, 0, 6, 0},
                {0, 6, 6, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }
        },
        //L shape
        {
            {
                {0, 0, 0, 0},
                {0, 7, 7, 7},
                {0, 7, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 7, 0},
                {0, 0, 7, 0},
                {0, 0, 7, 7},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 0, 0, 7},
                {0, 7, 7, 7},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            },
            {
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }
        }        
    };
    
    /**
     * Constructs a new tetromino of a random shape at specified X and Y positions.
     * 
     * @param xPosition tetromino X position.
     * @param yPosition tetromino Y position.
     */
    public Tetromino(int xPosition, int yPosition) {
        Random r = new Random();
        int random = r.nextInt(7);
        shape = pickShape(random, 0);
        rotation = 0;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    /**
     * Constructs a new tetromino of specified shape at specified X and Y positions.
     * 
     * @param type tetromino shape
     * @param xPosition tetromino X position.
     * @param yPosition tetromino Y position.
     */    
    public Tetromino(int type, int xPosition, int yPosition) {
        shape = pickShape(type, 0);
        rotation = 0;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }    
    /**
     * Copy constructor.
     * 
     * @param tetro existing Tetromino object.
     */
    public Tetromino(Tetromino tetro) {
        this.shape = tetro.getTetromino();
        this.type = tetro.getType();
        this.rotation = tetro.getRotation();
    }    
    
    /**
     * 
     * @return tetromino shape array 4x4.
     */
    public int[][] getTetromino() {
        return shape;
    }    
    
    /**
     * 
     * @return tetromino X position.
     */
    public int getXposition() {
        return xPosition;
    }
    
    /**
     * 
     * @return tetromino Y position.
     */    
    public int getYposition()
    {
        return yPosition;
    } 
    
    /**
     * 
     * @param xPosition X position of a tetromino. 
     */
    public void setXposition(int xPosition) {
        this.xPosition = xPosition;
    }
    
    /**
     * 
     * @param yPosition Y position of a tetromino. 
     */    
    public void setYposition(int yPosition) {
        this.yPosition = yPosition;
    } 
 
    /**
     * Rotates tetromino shape.
     */
    public void rotate() {
        rotation++;
        if(rotation >= 4)
        {
            rotation = 0;
        }
        shape = tetrominoes[type][rotation];
    }
    
    /**
     * Clears tetromino 4x4 array.
     */
    public void clearTetromino() {    
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                shape[i][j] = 0;
            }
        }
    }
    
    /**
     * Picks shape from tetrominoes arrray based on type and rotation.
     * 
     * @param type tetromino type.
     * @param rotation tetromino rotation state.
     * @return 
     */
    private int[][] pickShape(int type, int rotation) {
        this.type = type;
        return tetrominoes[type][rotation];
    }

    private int getRotation() {
        return rotation;
    }
    
    public int getType() {
        return type;
    }           
}
