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

/**
 * @getVerticalCoord: getter for vertical coordinate
 * @getHorizontalCoord: getter for horizontal coordinate
 * @getScore: getter for score
 * @getCoins: getter for coins collected
 * @updateScore: updates score with points from either platform or coins
 * @hitObstacle: if player hits an obstacle, they die
 * @hitBottomPlatform: player moves down 10 if they hit the bottom of the platform
 * @hitTopPlatform: player moves up 10 if they hit the top of the platform
 * @updatePosition: takes adjusts player position
 * @isAlive: returns if player is alive or not
 * @moveDown: moves player down in respond to key input and checks for wall collision
 * @moveUp: moves player up in respond to key input and checks for wall collision
 * @moveLeft: moves player left in respond to key input and checks for wall collision
 * @moveRight: moves player right in respond to key input and checks for wall collision
 * @getPlayerRadius: getter for the sphere's radius
 * @getSphereBounds: gets bounds of sphere
 * @collidesWithPlatform: checks if player collides with the platforms
 * @collidesWithPlatformTop: checks which side of the platform the player collides with and calls a method accordingly
 * @collidesWithRocket: checks if player collides with rocket and calls method accordingly
 */


public class Player {
	public int score;
	public int jumpDist;
	public boolean isJumping;
	public int verticalCoord;
	public int horizontalCoord;
	public boolean isAlive;
	public int radius;
	
	public Player() {
        this.score = 0;
        this.jumpDist = 0;
        this.isJumping = false;
        this.verticalCoord = 500;
        this.horizontalCoord = 500;
        this.isAlive = true;
        this.radius = 10;
    }
	
	// getters
	public double getVerticalCoord() {
		return verticalCoord;
	}
	
	public double getHorizontalCoord() {
		return horizontalCoord;
	}
	
    public int getScore() {
        return score;
    }

    
    // get player radius
    public int getPlayerRadius() {
        return radius;
    }
	
    /**
     * method to keep track of score
     * @param points
     */
    public void updateScore(int points) {
        this.score += points;
    }
    
    
    // hit an obstacle like a rocket
    public void hitObstacle() {
        this.isAlive = false;
    }
    
    // if player hits bottom of a platform
    public void hitBottomPlatform () {
    	moveDown(10);
    }
    
    // if player hits top of a platform
    public void hitTopPlatform () {
    	moveUp(10);
    	updateScore(10);
    }
    
    /**
     * method to update player position, taking how much the coordinates should change as input
     * @param horizontalChange
     * @param verticalChange
     */
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
    

    // move down
    public void moveDown(int distance) {
    	int playerRadius = getPlayerRadius();
        verticalCoord += distance;
        
        // player bounces off of wall if necessary
        if (verticalCoord + playerRadius >= 950) {
            verticalCoord = 950 - playerRadius;
            hitObstacle();
        }
        
        updatePosition(0, verticalCoord); // update position coordinates that are returned
    }
    
    // move up
    public void moveUp(int distance) {
        int playerRadius = getPlayerRadius();
        verticalCoord -= distance;
        
        // player bounces off of wall if necessary
        if (verticalCoord - playerRadius <= 0) {
            verticalCoord = playerRadius;
        }
        
        updatePosition(0, verticalCoord); // update position coordinates that are returned
        
    }
    
    // move left
    public void moveLeft(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord -= distance;
        
        // player bounces off of wall if necessary
        if (horizontalCoord - playerRadius <= 0) {
            horizontalCoord = playerRadius;
        }
        
        updatePosition(horizontalCoord, 0); // update position coordinates that are returned
    }
    
    // move right
    public void moveRight(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord += distance;
        
        // player bounces off of wall if necessary
        if (horizontalCoord + playerRadius >= 1000) {
            horizontalCoord = 1000 - playerRadius;
        }
        
        updatePosition(horizontalCoord, 0); // update position coordinates that are returned
    }
    
    
    // return player bounds for comparison to objects
    public Ellipse2D.Double getSphereBounds() {
        return new Ellipse2D.Double(horizontalCoord - radius, verticalCoord - radius, 2 * radius, 2 * radius);
    }
    
    // draw the player
    public void drawPlayer (Graphics g) {
    	int playerX = horizontalCoord;
        int playerY = verticalCoord;
        
        g.setColor(Color.GREEN);
        g.fillOval(playerX - radius, playerY - radius, 2 * radius, 2 * radius);
    }
    
    // GENERAL PLATFORM INTERSECTION
    public boolean collidesWithPlatform(Rectangle2D platform) {
        Ellipse2D.Double playerBounds = getSphereBounds(); 
        Rectangle platformBounds = platform.getBounds();
        // Check if bounds intersect
        return playerBounds.intersects(platformBounds);
    }
    
    // WHICH SIDE OF THE PLATFORM IS THE HIT ON?
    public boolean collidesWithPlatformTop(Rectangle2D platform) {
        Ellipse2D.Double playerBounds = getSphereBounds(); 
        Rectangle platformBounds = platform.getBounds();
        
        if (playerBounds.getCenterY() < platformBounds.getY()) {
        	return true;
        } else {
        	return false;
        }
    }

    // if player collides with a rocket
    public boolean collidesWithRocket(Rocket rocket) {
        Ellipse2D.Double playerBounds = getSphereBounds();
        Rectangle rocketBounds = rocket.getRocketBounds();

        if (playerBounds.intersects(rocketBounds)) {
            hitObstacle();
            return true;
        }
        return false;
    }
    
}
