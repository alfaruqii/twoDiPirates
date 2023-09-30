package main;

public class Game implements Runnable{
    private GamePanel panel;
    private GameWindow frame;
    private Thread thread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private long lastCheck;
    public Game(){
        panel = new GamePanel();
        frame = new GameWindow(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        startGameLoop();
    }
    public void startGameLoop(){
        thread = new Thread(this);
        thread.start();
    }
    public void update(){
        panel.getPlayer().updateGame();
    }
    @Override
    public void run(){
        double timePerFrame = 1000000000.0 / FPS_SET;
        double updatePerFrame = 1000000000.0 / UPS_SET;
        int frames = 0;
        int update = 0;
        double deltaU = 0;
        double deltaF = 0;
        long previosTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        while (true){
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previosTime) / updatePerFrame;
            deltaF += (currentTime - previosTime) / timePerFrame;
            previosTime = currentTime;
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
                update =0;
            }
        }
    }
}
