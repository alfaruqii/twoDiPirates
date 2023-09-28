package main;

import keylistener.KeyboardInput;
import mouselistener.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int xDelta = 0,yDelta=0;
    MouseInputs mouse;
    public GamePanel(){
        mouse = new MouseInputs(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void changeXDelta(int value){
        this.xDelta+=value;
        super.repaint();
    }
    public void changeYDelta(int value){
        this.yDelta+=value;
        super.repaint();
    }
    public void setRectPos(int x,int y){
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(xDelta,yDelta,200,50);
    }
}
