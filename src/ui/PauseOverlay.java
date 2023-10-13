package ui;

import gamestates.Gamestates;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.URMButton.URM_SIZE;
import static utilz.Constants.UI.VolumeButton.*;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int xPos, yPos, width, height;
    private UrmButton menuB, replayB, unpauseB;
    private AudioOptions audioOptions;
    private Playing playing;
    public PauseOverlay(Playing playing){
        this.playing = playing;
        audioOptions = playing.getGame().getAudioOptions();
        loadImg();
        createUrmButtons();
    }
    private void createUrmButtons(){
        int menuX = (int)(313 * Game.SCALE);
        int replayX = (int)(387 * Game.SCALE);
        int unpauseX = (int)(461 * Game.SCALE);
        int buttonY = (int)(325 * Game.SCALE);
        this.menuB = new UrmButton(menuX,buttonY,URM_SIZE,URM_SIZE,2);
        this.replayB = new UrmButton(replayX,buttonY,URM_SIZE,URM_SIZE,1);
        this.unpauseB = new UrmButton(unpauseX,buttonY,URM_SIZE,URM_SIZE,0);
    }
    public void update(){
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
    }
    private void loadImg(){
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.PAUSE_MENU);
        width = (int)(backgroundImg.getWidth() * Game.SCALE);
        height = (int)(backgroundImg.getHeight() * Game.SCALE);
        xPos = Game.GAME_WIDTH/2 - width/2;
        yPos = (int)(25 * Game.SCALE);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg,xPos,yPos,width,height,null);
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        audioOptions.draw(g);
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,menuB)){
            if(menuB.isMousePressed()){
                Gamestates.state = Gamestates.MENU;
                playing.unpause();
            }
        } else if(isIn(mouseEvent,replayB)){
            if(replayB.isMousePressed())
            {
                playing.resetAll();
                playing.unpause();
            }
        } else if(isIn(mouseEvent,unpauseB)){
            if(unpauseB.isMousePressed())
                playing.unpause();
        } else {
            audioOptions.mouseReleased(mouseEvent);
        }
        replayB.resetBools();
        menuB.resetBools();
        unpauseB.resetBools();
    }


    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,menuB))
            menuB.setMousePressed(true);
        else if(isIn(mouseEvent,replayB))
            replayB.setMousePressed(true);
        else if(isIn(mouseEvent,unpauseB))
            unpauseB.setMousePressed(true);
        else
            audioOptions.mousePressed(mouseEvent);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        if(isIn(mouseEvent,menuB)){
            menuB.setMouseOver(true);
        } else if(isIn(mouseEvent,replayB)){
            replayB.setMouseOver(true);
        } else if(isIn(mouseEvent,unpauseB)){
            unpauseB.setMouseOver(true);
        } else
            audioOptions.mouseMoved(mouseEvent);
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        audioOptions.mouseDragged(mouseEvent);
    }
    private boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
