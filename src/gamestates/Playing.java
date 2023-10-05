package gamestates;

import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods {
    private entities.Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean pause = false;
    public Playing(Game game){
        super(game);
        initClass();
    }
    public void initClass(){
        pauseOverlay = new PauseOverlay(this);
        levelManager = new LevelManager(game);
        player = new entities.Player(200,200,(int)(64*Game.SCALE),(int)(40*Game.SCALE));
        player.loadLvlData(levelManager.getCurrenLevel().getLevelData());
    }

    @Override
    public void update() {
        if(!pause){
            levelManager.update();
            player.update();
        }else{
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        if(pause)
            pauseOverlay.draw(g);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(pause)
            pauseOverlay.mouseReleased(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            player.setAttacking(true);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if(pause)
            pauseOverlay.mouseMoved(mouseEvent);
    }

    public void unpause(){
        pause = false;
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(pause)
            pauseOverlay.mouseDragged(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(pause)
            pauseOverlay.mousePressed(mouseEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_BACK_SPACE:
                Gamestates.state = Gamestates.MENU;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                pause = !pause;
                break;
        }
    }
    public entities.Player getPlayer(){
        return player;
    }
    public void windowFocusLost(){
        player.resetDirBoolean();
    }
}
