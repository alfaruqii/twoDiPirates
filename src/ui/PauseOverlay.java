package ui;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.PauseButton.SOUND_SIZE_DEFAULT;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private SoundButton musicButton,sfxButton;
    private int xPos, yPos, width, height;
    public PauseOverlay(){
        loadImg();
        createSoundButton();
    }
    public void update(){
        musicButton.update();
        sfxButton.update();
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
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,musicButton))
            if(musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        if(isIn(mouseEvent,sfxButton))
            if(sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        musicButton.resetBools();
        sfxButton.resetBools();
    }


    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,musicButton))
            musicButton.setMousePressed(true);
        if(isIn(mouseEvent,sfxButton))
            sfxButton.setMousePressed(true);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        if(isIn(mouseEvent,musicButton))
            musicButton.setMouseOver(true);
        if(isIn(mouseEvent,sfxButton))
            sfxButton.setMouseOver(true);
    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }
    private boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
