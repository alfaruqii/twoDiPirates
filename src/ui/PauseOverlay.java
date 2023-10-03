package ui;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int xPos, yPos, width, height;
    public PauseOverlay(){
        loadImg();
    }
    private void update(){
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg,xPos,yPos,width,height,null);
        width = (int)(backgroundImg.getWidth() * Game.SCALE);
        height = (int)(backgroundImg.getHeight() * Game.SCALE);
        xPos = Game.GAME_WIDTH/2 - width/2;
        yPos = (int)(25 * Game.SCALE);
    }
    private void loadImg(){
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_MENU);
    }
    public void mouseReleased(MouseEvent mouseEvent) {
    }


    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseMoved(MouseEvent mouseEvent) {
    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }
}
