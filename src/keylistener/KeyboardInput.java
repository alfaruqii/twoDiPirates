package keylistener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

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
                gamePanel.changeXDelta(-10);
                break;
            case KeyEvent.VK_W:
                gamePanel.changeYDelta(-10);
                break;
            case KeyEvent.VK_S:
                gamePanel.changeYDelta(10);
                break;
            case KeyEvent.VK_D:
                gamePanel.changeXDelta(10);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
