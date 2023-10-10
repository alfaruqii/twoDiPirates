package objects;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.ObjectConstant.*;

public class GameObject {
    protected int x,y,objType;
    protected Rectangle2D.Float hitbox;
    protected int aniTick,aniIndex;
    protected int xDrawOffset, yDrawOffset;
    protected boolean doAnimation, active = true;
    public GameObject(int x, int y,int objType){
        this.x = x;
        this.y = y;
        this.objType = objType;
    }
    public void updateAnimationTick(){
        aniTick++;
        if(aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(objType)){
                aniIndex = 0;
                if(objType == BARREL || objType == BOX){
                    doAnimation = false;
                    active = false;
                }else if(objType == CANNON_LEFT || objType == CANNON_RIGHT){
                    doAnimation = false;
                }
            }
        }
    }
    public void reset(){
        aniTick = 0;
        aniIndex = 0;
        active = true;
        if(objType == BARREL || objType == BOX || objType == CANNON_LEFT || objType == CANNON_RIGHT){
            doAnimation = false;
        }else{
            doAnimation = true;
        }
    }
    protected void initHitbox(int width,int height){
        hitbox = new Rectangle2D.Float(x,y,(int)(width* Game.SCALE),(int)(height*Game.SCALE));
    }
    public void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(hitbox.x-xLvlOffset),(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public int getAniTick() {
        return aniTick;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public void setxDrawOffset(int xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public void setyDrawOffset(int yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }

    public boolean isDoAnimation() {
        return doAnimation;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
