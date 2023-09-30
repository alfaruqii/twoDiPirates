package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
    private JFrame frame;
    public GameWindow(GamePanel panel){
        frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent windowEvent) {
                panel.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent windowEvent) {

            }
        });
    }

}
