import java.awt.Color;
import java.awt.Graphics;


public class AlienBullet extends PlayerBullet {

    double x;
    double y;
    double speed_x;
    double speed_y;


    public AlienBullet(double x, double y){
        super(x,y);
        this.x = x;
        this.y = y;
        this.speed_y = 1.5;
    }

    public void draw(Graphics g){
        g.setColor(new Color(75, 147, 220));
        g.fillOval((int)this.x,(int)this.y,5,20);
    }
}
