import javax.print.attribute.standard.PresentationDirection;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.ImageObserver;
import java.io.File;

public class Bird {
    String wing = "audios/wing.wav";
    static Image upflap = new ImageIcon("assets/redbird-upflap.png").getImage();
    static Image downflap = new ImageIcon("assets/redbird-downflap.png").getImage();

    static Image midflap = new ImageIcon("assets/redbird-midflap.png").getImage();
    int x, y;
    static int velocity;
    static int gravity;
    static boolean isJumping;
    static boolean falling; //for you <3
    public Bird(int x, int y){
        this.x = x;
        this.y = y;
        velocity = 0;
        gravity = 1;
        isJumping = false;
    }
    public void reset(int x, int y){
        this.x = x;
        this.y = y;
        velocity = 0;
        gravity = 1;
        isJumping = false;
    }
    public int getX(){ return x;}
    public int width(){
        return upflap.getWidth(null);
    }
    public int height(){
        return upflap.getHeight(null);
    }
    public int getY(){ return y;}
    public void update(){
        velocity += gravity;
        y += velocity;
        if(velocity < 0){
            isJumping = true;
        }else{
            isJumping = false;
        }
    }
    public static void PlayMusic(String location){
        try {
            File musicPath = new File(location);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }else{
                System.out.println("No music for you ma boy");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void jump(){
        PlayMusic(wing);
        velocity = -7;
    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        double angle = Math.toRadians(30);
        int cx = downflap.getWidth(null)/2;
        int cy = downflap.getHeight(null)/2;
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(angle, cx, cy);
        if(isJumping){
            g.drawImage(upflap, x, y, null);
        }else {
            g2d.drawImage(downflap, transform, null);
        }
    }
}
