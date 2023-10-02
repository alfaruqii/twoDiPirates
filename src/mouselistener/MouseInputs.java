package mouselistener;

import gamestates.Gamestates;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        switch (Gamestates.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(mouseEvent);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(mouseEvent);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
//        gamePanel.setRectPos(mouseEvent.getX(),mouseEvent.getY());
    }
}
