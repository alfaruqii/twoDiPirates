package gamestates;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImage;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadBackground();
        loadButtons();
    }

    private void loadBackground(){
        backgroundImage = LoadSave.GetSpritesAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int)(backgroundImage.getWidth() * Game.SCALE);
        menuHeight = (int)(backgroundImage.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH/2 - menuWidth/2;
        menuY = (int)(45*Game.SCALE);
    }
    public void loadButtons(){
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2,(int)(150*Game.SCALE),0,Gamestates.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2,(int)(220*Game.SCALE),1,Gamestates.OPTION);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2,(int)(290*Game.SCALE),2,Gamestates.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage,menuX,menuY,menuWidth,menuHeight,null);
        for(MenuButton mb :buttons){
            mb.draw(g);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        for (MenuButton mb : buttons){
            if(isIn(mouseEvent,mb)){
                if(mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        for (MenuButton mb : buttons){
            if(isIn(mouseEvent,mb))
                mb.setMousePressed(true);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for (MenuButton mb : buttons){
            mb.setMouseOver(false);
        }

        for (MenuButton mb : buttons){
            if(isIn(mouseEvent,mb)){
                mb.setMouseOver(true);
                break;
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestates.state = Gamestates.PLAYING;
    }
}
