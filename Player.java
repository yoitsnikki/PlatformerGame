/**
 * CSC 171
 * Platformer Game: Player Class
 */

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;


/**
 * Class Player
 */

public class Player {
	public int score;
	public int coins;
	public int jumpDist;
	public boolean isJumping;
	public int verticalCoord;
	public int horizontalCoord;
	public boolean isAlive;
	
	public Player() {
        this.score = 0;
        this.coins = 0;
        this.jumpDist = 0;
        this.isJumping = false;
        this.verticalCoord = 0;
        this.horizontalCoord = 0;
        this.isAlive = true;
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
    
    // landing on platform
    public void landing() {
        if (isJumping) {
            updateScore(10); // each platform = 10 points
            this.isJumping = false;
            this.jumpDist = 0; // Reset jump distance
        }
    }
    
    // hit an obstacle
    public void hitObstacle() {
        this.isAlive = false;
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

    
    // key presses
    public void keyPressed (KeyEvent e) {
    	int keyCode = e.getKeyCode();
        int moveDistance = 10; // dist the player moves when key is pressed
        
     // check which arrow key was pressed
        switch (keyCode) {
            case KeyEvent.VK_UP:
                moveUp(moveDistance);
                break;
            case KeyEvent.VK_DOWN:
                moveDown(moveDistance);
                break;
            case KeyEvent.VK_LEFT:
                moveLeft(moveDistance);
                break;
            case KeyEvent.VK_RIGHT:
                moveRight(moveDistance);
                break;
        }
        
    }
    
    public void keyReleased(KeyEvent e) {
        // handle key releases
    }
    
    // move up
    private void moveUp(int distance) {
    	int playerRadius = getPlayerRadius();
        verticalCoord -= distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (verticalCoord - playerRadius < 0) {
            verticalCoord = playerRadius;
        }
        
        updatePosition(0, 10); // update position coordinates that are returned
    }
    
    // move down
    private void moveDown(int distance) {
        int playerRadius = getPlayerRadius();
        verticalCoord += distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (verticalCoord + playerRadius > 1000) {
            verticalCoord = 1000 - playerRadius;
        }
        
        updatePosition(0, -10); // update position coordinates that are returned
        
    }
    
    // move left
    private void moveLeft(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord -= distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (horizontalCoord - playerRadius < 0) {
            horizontalCoord = playerRadius;
        }
        
        updatePosition(-10, 0); // update position coordinates that are returned
    }
    
    // move right
    private void moveRight(int distance) {
        int playerRadius = getPlayerRadius();
        horizontalCoord += distance;
        jump(distance);
        
        // player bounces off of wall if necessary
        if (horizontalCoord + playerRadius > 1000) {
            horizontalCoord = 1000 - playerRadius;
        }
        
        updatePosition(10, 0); // update position coordinates that are returned
    }
    
    // get player radius
    private int getPlayerRadius() {
        return 10;
    }
    
    public void draw (Graphics g) {
    	int playerX = horizontalCoord;
        int playerY = verticalCoord;
        int playerRadius = 10; 
        
        g.setColor(Color.GREEN);
        g.fillOval(playerX - playerRadius, playerY - playerRadius, 2 * playerRadius, 2 * playerRadius);
    }
    
}
