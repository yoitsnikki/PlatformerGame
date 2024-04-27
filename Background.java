/**
 * CSC 171
 * Platformer Game: Background Class
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Background extends JPanel{
    platformCanvas platforms = new platformCanvas();

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
    public void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            cloud.drawCloud(g);
        }
    }
    // create the background
    public Background() {
        setBackground(SKY_BLUE);
        clouds = new ArrayList<>();
        generateClouds();
        Timer time = new Timer(1000, new TimerCallback());
        time.start();
        Timer moveRocket = new Timer(20, new TimerCallback2());
        Timer addRocket = new Timer(5000, new TimerCallback3());
        moveRocket.start();
        addRocket.start();
        Rocket.addRocket(); //sends the first wave of rockets ahead of the timer

    }

    // override and draw clouds when panel is painted
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClouds(g);
        platforms.drawRectangle(g);
        Rocket.drawRockets(g);
    }
    protected class TimerCallback implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            Color c2 = new Color(255, 255, 0);
            platformCanvas.setColor(c2);
          //  platforms.setYCoord(200);
            repaint();
        }
    }
    protected class TimerCallback2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            for (Rocket rocket : Rocket.rockets) {
                rocket.move();
            }
            repaint();
        }
    }
    protected class TimerCallback3  implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            Rocket.addRocket();
            repaint();
        }
    }
}
