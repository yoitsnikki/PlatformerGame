/*
 * CSC 171
 * Main Class
 */

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;

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
