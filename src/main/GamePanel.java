package main;

import keylistener.KeyboardInput;
import mouselistener.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    private float xDelta = 0,yDelta=0;
    private float xDir=1,yDir=1;
    private Color color = new Color(255,255,255);
    private Random random;
    private long lastCheck;
    private ArrayList<MyRect> rects = new ArrayList<>();
    MouseInputs mouse;
    public GamePanel(){
        random = new Random();
        mouse = new MouseInputs(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void changeXDelta(int value){
        this.xDelta+=value;
    }
    public void changeYDelta(int value){
        this.yDelta+=value;
    }
    public void spawnRect(int x, int y){
        rects.add(new MyRect(x,y));
    }
    public void setRectPos(int x,int y){
        this.xDelta = x;
        this.yDelta = y;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(MyRect rect : rects){
            rect.updateRect();
            rect.draw(g);
        }
        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta,(int)yDelta,50,50);
    }

    public void updateRectangle(){
        xDelta += xDir;
        if(xDelta > 400 || xDelta < 0){
            color = getRndColor();
            xDir *= -1;
        }
        yDelta += yDir;
        if(yDelta > 400 || yDelta < 0){
            color = getRndColor();
            yDir *= -1;
        }
    }
    public Color getRndColor(){
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r,g,b);
    }
    public class MyRect {
        int x,y,w,h;
        int xDir=1,yDir=1;
        Color color;
        public MyRect(int x, int y){
            this.x = x;
            this.y = y;
            w = random.nextInt(50);
            h = w;
            color = getRndColor();
        }
        public void updateRect(){
            this.x += xDir;
            this.y += yDir;
            if((x) > 400 || x < 0){
                xDir *= -1;
                color = getRndColor();
            }
            if((y) > 400 || y < 0){
                yDir *= -1;
                color = getRndColor();
            }
        }
        public void draw(Graphics g){
            g.setColor(color);
            g.fillRect(x,y,w,h);
        }
    }
}
