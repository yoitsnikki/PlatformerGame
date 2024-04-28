/*
 * CSC 171
 * Main Class
 */

import javax.swing.JFrame;
public class Main extends Background{
	static int screenX = 1000;
	static int screenY = 1000;
	
	public static double getScreenX() {
		return screenX;
	}
	
	public static double getScreenY() {
		return screenY;
	}

	// initiate jframe
	public static void main(String[] args) {
        JFrame frame = new JFrame("Platformer Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenX,screenY);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Background background = new Background();
        frame.add(background);    
        frame.setVisible(true);  
    }
}
