import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This class creates an instance of tetris game and GUI to display it to user.
 * 
 * @author Yuriy Koziy
 * @version 1.0
 */
public class Game {
    private final JPanel mainPane;
    private final Board board;

    /**
     * Constructs tetris logic and GUI.
     */
    public Game() {
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.setBackground(Color.LIGHT_GRAY);
        mainPane.setPreferredSize(new Dimension(355, 405));
                    
        board = new Board(10, 20, 0); 

        Level level = new Level(205, 405, board);  

        SidePanel sidePanel = new SidePanel(150, 405);
        sidePanel.setListener(board);
        
        board.startGame();
        
        mainPane.add(level, BorderLayout.CENTER);
        mainPane.add(sidePanel, BorderLayout.EAST);        
    }
    
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
        frame.setContentPane(game.mainPane);
        
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
