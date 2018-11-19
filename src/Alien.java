import java.awt.Color;
import java.awt.Graphics;

public class Alien extends GraphicsObject {

    double x;
    double y;
    double speed_x;
    boolean MoveRight;
    boolean MoveLeft;
    Color color;
    boolean visible;

    public Alien(double x, double y,Color color,boolean visible){
        super(x,y);
        this.x = x;
        this.y = y;
        this.speed_x = 2.8;
        this.MoveRight = true;
        this.MoveLeft = false;
        this.color = color;
        this.visible = visible;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect((int)this.x, (int)this.y, 18, 18);
    }

}
