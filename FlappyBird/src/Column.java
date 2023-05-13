import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Column {
    String audioDie = "audios/die.wav";
    String hit = "audios/hit.wav";
    static Image column = new ImageIcon("assets/pipe-green.png").getImage();
    int x, y;
    int gap = 100;
    public boolean passed;
    public Column(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void pass(int speed){
        this.x -= speed;
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
    public int getWidth(){
        return column.getWidth(null);
    }
    public int getX(){
        return x;
    }
    public int getY(){ return y;}
    public void setPassed(boolean passed){
        this.passed = passed;
    }
    public boolean isPassed(){
        return passed;
    }
    public  boolean collides(Bird bird){
        int birdY = bird.getY();
        if(birdY < y && birdY > y - 100 && birdY + bird.height() < y && birdY + bird.height() > y - 100){
            return false;
        }
        PlayMusic(hit);
        PlayMusic(audioDie);
        return true;
    }

    public void draw(Graphics2D g){
        g.drawImage(column, x, y, null);
        g.drawImage(column, x, y - gap, column.getWidth(null), -column.getHeight(null), null);
    }

}
