import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.cert.PolicyQualifierInfo;
import java.util.BitSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener {
    private final JButton restartButton;
    private static final String scoresFile = "best_score.txt";
    String audioDie = "audios/die.wav";

    String hugMe = "audios/hugMe.wav";
    String hit = "audios/hit.wav";
    String point = "audios/point.wav";
    private final List<Column> columns = new ArrayList<>();
    private final List<Column> deleteColumns = new ArrayList<>();
    private final int columnDistance = 150;
    static Score c = new Score();
    private int score = 0;
    static Image img = new ImageIcon("assets/background-day.png").getImage();
    static Image base = new ImageIcon("assets/base.png").getImage();
    static Image gameover = new ImageIcon("assets/gameover.png").getImage();
    static ImageIcon restartImg = new ImageIcon("assets/restartButton.png");
    static Image beginMessage = new ImageIcon("assets/message.png").getImage();
    static Image scoreImg = new ImageIcon("assets/scoreImg.png").getImage();
    static final int SCREEN_WIDTH = img.getWidth(null);
    static final int SCREEN_HEIGHT = img.getHeight(null);
    static int DELAY = 75;
    static Bird bird = new Bird(50, SCREEN_HEIGHT/2);

    Timer timer;
    boolean isRunning = false;
    boolean gameStarted = false;
    GamePanel(){
        restartButton = new JButton(restartImg);
        restartButton.setBounds((SCREEN_WIDTH - restartImg.getIconWidth())/2, 350, 100, restartImg.getIconHeight());
        restartButton.addActionListener(e -> restartGame());
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        columns.add(new Column(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
        columns.add(new Column(SCREEN_WIDTH/2 + columnDistance, 200));
        startGame();
    }
    public void startGame(){
        PlayMusic(hugMe);
        isRunning = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void restartGame(){
        score = 0;
        columns.clear();
        columns.add(new Column(SCREEN_WIDTH/2, SCREEN_HEIGHT/2));
        columns.add(new Column(SCREEN_WIDTH/2 + columnDistance, 200));
        bird.reset(50, SCREEN_HEIGHT/2);
        isRunning = true;
        gameStarted = false;
        timer.restart();
        remove(restartButton);
        requestFocus();
    }
    public static int getBestScore(){
        try {
            File file = new File(scoresFile);
            if(!file.exists()){
                return 0;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static void saveBestScore(int bestScore){
        try{
            File file = new File(scoresFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(Integer.toString(bestScore));
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        bird.draw(g);
        if(!gameStarted){
            g.drawImage(base, 0, SCREEN_HEIGHT - base.getHeight(null), null);
            g.drawImage(beginMessage, (SCREEN_WIDTH - beginMessage.getWidth(null))/2, 50, null);
        }else{
            for(Column c : columns){
                c.draw(g2d);
            }
            c.showScore(score, g);
            g.drawImage(base, 0, SCREEN_HEIGHT - base.getHeight(null), null);
            bird.draw(g);
            if(!isRunning){
                add(restartButton);
                gameOver(g);
            }
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
    public void updateColumn(){
        for(Column column : columns){
            if(column.getX() + column.getWidth() < 0){
                deleteColumns.add(column);
            }
            column.pass(7);
        }
        columns.removeAll(deleteColumns);
        deleteColumns.clear();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        if(isRunning && gameStarted){
            bird.update();
            updateColumn();
            checkCollision();
            columnAdder();
        }
        repaint();
    }
    public void columnAdder(){
        if (columns.get(columns.size() - 1).getX() < SCREEN_WIDTH - columnDistance){
            int max = SCREEN_HEIGHT - base.getHeight(null) - 50;
            int min = 100;
            int random = (int)((Math.random() * (max - min)) + min);
            columns.add(new Column(columns.get(columns.size() - 1).getX() + columnDistance, random));
        }
    }
    public void checkCollision(){
        if(bird.getY() > SCREEN_HEIGHT - base.getHeight(null) - 25|| bird.getY() < 0){
            PlayMusic(hit);
            PlayMusic(audioDie);
            isRunning = false;
        }
        for(Column c : columns){
            if(bird.getX() + bird.width() > c.getX() + c.getWidth() && !c.isPassed()){
                c.setPassed(true);
                PlayMusic(point);
                score++;
            }
            if(bird.getX() + bird.width() > c.getX() && bird.getX() + bird.width() < c.getX() + c.getWidth()){
                if(c.collides(bird)){
                    isRunning = false;
                }
            }
        }
        if(!isRunning){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        int bestScore = getBestScore();
        if(score > bestScore){
            saveBestScore(score);
            bestScore = score;
        }
        g.drawImage(scoreImg, -5, 150 + gameover.getHeight(null), null);
        c.gameOverScore(score, 233, 233, g);
        c.gameOverScore(bestScore, 233, 290, g);
        g.drawImage(gameover, (SCREEN_WIDTH-gameover.getWidth(null))/2, 100, null);
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                gameStarted = true;
                bird.jump();
            }
        }
    }
}
