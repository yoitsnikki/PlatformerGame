import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
public class platformCanvas {
    static Color c = Color.BLUE; 
    final static int numOfLevels = 40;
    final static int maxNumOfPlatforms =3;
    final static int heightOfPlatform = 30;
    final static int yCoordInterval = 150; 
    ArrayList <Rectangle2D> platformsList = new ArrayList<>();
    public double yCoord = 850;
    double xCoord= 0;
    double interval= 0;
    double width = 0;

    public platformCanvas(){
        createPlatforms();
    }
    public void drawRectangle(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        for (Rectangle2D rects: platformsList){
            g2d.fill(rects);
            g2d.draw(rects);}
    }
    // creates an array of Rectange2d objects 
    public void createPlatforms(){
        //loop for levels
        for (int l= 0; l<numOfLevels; l++){
            //loop for platforms in each level
            for (int i=0; i<maxNumOfPlatforms;i++){
                if (i==0){
                    interval = Randomization.randomInteval();
                    width = Randomization.width();
                    xCoord = Randomization.xCoordFirst(interval,width);
                }
                else{
                    interval = Randomization.randomInteval();
                    width = Randomization.width();
                    xCoord = Randomization.xCoordNext(interval, width);
                }
                Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heightOfPlatform);
                if (rect.getX()>1001){
                    break;
                }
                platformsList.add(rect);
            }
            Randomization.setXCoord(0);
            yCoord-= yCoordInterval;
        }
    }
    //change in each platform yCoord + 1 
    public void updateY(){
        double x,y, w,h;
        for (Rectangle2D platform : platformsList){
            x = platform.getX();
            w = platform.getWidth();
            h = platform.getHeight();
            y = platform.getY() + 1;
            platform.setRect( x,  y,  w,  h);
        }
    }
    //for debugging
    // public void YCoordList (){
    //     for (Rectangle2D platform : platformsList){
    //         System.out.println(platform.getBounds2D());
    //     }
    // }
    public static void setColor(Color c2){
        c = c2;
    }

    //40 levels for 2 min, 18 sec for all to move down 
    //34 for all to dissapear
}

 
