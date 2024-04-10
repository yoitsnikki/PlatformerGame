/**
 * CSC 171
 * Platformer Game: Player Class
 */

import java.util.ArrayList;

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
    
}
