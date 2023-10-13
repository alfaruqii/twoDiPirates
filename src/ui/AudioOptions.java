package ui;

import gamestates.Gamestates;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButton.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButton.VOLUME_HEIGHT;

public class AudioOptions {
    private SoundButton musicButton,sfxButton;
    private VolumeButton volumeButton;
    public AudioOptions(){
        createVolumeButtons();
        createSoundButton();
    }
    private void createVolumeButtons() {
        int vX = (int)(309 * Game.SCALE);
        int vY = (int)(278 * Game.SCALE);
        volumeButton = new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
    }
    private void createSoundButton(){
        int soundX = (int)(450 * Game.SCALE);
        int musicY = (int)(140 * Game.SCALE);
        int sfxY = (int)(186 * Game.SCALE);
        musicButton = new SoundButton(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButton = new SoundButton(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
    }
    public void draw(Graphics g){
        musicButton.draw(g);
        sfxButton.draw(g);
        volumeButton.draw(g);
    }
    public void update(){
        musicButton.update();
        sfxButton.update();
        volumeButton.update();
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
        else if(isIn(mouseEvent,volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);
        if(isIn(mouseEvent,musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(mouseEvent,sfxButton))
            sfxButton.setMouseOver(true);
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
