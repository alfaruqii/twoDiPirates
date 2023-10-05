package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButton.URM_SIZE_DEFAULT;
import static utilz.Constants.UI.VolumeButton.*;

public class VolumeButton extends PauseButton{
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index;
    private int buttonX;
    private boolean mouseOver,mousePressed;
    private int maxX,minX;
    public VolumeButton(int xPos, int yPos, int width, int height) {
        super(xPos + width/2, yPos, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH /2;
        buttonX = xPos+width/2;
        this.xPos = xPos;
        this.width = width;
        this.minX = xPos+VOLUME_WIDTH/2;
        this.maxX = xPos+width-VOLUME_WIDTH/2;
        loadImgs();
    }

    private void loadImgs(){
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.VOLUME_BUTTON);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i*VOLUME_DEFAULT_WIDTH,0,VOLUME_DEFAULT_WIDTH,VOLUME_DEFAULT_HEIGHT);
        }
        slider = temp.getSubimage(3*VOLUME_DEFAULT_WIDTH,0,SLIDER_DEFAULT_WIDTH,VOLUME_DEFAULT_HEIGHT);
    }
    public void draw(Graphics g){
        g.drawImage(slider,xPos,yPos,width,height,null);
        g.drawImage(imgs[index],buttonX-VOLUME_WIDTH/2,yPos,VOLUME_WIDTH,height,null);
    }
    public void update(){
        index = 0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
    public void changeX(int x){
        if(x < minX)
            buttonX = minX;
        else if(x > maxX)
            buttonX = maxX;
        else
            buttonX = x;

        bounds.x = buttonX - VOLUME_WIDTH /2;
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
