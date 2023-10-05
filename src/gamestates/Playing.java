package gamestates;

import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {
    private entities.Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean pause = false;
    private int xLvlOffset;
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage backgroundImg,bigCloud,smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();
    public Playing(Game game){
        super(game);
        initClass();
        backgroundImg = LoadSave.GetSpritesAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpritesAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpritesAtlas(LoadSave.SMALL_CLOUDS);
    }
    public void initClass(){
        pauseOverlay = new PauseOverlay(this);
        levelManager = new LevelManager(game);
        player = new entities.Player(200,200,(int)(64*Game.SCALE),(int)(40*Game.SCALE));
        player.loadLvlData(levelManager.getCurrenLevel().getLevelData());
        smallCloudsPos = new int[8];
        for(int i=0;i<smallCloudsPos.length;i++)
            smallCloudsPos[i] = (int)(90*Game.SCALE) + rnd.nextInt((int) (100*Game.SCALE));
    }

    @Override
    public void update() {
        if(!pause){
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }else{
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if(diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        if(diff < leftBorder)
            xLvlOffset += diff - leftBorder;
        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        if(xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        drawClouds(g);
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        if(pause){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_WIDTH);
            pauseOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for(int i=0;i<3;i++)
            g.drawImage(bigCloud,i*BIG_CLOUD_WIDTH-(int)(xLvlOffset * 0.3),(int)(93*Game.SCALE),BIG_CLOUD_WIDTH,BIG_CLOUD_HEIGHT,null);
        for(int i=0;i<smallCloudsPos.length;i++)
            g.drawImage(smallCloud,SMALL_CLOUD_WIDTH*4*i - (int)(xLvlOffset * 0.7),smallCloudsPos[i],SMALL_CLOUD_WIDTH,SMALL_CLOUD_HEIGHT,null);
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
