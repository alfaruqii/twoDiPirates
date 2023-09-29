package main;

public class Game implements Runnable{
    private GamePanel panel;
    private GameWindow frame;
    private Thread thread;
    final private int FPS_SET = 120;
    private int frames;
    private long lastCheck;
    public Game(){
        panel = new GamePanel();
        frame = new GameWindow(panel);
        panel.requestFocus();
        startGameLoop();
    }
    public void startGameLoop(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run(){
        double timePerFrame = 1000000000.0 / FPS_SET;
        long now = System.nanoTime();
        long lastFrame = System.nanoTime();
        while (true){
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame){
                panel.repaint();
                lastFrame = now;
                frames++;
            }
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println(frames);
                frames = 0;
            }
        }
    }
}
