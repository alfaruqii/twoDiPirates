import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(){
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.fillRect(100,100,200,200);
    }
}
