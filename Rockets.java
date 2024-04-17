import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/*
This code uses sample canvas of auto generated for me by ChatGPT, so we still need to readjust stuff for our own canvas
*/
public class Rockets {

    /*
    initialize ArrayList for rockets as part of the game
    initialize ArrayList for platforms
    initialize timer that's responsible for sending a rocket once in 5 seconds
     */
    private static List<Rocket> rockets = new ArrayList<>();
    private static Timer timer;

    //class for rockets object
    private static class Rocket {
        int x, y;

        public Rocket(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //Made this move method assuming that rockets fly from right border of the screen to the left
        public void move() {
            x -= 5; // Move rocket leftward
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 10, 10); // Draw rocket as a red square
        }
    }
    
    Timer rocketTimer = new Timer(5000, new Timer1());
    rocketTimer.start();
    
   // component to draw rockets
    public void drawRockets(Graphics g) {
        for (Rocket rocket : rockets) {
                 rocket.draw(g);
             }
    }

    //what occurs when the timer is called	
    class Timer1 implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		rockets.add(new Rocket(980, 200));
    	    rockets.add(new Rocket(980, 400));
    	    rockets.add(new Rocket(980, 600));
    	    rockets.add(new Rocket(980, 800));
    	}
    }
}

		
