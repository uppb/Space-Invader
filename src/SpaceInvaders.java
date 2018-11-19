// utility

// graphics
import javafx.scene.layout.BackgroundImage;

import java.awt.*;
import java.awt.Image;
// events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

// swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Runnable {

    private final int canvasWidth;
    private final int canvasHeight;
    private final Color backgroundColor;

    private final int framesPerSecond = 25;
    private final int msPerFrame = 1000 / framesPerSecond;
    private Timer timer;
    private int frame = 0;
    private double dt;
    private int ddt;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isPlayerFired;

    private Player player;
    private Alien[] aliens = new Alien[48];
    private ArrayList<PlayerBullet> bullets;
    private ArrayList<AlienBullet> alienBullets;

    private int ax;
    private int ay;
    boolean gameWon = false;
    boolean gameOver = false;

    /* Constructor for a Space Invaders game
     */
    public SpaceInvaders() {
        // fix the window size and background color
        this.canvasWidth = 600;
        this.canvasHeight = 400;
        this.backgroundColor = Color.WHITE;
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));
        // set the drawing timer
        this.timer = new Timer(msPerFrame, this);
        this.player = new Player(285,320);
        ax = 20;
        ay = 20;
        for(int i = 0; i< aliens.length; i++){
            if(i%12 == 0){
                ax = 20;
                ay += 25;
            }
            aliens[i] = new Alien(ax,ay,new Color(218, 76, 216),true);
            ax += 30;
        }
        this.bullets = new ArrayList<PlayerBullet>();
        this.alienBullets = new ArrayList<AlienBullet>();
        // FIXME initialize your game objects
    }

    /* Start the game
     */
    @Override
    public void run() {
        // show this window
        display();

        // set a timer to redraw the screen regularly
        this.timer.start();
    }

    /* Create the window and display it
     */
    private void display() {
        JFrame jframe = new JFrame();
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setContentPane(this);
        jframe.pack();
        jframe.setVisible(true);
    }

    /* Run all timer-based events
     *
     * @param e  An object describing the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // update the game objects
        update();
        // draw every object (this calls paintComponent)
        repaint(0, 0, this.canvasWidth, this.canvasHeight);
        // increment the frame counter
        this.frame++;
    }

    /* Paint/Draw the canvas.
     *
     * This function overrides the paint function in JPanel. This function is
     * automatically called when the panel is made visible.
     *
     * @param g The Graphics for the JPanel
     */
    @Override
    protected void paintComponent(Graphics g) {
        // clear the canvas before painting
        clearCanvas(g);
        if (hasWonGame()) {
            paintWinScreen(g);
        } else if (hasLostGame()) {
            paintLoseScreen(g);
        } else {
            paintGameScreen(g);
        }
    }

    /* Clear the canvas
     *
     * @param g The Graphics representing the canvas
     */
    private void clearCanvas(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
        g.setColor(oldColor);

    }

    /* Respond to key release events
     *
     * A key release is when you let go of a key
     *
     * @param e  An object describing what key was released
     */
    public void keyReleased(KeyEvent e) {
        isLeftKeyPressed = false;
        isRightKeyPressed = false;
        isPlayerFired = false;
    }

    /* Respond to key type events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyTyped(KeyEvent e) {
        // you can leave this function empty
    }

    /* Respond to key press events
     *
     * A key type is when you press then let go of a key
     *
     * @param e  An object describing what key was typed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            isLeftKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            isRightKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isPlayerFired = true;
        }

    }

    /* Move the player object smoothly
     */
    public void fireBullet(){
        if(isPlayerFired == true && dt >= 2){
            bullets.add(new PlayerBullet(player.x+12.5, player.y-20));
        }
    }

    public void fireAlienBullet(){
        if((int)(Math.random()*100+1) == 1){
            alienBullets.add(new AlienBullet(aliens[42].x+18,aliens[42].y+18));
        }
    }
    public void MoveBullet(){
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).y -= bullets.get(i).speed_y;
            if(bullets.get(i).y < 0){
                bullets.remove(i);
            }
        }
        for(int i = 0; i < alienBullets.size(); i++){
            alienBullets.get(i).y += alienBullets.get(i).speed_y;
            if(alienBullets.get(i).y > canvasHeight){
                alienBullets.remove(i);
            }
        }
    }

    public void PlayerAction(){
        if(player.x < 0){
            player.x += player.speed_x;
        }
        if(player.x > canvasWidth - 30){
            player.x -= player.speed_x;
        }
        if(isLeftKeyPressed == true){
            player.x -= player.speed_x;
        }
        if(isRightKeyPressed == true){
            player.x += player.speed_x;
        }
    }

    public void MoveAlien(){
        for(int i = 0; i < aliens.length; i++) {
            if (aliens[i].MoveRight == true) {
                aliens[i].x += aliens[i].speed_x;
            }
            if (aliens[i].MoveLeft == true) {
                aliens[i].x -= aliens[i].speed_x;
            }
        }
        for(int i = 0; i < aliens.length; i++){
            if(aliens[i].visible == true){
                if(aliens[i].x < 0){
                    for(int j = 0; j < aliens.length; j++){
                        aliens[j].MoveRight = true;
                        aliens[j].MoveLeft = false;
                        aliens[j].y += 3;
                    }
                }
                if(aliens[i].x > canvasWidth-20){
                    for(int j = 0; j < aliens.length; j++){
                        aliens[j].MoveLeft = true;
                        aliens[j].MoveRight = false;
                        aliens[j].y += 3;
                    }
                }
            }
        }
    }

    public void OnTouch(){
        for(int i=0; i < aliens.length; i++){
            boolean isCollide = false;
            if(aliens[i].visible == true){
                for(int j = 0; j < bullets.size(); j++){
                    if(bullets.get(j).x >= (aliens[i].x - 9) && bullets.get(j).x <= (aliens[i].x + 9) && bullets.get(j).y <= (aliens[i].y + 15)){
                        bullets.remove(j);
                        isCollide = true;
                        aliens[i].visible = false;
                        break;
                    }
                }
                if(isCollide == true){
                    aliens[i] = new Alien(aliens[i].x,aliens[i].y,new Color(255,255,255),false);
                }
            }

        }
    }


    /* Update the game objects
     */
    private void update() {
        PlayerAction();
        MoveAlien();
        MoveBullet();
        fireBullet();
        fireAlienBullet();
        OnTouch();
        dt++;
        ddt++;
        if(ddt > 110){ddt = 0;}
        if(dt > 2.5){dt=0;}
        // FIXME update game objects here
    }

    /* Check if the player has lost the game
     *
     * @returns  true if the player has lost, false otherwise
     */
    private boolean hasLostGame() {
        return false; // FIXME delete this when ready
    }

    /* Check if the player has won the game
     *
     * @returns  true if the player has won, false otherwise
     */
    private boolean hasWonGame() {
        boolean all = true;
        for(int i =0; i < aliens.length; i++){
            if(aliens[i].visible == true){
                all = false;
            }
        }
        if(all == true){
            return true;
        }else return false;
    }

    /* Paint the screen during normal gameplay
     *
     * @param g The Graphics for the JPanel
     */
    private void paintGameScreen(Graphics g) {
        for(int i = 0; i < aliens.length; i++){
            aliens[i].draw(g);
        }
        player.draw(g);
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }
        for(int i = 0; i < alienBullets.size(); i++){
            alienBullets.get(i).draw(g);
        }
    }

    /* Paint the screen when the player has won
     *
     * @param g The Graphics for the JPanel
     */
    private void paintWinScreen(Graphics g) {

        //about 300 pixels wide
        g.setFont(new Font("Arial",Font.BOLD,50));
        g.setColor(Color.RED);
        int xPos = getWidth()/2 - 145;
        g.drawString("GAME WON!",(xPos < 0 ? 0 : xPos),getHeight()/2);
    }

    /* Paint the screen when the player has lost
     *
     * @param g The Graphics for the JPanel
     */
    private void paintLoseScreen(Graphics g) {
        //about 310 pixels wide
        g.setFont(new Font("Arial",Font.BOLD,50));
        g.setColor(Color.RED);
        int xPos = getWidth()/2 - 155;
        g.drawString("GAME OVER, YOU LOSE",(xPos < 0 ? 0 : xPos),getHeight()/2);
    }

    public static void main(String[] args) {
        SpaceInvaders invaders = new SpaceInvaders();
        EventQueue.invokeLater(invaders);


    }
}