/*
 * CSC 171
 * Main Class
 */

import javax.swing.JFrame;
public class Main extends Background{

	// initiate jframe
	public static void main(String[] args) {
        JFrame frame = new JFrame("Platformer Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Background background = new Background();
        frame.add(background);    
        frame.setVisible(true);  
    }
}
