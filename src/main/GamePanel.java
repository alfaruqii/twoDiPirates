package main;

import keylistener.KeyboardInput;
import mouselistener.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int xDelta = 0,yDelta=0;
    MouseInputs mouse;
    public GamePanel(){
        mouse = new MouseInputs();
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void changeXDelta(int value){
        this.xDelta+=value;
        repaint();
    }
    public void changeYDelta(int value){
        this.yDelta+=value;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.fillRect(100+xDelta,100+yDelta,200,50);
    }
}
