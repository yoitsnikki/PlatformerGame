/*
 * Ben Levy
 * Workshop 7
 *
 * I tried to make the code as clear as possible and added comments to explain as much as I could.
 * Email me if you have questions
 * Email: Blevy9@u.rochester.edu
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Note: the "var" keyword detect the type of variable it is assigned to, and can be
 * used instead of explicitly stating the type, I use it multiple times here but is not needed
 */

/**
 * Overview of the Workshop Solution:
 *
 * In main: create a frame and add a new {@class Workshop7Solution} panel to it
 *
 * In the {@class Workshop7Solution} constructor:
 * - Create {@class Sphere}s and add them to the panel
 * - Start the timer
 *
 * On each {@class TimerCallback}
 * - The actionPerformed method is called which {@code repaint}s the panel
 *
 * In {@method paintComponent}:
 * - Check for collisions
 * - Update the position of the spheres
 * - Draw the spheres
 *
 * In {@class Sphere}:
 * - {@method Sphere}: Constructor for the Sphere
 * - {@method setVelocity}: to set the velocity of a Sphere
 * - {@method updatePosition}: to update the position of the Sphere
 * - {@method draw} to draw the Sphere
 * - {@method checkCollisions} which calls
 * -- {@method checkWallCollision} to check for collisions with walls
 * -- {@method detectCollision} to check if the sphere is colliding with another
 * sphere
 * - {@method resolveCollision} to resolve the collision between two spheres
 */
public class Workshop7Solution extends JPanel {
    private int deltatime = 10; // in milliseconds, time between each frame
    private ArrayList<Sphere> spheres = new ArrayList<Sphere>(); // list of spheres

    public static void main(String[] args) {
        // Make the frame
        var frame = new JFrame();

        // Add the panel to the frame
        frame.add(new Workshop7Solution());

        // Boilerplate for frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Constructor for the panel
     * - Creates the spheres
     * - Adds them to panel
     * - Starts the timer
     */
    public Workshop7Solution() {

        // Width and heigh of the panel
        int width = 1000;
        int height = 800;
        setPreferredSize(new Dimension(width, height)); // set the size of the panel

        // Make i spheres
        for (int i = 0; i <= 10; i++) {
            // Create a new sphere
            var newSphere = new Sphere(rand(0, width), rand(0, height), rand(20, 50), rand(1, 3));

            // Set velocity, color and give it a reference to the panel
            newSphere.setVelocity(rand(0, 1.5), rand(0, 1.5));
            newSphere.setColor(new Color(randInt(0, 255), randInt(0, 255), randInt(0, 255)));
            newSphere.setDeltatme(deltatime);

            // Add the newSphere to the list of spheres and to the panel
            spheres.add(newSphere);
            add(newSphere);
        }

        // Create and start the timer
        Timer timer = new Timer(deltatime, new TimerCallback());
        timer.start();
    }

    /**
     * Timer callback runs {@method actionPerformed} every cycle of the timer
     * - When {@method actionPerformed} it repaints the panel
     */
    protected class TimerCallback implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    /**
     * While {@method paintComponent} is needed to draw the spheres,
     * it can also be used to update spheres in other ways before drawing\
     *
     * Here, every time repaint is called, paintComponent in order:
     * - Checks collision of spheres with walls and eachother
     * - Updates position of spheres (moves them)
     * - Draws the spheres
     * -- This last drawing step is the only thing that is visible to the user and
     * really needs to be done in paintComponent
     *
     * I draw the spheres last so that any weirdness from overlapping spheres is
     * hopefully resolved before drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Sphere s : spheres) {
            s.checkCollisions(this);
            s.updatePosition(deltatime);
            s.draw(g);
        }
    }

    /**
     * Random number generators returns a random number between min and max.
     * I use them to generate random positions, velocities, and colors for the
     * spheres but they are not needed to do this.
     *
     * Note: You could do all the random number generation back in
     * {@method Workshop7Solution} if you wanted but I do it here to make the code
     * in the constructor more compact and readable
     *
     * {@method rand} returns a double
     * {@method randInt} returns an int
     */
    private double rand(double min, double max) {
        return new Random().nextDouble(max - min) + min;
    }

    private int randInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    /**
     * Getters used to get the speed of the clock and the list of spheres
     */
    public ArrayList<Sphere> getSpheres() {
        return spheres;
    }

    public int getDeltaTime() {
        return deltatime;
    }
}

/**
 * {@class Sphere} is a subclass of JComponent and is used to create spheres
 * - {@extends JComponent} so that it can be added to the panel and be drawn
 * - Position is stored as {@double} instead of {@int} to make movement
 * calculations more precise
 * - A sphere can be drawn, updated, and checked for collisions
 */
class Sphere extends JComponent {
    private Color color;
    private double radius, mass, posX, posY, vX, vY;
    private int deltatime; // time between each frame in milliseconds

    /**
     * Constructor for {@class Sphere} that sets the radius, mass, and position of
     * the sphere
     *
     * @param posX   X coordinate of the center of the sphere
     * @param posY   Y coordinate of the center of the sphere
     * @param radius Radius of the sphere
     * @param mass   Mass of the sphere
     */
    public Sphere(double posX, double posY, double radius, double mass) {
        this.radius = radius;
        this.mass = mass;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Sets the velocity of the sphere
     * - Setter that sets 2 variables at once
     */
    public void setVelocity(double vX, double vY) {
        this.vX = vX;
        this.vY = vY;
    }

    /**
     * Updates the position of the sphere based on its velocity and the time passed
     */
    public void updatePosition(int deltaTime) {
        posX += vX * deltaTime;
        posY += vY * deltaTime;
    }

    /**
     * Draws the sphere
     * - First sets its color
     * - Then draws the circle
     *
     * **Notes on FillOval:
     * - {@code fillOval} takes in the x and y of the top left corner of the
     * bounding box
     * -- {@double posX} and {@double posY} are the center of the circle so I
     * subtract the radius from the x and y to get the top left corner
     *
     * - {@code fillOval} requires ints so I cast the doubles to ints when I call it
     * -- {@see Comments above Sphere class for why I use doubles for
     * positions instead of ints}
     * **
     */
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int) (posX - radius), (int) (posY - radius), (int) (2 * radius), (int) (2 * radius));
    }

    /**
     * Checks for collisions with walls and other spheres
     * - Checks for collisions with walls first
     * - Then checks for collisions with other spheres {@code detectCollision}
     * - **Note: {@code s!=this} is used to make sure the sphere is not checking
     * for collision with itself**
     * - If a collision is detected, it resolves the collision
     * {@code resolveCollision}
     *
     * @param panel Reference to the panel the spheres are on
     */
    public void checkCollisions(Workshop7Solution panel) {
        // check if the sphere is colliding with the walls
        checkWallCollision(panel);

        // check if the sphere is colliding with other spheres
        for (Sphere s : panel.getSpheres()) {
            if (s != this && detectCollision(s)) {
                resolveCollision(s);
            }
        }
    }

    /**
     * Checks if the sphere is colliding with the walls
     * - Does this by checking if the sphere is outside the bounds of the panel
     * - If it is colliding, it changes the velocity of the sphere to move it inside
     *
     * @param panel Reference to the panel the spheres are on
     */
    private void checkWallCollision(Workshop7Solution panel) {
        if (posX - radius < 0) {
            vX = Math.abs(vX);
        } else if (posX + radius > panel.getWidth()) {
            vX = -Math.abs(vX);
        }
        if (posY - radius < 0) {
            vY = Math.abs(vY);
        } else if (posY + radius > panel.getHeight()) {
            vY = -Math.abs(vY);
        }
    }

    /**
     * Detects if the sphere is colliding with another sphere
     *
     * @param other Sphere to check collision with
     * @return true if the spheres are colliding, false otherwise
     */
    private boolean detectCollision(Sphere other) {
        // distance formula
        double dx = posX - other.getPosX(); // x distance
        double dy = posY - other.getPosY(); // y distance
        double distance = Math.sqrt(dx * dx + dy * dy); // total distance

        // if the distance is less than the sum of the radii they are colliding
        if (distance < this.radius + other.getRadius()) {
            return true;
        }

        // if they are not colliding return false
        return false;
    }

    /**
     * Resolves the collision between two spheres:
     * Uses the formula for elastic collision to calculate the new velocities of the
     * spheres
     *
     * {@double v1} represents the velocity of the first sphere
     * {@double v2} represents the velocity of the second sphere
     *
     * @param other Sphere to resolve collision with
     */
    private void resolveCollision(Sphere other) {
        var totalMass = this.mass + other.getMass();

        // new velocities for X after colision using elastic collision equation
        var v1x = ((mass - other.getMass()) * vX + (2 * other.getMass() * other.getvX())) / totalMass;
        var v2x = ((other.getMass() - mass) * other.getvX() + (2 * mass * vX)) / totalMass;

        // new velocities for Y after colision using elastic collision equation
        var v1y = ((mass - other.getMass()) * vY + (2 * other.getMass() * other.getvY())) / totalMass;
        var v2y = ((other.getMass() - mass) * other.getvY() + (2 * mass * vY)) / totalMass;

        // set the new velocities
        this.vX = v1x;
        this.vY = v1y;
        other.setVelocity(v2x, v2y);

        // update the position of the spheres to prevent them from overlapping
        updatePosition(deltatime);
    }

    // Getters and setters
    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }

    public void setDeltatme(int deltaTime) {
        this.deltatime = deltaTime;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

/**
 * Not implemented since this part is for the workshop to think through
 * abstractly but if you want to implement go ahead
 *
 * Think about how you could implement a set of rules to get specific types of
 * agents to move on certain conditions rather than just any time the timer
 * ticks
 */
class Agent1 extends Sphere {
    public Agent1(double posX, double posY, double radius, double mass) {
        super(posX, posY, radius, mass);
    }
}