package gamestates;

import main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButton.URM_SIZE;

public class GameOptions extends State implements Statemethods{
    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;
    public GameOptions(Game game){
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() {
        int menuX = (int)(387*Game.SCALE);
        int menuY = (int)(325*Game.SCALE);
        menuB = new UrmButton(menuX,menuY,URM_SIZE,URM_SIZE,2);
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionsBackgroundImg = LoadSave.GetSpritesAtlas(LoadSave.OPTIONS_MENU);
        bgW = (int)(optionsBackgroundImg.getWidth()*Game.SCALE);
        bgH = (int)(optionsBackgroundImg.getHeight()*Game.SCALE);
        bgX = Game.GAME_WIDTH/2-bgW/2;
        bgY = (int)(33*Game.SCALE);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        g.drawImage(optionsBackgroundImg,bgX,bgY,bgW,bgH,null);
        menuB.draw(g);
        audioOptions.draw(g);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,menuB)){
            if(menuB.isMousePressed())
                Gamestates.state = Gamestates.MENU;
        }else
            audioOptions.mouseReleased(mouseEvent);
        menuB.resetBools();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        menuB.setMouseOver(false);
        if(isIn(mouseEvent,menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(mouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        audioOptions.mouseDragged(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(isIn(mouseEvent,menuB)){
            menuB.setMousePressed(true);
        }else
            audioOptions.mousePressed(mouseEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestates.state = Gamestates.MENU;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }
    private boolean isIn(MouseEvent e, PauseButton b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
