import java.awt.Color;
import java.awt.Graphics;

public class PlayerBullet extends GraphicsObject{

    double x;
    double y;
    double speed_x;
    double speed_y;

    public PlayerBullet(double x, double y){
        super(x,y);
        this.x = x;
        this.y = y;
        this.speed_y = 2;
    }

    public void draw(Graphics g){
        g.setColor(new Color(220, 52, 25));
        g.fillOval((int)this.x,(int)this.y,15,15);
    }
}
