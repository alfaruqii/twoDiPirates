package main;

import entities.Player;
import keylistener.KeyboardInput;
import mouselistener.MouseInputs;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private Player player;
    MouseInputs mouse;
    public GamePanel(){
        setPanelSize();
        mouse = new MouseInputs(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        player = new Player(100,100);
    }


    private void setPanelSize(){
        Dimension dimension = new Dimension(1280,800);
        setPreferredSize(dimension);
    }
    public Player getPlayer(){
        return player;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.render(g);
    }
}
