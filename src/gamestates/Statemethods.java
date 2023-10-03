package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Statemethods {
    public void update();
    public void draw(Graphics g);
    public void mouseReleased(MouseEvent mouseEvent);
    public void mouseEntered(MouseEvent mouseEvent);
    public void mouseClicked(MouseEvent mouseEvent);
    public void mouseMoved(MouseEvent mouseEvent);
    public void mouseDragged(MouseEvent mouseEvent);
    public void mousePressed(MouseEvent mouseEvent);
    public void keyReleased(KeyEvent keyEvent);
    public void keyPressed(KeyEvent keyEvent);
}
