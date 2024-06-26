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
import java.awt.geom.Rectangle2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * @drawCloud: draws the clouds with graphics
 * @generateClouds: randomly generates the clouds and adds them to cloud array list
 * @drawClouds: uses drawCloud to draw all the clouds generated into the cloud array list by generateClouds
 * @Background: main class that generates the rockets, platforms, and timers
 * @paintComponent: draws clouds, platforms, rockets
 * @movePlatformsTimer: timer for the platforms to move across the screen
 * @TimerCallback2: keeps the rockets moving across the screen
 * @TimerCallback3: generates the rockets onto the screen
 */

public class Background extends JPanel implements KeyListener {
    platformCanvas platforms = new platformCanvas();
    Player player = new Player();

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

        Timer movePlatforms = new Timer(20, new movePlatformsTimer());
        movePlatforms.start();
        Timer moveRocket = new Timer(20, new TimerCallback2());
        Timer addRocket = new Timer(5000, new TimerCallback3());
        moveRocket.start();
        addRocket.start();
        Rocket.addRocket();

        setFocusable(true); // Ensure the panel can receive key events
        addKeyListener(this);
        
        // end game if player is not alive anymore
        if (player.isAlive() == false) {
        	// game over clause ; stop generating rockets + panels, and print score on middle of screen
        }
    }
    // override and draw clouds when panel is painted
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClouds(g);
        platforms.drawRectangle(g);
        player.draw(g);
        Rocket.drawRockets(g);
    }

    //timer to keep platforms moving across the screen
    protected class movePlatformsTimer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            boolean m = false;
            platforms.updateY();
            for (Rectangle2D platform: platforms.platformsList){
                m = player.collidesWithPlatform(platform);
                
                //if collision is detected then
                if(m==true){
                    
                    boolean side = player.collidesWithPlatformTop(platform);
                    if (side == true) {
                    	player.hitTopPlatform();
                    } else {
                    	player.hitBottomPlatform();
                    }
                    
                }
            }
            repaint();
        }
    }
    
    // moves the rockets across the screen
    protected class TimerCallback2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean r = false;
            for (Rocket rocket : Rocket.rockets) {
                rocket.move();
                r = player.collidesWithRocket(rocket);
                if (r==true) {
                	player.hitObstacle();
                }
                
            }
            repaint();
        }
    }
    
    // adds the rockets to the screen
    protected class TimerCallback3  implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            Rocket.addRocket();
            repaint();
        }
    }

    // key presses
    @Override
    public void keyPressed (KeyEvent e) {
        int keyCode = e.getKeyCode();
        int moveDistance = 10; // dist the player moves when key is pressed

        // check which arrow key was pressed
        switch (keyCode) {
            case KeyEvent.VK_UP:
                player.moveUp(10);
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown(10);
                break;
            case KeyEvent.VK_LEFT:
                player.moveLeft(10);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight(10);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // handle key releases
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typing
    }
    
}
