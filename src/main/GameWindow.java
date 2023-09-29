package main;

import javax.swing.*;

public class GameWindow extends JFrame {
    private JFrame frame;
    public GameWindow(GamePanel panel){
        frame = new JFrame();
        frame.add(panel);
//        frame.setSize(400,400);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
