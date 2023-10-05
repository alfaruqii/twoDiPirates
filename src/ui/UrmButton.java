package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButton.*;

public class UrmButton extends PauseButton{
    private int rowIndex, index;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    public UrmButton(int xPos, int yPos, int width, int height,int rowIndex) {
        super(xPos, yPos, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }
    private void loadImgs(){
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.URM_BUTTON);
        this.imgs = new BufferedImage[3];
        for(int i=0;i<imgs.length;i++){
            imgs[i] = temp.getSubimage(i*URM_SIZE_DEFAULT,rowIndex*URM_SIZE_DEFAULT,URM_SIZE_DEFAULT,URM_SIZE_DEFAULT);
        }
    }
    public void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }
    public void draw(Graphics g){
        g.drawImage(imgs[index],xPos,yPos,width,height,null);
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
