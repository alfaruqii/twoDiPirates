public class Game {
    private GamePanel panel;
    private GameWindow frame;
    public Game(){
        panel = new GamePanel();
        frame = new GameWindow(panel);
    }
}
