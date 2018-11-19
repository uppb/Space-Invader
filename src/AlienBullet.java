public class AlienBullet extends PlayerBullet{

    public AlienBullet(double x, double y) {
        super(x,y);
        this.x = x;
        this.y = y;
        this.speed_y = -10;
    }
}
