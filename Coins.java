
/**
 * CSC 171
 * Coins Class
 * Platformer Game
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Coins {
	private int x;
    private int y;
    private int radius;
    private boolean active;

    public void Coin (int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 5;
        this.active = true; // coin is initially active
    }
    
    // return whether the coins are active or not
    public boolean isActive() {
        return active;
    }
    
    // set whether the coin is active or not
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // draw the coin
    public void draw(Graphics g) {
        if (active) {
        	g.setColor(Color.YELLOW);
        	g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius); // Draw filled oval
        }
    }
    
    // check for collision with a player
    public boolean collidesWithPlayer(Player player) {
    	Ellipse2D.Double playerBounds = player.getSphereBounds(); // player bounds

        //  distance between player center and coin center
        double distance = Math.sqrt(Math.pow(x - playerBounds.getCenterX(), 2) + Math.pow(y - playerBounds.getCenterY(), 2));

        // check if distance is less than sum of player radius and coin radius
        return distance < (player.getPlayerRadius() + radius);
    }
    
    // check collision with player and adjust player score accordingly
    public void checkCollisionWithPlayer(Player player) {
        if (active && collidesWithPlayer(player)) {
            player.updateScore(25); // Increase player's score by 50 when collision occurs
            setActive(false); // Make the coin inactive (disappear)
        }
    }
    
    
}
