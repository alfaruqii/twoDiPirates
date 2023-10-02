package keylistener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestates;
import main.GamePanel;

import static utilz.Constants.Directions.*;

public class KeyboardInput implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInput(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (Gamestates.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(keyEvent);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(keyEvent);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (Gamestates.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(keyEvent);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(keyEvent);
                break;
            default:
                break;
        }
    }
}
