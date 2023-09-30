package keylistener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                gamePanel.getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_W:
                gamePanel.getPlayer().setUp(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getPlayer().setRight(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.getPlayer().setLeft(false);
                gamePanel.getPlayer().setRight(false);
                gamePanel.getPlayer().setUp(false);
                gamePanel.getPlayer().setDown(false);
                break;
        }
    }
}
