import java.awt.Color;
import java.awt.Graphics;

public class Alien extends GraphicsObject {

    double x;
    double y;
    double speed_x;
    double speed_y;

    public Alien(double x, double y){
        super(x,y);
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.setColor(new Color(218, 76, 216));
        for (int i = 0; i < 12; i++){
            for(int j = 0; j < 4; j++){
                g.fillRect(((int)this.x)+i*30, ((int)this.y)+j*25, 18, 18);
            }

        }
    }
}
