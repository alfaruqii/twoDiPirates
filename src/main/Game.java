package main;

import entities.Player;
import gamestates.Gamestates;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{
    private GamePanel panel;
    private GameWindow frame;
    private Thread thread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;
    public static gamestates.Menu menu;
    public static Playing playing;
    public static final int TILES_DEFAULT = 32;
    public static final float SCALE = 1f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public Game(){
        initClass();
        panel = new GamePanel(this);
        frame = new GameWindow(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        startGameLoop();
    }
    public void initClass(){
        menu = new Menu(this);
        playing = new Playing(this);
    }
    public void startGameLoop(){
        thread = new Thread(this);
        thread.start();
    }
    public void update(){
        switch (Gamestates.state){
            case MENU:
                menu.update();
                break;
            case PLAYING :
                playing.update();
                break;
            case OPTION:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }
    public void render(Graphics g){
        switch (Gamestates.state){
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            default:
                break;
        }
    }
    @Override
    public void run(){
        double timePerFrame = 1000000000.0 / FPS_SET;
        double updatePerFrame = 1000000000.0 / UPS_SET;
        int frames = 0;
        int update = 0;
        double deltaU = 0;
        double deltaF = 0;
        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        while (true){
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / updatePerFrame;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if(deltaU >= 1){
                update();
                update++;
                deltaU--;
            }
            if(deltaF >= 1){
                panel.repaint();
                frames++;
                deltaF--;
            }
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS : "+frames+" UPS : "+update);
                frames = 0;
                update = 0;
            }
        }
    }
    public void windowFocusLost(){
        if(Gamestates.state == Gamestates.PLAYING)
            playing.getPlayer().resetDirBoolean();
    }
    public gamestates.Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
}
