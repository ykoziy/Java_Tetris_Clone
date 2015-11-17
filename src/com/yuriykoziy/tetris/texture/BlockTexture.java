package com.yuriykoziy.tetris.texture;

import com.yuriykoziy.tetris.panels.PreviewPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Utility class for loading block textures.
 * 
 * @author Yuriy Koziy
 * @version 1.01
 */
public class BlockTexture {
    private static final String RESOURCE_PATH = "/com/yuriykoziy/tetris/resources/";
    private BufferedImage[] blockTextures;
    
    /**
     * Constructs BlockTexture objects that stores images for each block type.
     */    
    public BlockTexture() {
        loadBlockTextures();
    }
    
    private void loadBlockTextures() {
        blockTextures = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            try {
                blockTextures[i] = ImageIO.read(getClass().getResource(RESOURCE_PATH + i + "_block.png"));
            } catch (IOException ex) {
                Logger.getLogger(PreviewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    /**
     * @param type block type.
     * @return block Image.
     */    
    public Image getBlockTexture(int type) {
        return blockTextures[type - 1];
    }
}
