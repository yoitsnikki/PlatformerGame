/**
 * CSC 171
 * Platformer Game: Player Class
 */

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Player {
	public int score;
	public int coins;
	public int jumpDist;
	public boolean isJumping;
	public int verticalCoord;
	public int horizontalCoord;
	public boolean isAlive;
	public int radius;
	
	public Player() {
        this.score = 0;
        this.coins = 0;
        this.jumpDist = 0;
        this.isJumping = false;
        this.verticalCoord = 500;
        this.horizontalCoord = 500;
        this.isAlive = true;
        this.radius = 10;
    }
	
	// keep track of score (should points be part of player class, or separate?)
    public void updateScore(int points) {
        this.score += points;
    }
    
    // update coins
    public void updateCoins(int collectedCoins) {
        this.coins += collectedCoins;
    }
    
    // jumping
    public void jump(int distance) {
        if (!isJumping) {
            this.jumpDist = distance;
            this.isJumping = true;
        }
    }
    
    // if player lands on top of platform
    public void landing() {
        if (isJumping) {
            updateScore(10); // each platform = 10 points
            this.isJumping = false;
            this.jumpDist = 0; // Reset jump distance
        }
    }
    
    // hit an obstacle like a rocket
    public void hitObstacle() {
        this.isAlive = false;
    }
    
    // if player hits bottom of a platform
    public void hitBottomPlatform () {
    	moveDown(2);
    }
    
    // update player position
    public void updatePosition(int horizontalChange, int verticalChange) {
        if (isJumping) {
            this.verticalCoord += verticalChange;
            this.horizontalCoord += horizontalChange;
        } 
    }
    
    // is player alive
    public boolean isAlive() {
        return isAlive;
    }
    
    // get score
    public int getScore() {
        return score;
    }

    // get coins
    public int getCoins() {
        return coins;
    }
    
    // move down
    public void moveDown(int distance) {
    	int playerRadius = getPlayerRadius();
        verticalCoord += distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (verticalCoord - playerRadius < 0) {
            verticalCoord = playerRadius;
        }
        
        updatePosition(0, distance); // update position coordinates that are returned
    }
    
    // move up
    public void moveUp(int distance) {
        int playerRadius = getPlayerRadius();
        verticalCoord -= distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (verticalCoord + playerRadius > 1000) {
            verticalCoord = 1000 - playerRadius;
        }
        
        updatePosition(0, -distance); // update position coordinates that are returned
        
    }
    
    // move left
    public void moveLeft(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord -= distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (horizontalCoord - playerRadius < 0) {
            horizontalCoord = playerRadius;
        }
        
        updatePosition(-distance, 0); // update position coordinates that are returned
    }
    
    // move right
    public void moveRight(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord += distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (horizontalCoord + playerRadius > 1000) {
            horizontalCoord = 1000 - playerRadius;
        }
        
        updatePosition(distance, 0); // update position coordinates that are returned
    }
    
    // get player radius
    private int getPlayerRadius() {
        return radius;
    }
    
    // return player bounds for comparison to objects
    public Ellipse2D.Double getSphereBounds() {
        return new Ellipse2D.Double(horizontalCoord - radius, verticalCoord - radius, 2 * radius, 2 * radius);
    }
    
    // draw the player
    public void draw (Graphics g) {
    	int playerX = horizontalCoord;
        int playerY = verticalCoord;
        
        g.setColor(Color.GREEN);
        g.fillOval(playerX - radius, playerY - radius, 2 * radius, 2 * radius);
    }
    
    public boolean collidesWithPlatform(Rectangle2D platform) {
        Ellipse2D.Double playerBounds = getSphereBounds(); 
        Rectangle platformBounds = platform.getBounds();
        // Check if bounds intersect
        return playerBounds.intersects(platformBounds);
    }
    public boolean collidesWithRocket(Rocket rocket) {
        Ellipse2D.Double playerBounds = getSphereBounds();
        Rectangle rocketBounds = rocket.getRocketBounds();
    
        // Check if bounds intersect
        return playerBounds.intersects(rocketBounds);
    }
    
}
