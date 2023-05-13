import javax.swing.*;
import java.awt.*;

public class Score {
    static Image zero = new ImageIcon("numbers/0.png").getImage();
    static Image one = new ImageIcon("numbers/1.png").getImage();
    static Image two = new ImageIcon("numbers/2.png").getImage();
    static Image three = new ImageIcon("numbers/3.png").getImage();
    static Image four = new ImageIcon("numbers/4.png").getImage();
    static Image five = new ImageIcon("numbers/5.png").getImage();
    static Image six = new ImageIcon("numbers/6.png").getImage();
    static Image seven = new ImageIcon("numbers/7.png").getImage();
    static Image eight = new ImageIcon("numbers/8.png").getImage();
    static Image nine = new ImageIcon("numbers/9.png").getImage();

    void gameOverScore(int a, int x, int y,Graphics g){
        if(a == 0){
            g.drawImage(zero, x, y, null);
        }
        int s = a;
        int digit;
        int swift = 0;
        while (s > 0) {
            digit = s % 10;
            switch (digit) {
                case 0 -> {
                    g.drawImage(zero, x - swift, y, null);
                    swift += zero.getWidth(null);
                }
                case 1 -> {
                    g.drawImage(one, x - swift, y, null);
                    swift += one.getWidth(null);
                }
                case 2 -> {
                    g.drawImage(two, x - swift, y, null);
                    swift += two.getWidth(null);
                }
                case 3 -> {
                    g.drawImage(three, x - swift, y, null);
                    swift += three.getWidth(null);
                }
                case 4 -> {
                    g.drawImage(four, x - swift, y, null);
                    swift += four.getWidth(null);
                }
                case 5 -> {
                    g.drawImage(five, x - swift, y, null);
                    swift += five.getWidth(null);
                }
                case 6 -> {
                    g.drawImage(six, x - swift, y, null);
                    swift += six.getWidth(null);
                }
                case 7 -> {
                    g.drawImage(seven, x - swift, y, null);
                    swift += seven.getWidth(null);
                }
                case 8 -> {
                    g.drawImage(eight, x - swift, y, null);
                    swift += eight.getWidth(null);
                }
                case 9 -> {
                    g.drawImage(nine, x - swift, y, null);
                    swift += nine.getWidth(null);
                }
            }
            s = s/10;
        }
    }
    void showScore(int score, Graphics g){
        if(score == 0){
            g.drawImage(zero, 144 - zero.getWidth(null), 0, null);
        }
        int s = score;
        int digit;
        int swift = 15;
        while (s > 0) {
            digit = s % 10;
            switch (digit) {
                case 0 -> g.drawImage(zero, 144 - swift, 0, null);
                case 1 -> g.drawImage(one, 144 - swift, 0, null);
                case 2 -> g.drawImage(two, 144 - swift, 0, null);
                case 3 -> g.drawImage(three, 144 - swift, 0, null);
                case 4 -> g.drawImage(four, 144 - swift, 0, null);
                case 5 -> g.drawImage(five, 144 - swift, 0, null);
                case 6 -> g.drawImage(six, 144 - swift, 0, null);
                case 7 -> g.drawImage(seven, 144 - swift, 0, null);
                case 8 -> g.drawImage(eight, 144 - swift, 0, null);
                case 9 -> g.drawImage(nine, 144 - swift, 0, null);
            }
            swift += 20;
            s = s/10;
        }
    }
}
