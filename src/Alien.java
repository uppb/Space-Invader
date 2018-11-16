import java.awt.Color;
import java.awt.Graphics;

public class Alien extends GraphicsObject {

    double x;
    double y;
    double speed_x;
    boolean MoveRight;
    boolean MoveLeft;

    public Alien(double x, double y, double speed_x){
        super(x,y);
        this.x = x;
        this.y = y;
        this.speed_x = speed_x;
        this.MoveRight = true;
        this.MoveLeft = false;
    }

    public void draw(Graphics g){
        g.setColor(new Color(218, 76, 216));
        g.fillRect((int)this.x, (int)this.y, 18, 18);
    }
}
