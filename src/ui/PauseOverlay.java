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
    private SoundButton musicButton,sfxButton;
    private int xPos, yPos, width, height;
    private UrmButton menuB, replayB, unpauseB;
    private Playing playing;
    private VolumeButton volumeButton;
    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadImg();
        createSoundButton();
        createUrmButtons();
        createVolumeButtons();
    }

    private void createVolumeButtons() {
        int vX = (int)(309 * Game.SCALE);
        int vY = (int)(278 * Game.SCALE);
        volumeButton = new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
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
        musicButton.update();
        sfxButton.update();
        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeButton.update();
    }
    private void createSoundButton(){
        int soundX = (int)(450 * Game.SCALE);
        int musicY = (int)(140 * Game.SCALE);
        int sfxY = (int)(186 * Game.SCALE);
        musicButton = new SoundButton(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButton = new SoundButton(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
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
        musicButton.draw(g);
        sfxButton.draw(g);
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        volumeButton.draw(g);
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,musicButton))
            if(musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        if(isIn(mouseEvent,sfxButton))
            if(sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        if(isIn(mouseEvent,menuB))
            if(menuB.isMousePressed()){
                Gamestates.state = Gamestates.MENU;
                playing.unpause();
            }
        if(isIn(mouseEvent,replayB))
            if(replayB.isMousePressed())
            {
                playing.resetAll();
                playing.unpause();
            }
        if(isIn(mouseEvent,unpauseB))
            if(unpauseB.isMousePressed())
                playing.unpause();
        musicButton.resetBools();
        sfxButton.resetBools();
        replayB.resetBools();
        menuB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }


    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,musicButton))
            musicButton.setMousePressed(true);
        else if(isIn(mouseEvent,sfxButton))
            sfxButton.setMousePressed(true);
        else if(isIn(mouseEvent,menuB))
            menuB.setMousePressed(true);
        else if(isIn(mouseEvent,replayB))
            replayB.setMousePressed(true);
        else if(isIn(mouseEvent,unpauseB))
            unpauseB.setMousePressed(true);
        else if(isIn(mouseEvent,volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);
        if(isIn(mouseEvent,musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(mouseEvent,sfxButton))
            sfxButton.setMouseOver(true);
        else if(isIn(mouseEvent,menuB))
            menuB.setMouseOver(true);
        else if(isIn(mouseEvent,replayB))
            replayB.setMouseOver(true);
        else if(isIn(mouseEvent,unpauseB))
            unpauseB.setMouseOver(true);
        else if(isIn(mouseEvent,volumeButton))
            volumeButton.setMouseOver(true);
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if(volumeButton.isMousePressed())
            volumeButton.changeX(mouseEvent.getX());
    }
    private boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
