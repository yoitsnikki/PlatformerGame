
//random widthfor 2dRect
//random xCoord iteration 

import java.util.Random;
public class Randomization {
    static double randomWidth = 0; 
    static double randomXCoord;
    public static double randomWidth(){
        final double limit = 100;
        Random random = new Random();
        double rand_double1 = random.nextDouble(400-limit)+limit;
        randomWidth = rand_double1;
        return rand_double1;
    }
    public static double randomXCoord(){
        Random random = new Random();
        //to make it dependent on the previous values
        System.out.println(randomXCoord);
       // double x = randomXCoord +10;
//when + 10 it van be over 100
        double rand_double2 = random.nextDouble(1000);
        randomXCoord = rand_double2;
        return rand_double2;
    }   
    public static void setXCoord(double x){
        randomXCoord = 0;
    }
}