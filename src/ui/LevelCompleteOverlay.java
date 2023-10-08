package ui;

import gamestates.Gamestates;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButton.*;

public class LevelCompleteOverlay {
    private Playing playing;
    private BufferedImage img;
    private UrmButton menu,next;
    private int bgW,bgH,bgX,bgY;
    public LevelCompleteOverlay(Playing playing){
        this.playing = playing;
        initButton();
        initImg();
    }
    private void initButton(){
        int menuX = (int)(330 * Game.SCALE);
        int nextX = (int)(445 * Game.SCALE);
        int y = (int)(195 * Game.SCALE);
        next = new UrmButton(nextX,y,URM_SIZE,URM_SIZE,0);
        menu = new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
    }
    private void initImg(){
        img = LoadSave.GetSpritesAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int)(img.getWidth() * Game.SCALE);
        bgH = (int)(img.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH /2 - bgW/2;
        bgY = (int)(75 * Game.SCALE);
    }
    public void update(){
        next.update();
        menu.update();
    }
    public void draw(Graphics g){
        g.drawImage(img,bgX,bgY,bgW,bgH,null);
        next.draw(g);
        menu.draw(g);
    }
    private boolean isIn(UrmButton b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);
        if(isIn(menu,e))
            menu.setMouseOver(true);
        if(isIn(next,e))
            next.setMouseOver(true);
    }
    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e))
            if(menu.isMousePressed())
            {
                playing.resetAll();
                Gamestates.state = Gamestates.MENU;
            }
        if(isIn(next,e))
            if(next.isMousePressed())
                playing.loadNextLevel();
        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e){
        next.setMousePressed(false);
        menu.setMousePressed(false);
        if(isIn(menu,e))
            menu.setMousePressed(true);
        if(isIn(next,e))
            next.setMousePressed(true);
    }
}
