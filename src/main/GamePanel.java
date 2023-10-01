package main;

import entities.Player;
import keylistener.KeyboardInput;
import mouselistener.MouseInputs;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;

public class GamePanel extends JPanel {
    private Game game;
    MouseInputs mouse;
    public GamePanel(Game game){
        this.game = game;
        setPanelSize();
        mouse = new MouseInputs(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }


    private void setPanelSize(){
        Dimension dimension = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(dimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
    public Game getGame(){
        return game;
    }
}
