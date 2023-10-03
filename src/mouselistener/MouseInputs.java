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
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(mouseEvent);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        switch (Gamestates.state){
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(mouseEvent);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(mouseEvent);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        switch (Gamestates.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(mouseEvent);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(mouseEvent);
                break;
            default:
                break;
        }
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
        switch (Gamestates.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(mouseEvent);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(mouseEvent);
                break;
            default:
                break;
        }
//        gamePanel.setRectPos(mouseEvent.getX(),mouseEvent.getY());
    }
}
