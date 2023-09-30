package main;

import entities.Player;

import java.awt.*;

public class Game implements Runnable{
    private GamePanel panel;
    private GameWindow frame;
    private Thread thread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    public Game(){
        initClass();
        panel = new GamePanel(this);
        frame = new GameWindow(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        startGameLoop();
    }
    public void initClass(){
        this.player = new Player(200,200);
    }
    public void startGameLoop(){
        thread = new Thread(this);
        thread.start();
    }
    public void update(){
        player.updateGame();
    }
    public void render(Graphics g){
        player.render(g);
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
    public Player getPlayer(){
        return player;
    }
    public void windowFocusLost(){
        player.resetDirBoolean();
    }
}
