/**
 * CSC 171
 * Platformer Game: Background Class
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Background extends JPanel{
	public ArrayList<Cloud> clouds;
    private static final Color SKY_BLUE = new Color(135, 206, 235);
    
    // creating clouds
    private static class Cloud {
        int x;
        int y;
        int size;

        public Cloud(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        public void drawCloud(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, size, size / 2); // multiple ovals interact and create apprx cloud shape
            g.fillOval(x + size / 3, y - size / 4, size, size / 2);
            g.fillOval(x + size / 2, y, size, size / 2);
        }
    }
    
    // cloud generation
    public void generateClouds() {
        Random random = new Random();
        int numClouds = random.nextInt(5) + 5; // randomly generate between 3-5 clouds
        for (int i = 0; i < numClouds; i++) {
            int x = random.nextInt(1000);
            int y = random.nextInt(500);
            int size = random.nextInt(50) + 50; // random cloud size btwn 50-100 pixels
            clouds.add(new Cloud(x, y, size));
        }      
        
    }
    
    // draw clouds
    private void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            cloud.drawCloud(g);
        }
    }
    
    // create the background
    public Background() {
        setBackground(SKY_BLUE);
        clouds = new ArrayList<>();
        generateClouds();
        
    }

    // override and draw clouds when panel is painted
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClouds(g);
    }
    
    
    // test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Background Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        Background background = new Background();
        frame.add(background);
        frame.setVisible(true);
     
    }
	

}
