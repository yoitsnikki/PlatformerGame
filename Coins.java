
/**
 * CSC 171
 * Coins Class
 * Platformer Game
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Coins {
	int x, y;
    static int radius;
    static boolean active;
    public static ArrayList<Coins> coinsList = new ArrayList<>();

    public Coins (int x, int y) {
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
    
    // getters for x and y coordinates
    public int getCoinX() {
        return x;
    }

    public int getCoinY() {
        return y;
    }
    
    // return array list
    public static ArrayList<Coins> returnCoinsList() {
    	return coinsList;
    }
    
    // draw the coin
    public static void drawCoins(Graphics g) {
        	g.setColor(Color.YELLOW);
        	for (Coins coin : coinsList) {
        		if (active) {
        		g.fillOval(coin.getCoinX() - radius, coin.getCoinY() - radius, 2 * radius, 2 * radius); // Draw filled oval
        		}
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
    
    // check collision with player and adjust player score accordingly (run on timer with platforms)
    public void checkCollisionWithPlayer(Player player) {
        if (active && collidesWithPlayer(player)) {
            player.updateScore(25); // increase player's score by 25 when collision occurs
            setActive(false); // make the coin inactive (disappear)
        }
    }
    
    // generate random list of coins
    public static void generateCoins() {
		int numCoins = 15; // Random number of coins
        for (int i = 0; i < numCoins; i++) {
        	Random random = new Random();
            // generate random coordinates for the coin within the game boundaries
            int coinX = random.nextInt(1000 - (2 * radius)) + radius;
            int coinY = random.nextInt(1000 - (2 * radius)) + radius;
            coinsList.add(new Coins(coinX, coinY)); // create and add coin to the list
        }
    }
    
}
