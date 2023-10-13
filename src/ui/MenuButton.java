package ui;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.Buttons.*;

public class MenuButton {
    private int xPos, yPos, indexRow,index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Rectangle bounds;
    private BufferedImage[] imgs;
    private Gamestates state;
    private boolean mouseover,mousePressed;
    public MenuButton(int xPos, int yPos,int indexRow,Gamestates state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;
        this.indexRow = indexRow;
        loadImgs();
        initButton();
    }
    public void initButton(){
        bounds = new Rectangle(xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT);
    }
    public void loadImgs(){
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.BUTTON_ATLAS);
        for(int i=0;i<imgs.length;i++){
            imgs[i] = temp.getSubimage(i*B_WIDTH_DEFAULT, indexRow*B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }
    public void update(){
        index = 0;
        if(mouseover){
            index = 1;
        }
        if(mousePressed){
            index = 2;
        }
    }
    public void draw(Graphics g){
        g.drawImage(imgs[index],xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT,null);
    }
    public void setMouseOver(boolean mouseover){
        this.mouseover = mouseover;
    }
    public boolean isMouseOver(){
        return mouseover;
    }
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }
    public boolean isMousePressed(){
        return mousePressed;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void applyGameState(){
        Gamestates.state = state;
    }
    public void resetBools(){
        mouseover = false;
        mousePressed = false;
    }
    public Gamestates getState(){
        return state;
    }
}
