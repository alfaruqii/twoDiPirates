package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x=0f,y=0f;
    protected int width=0,height=0;
    protected int aniTick, aniIndex;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed ;
    protected int state;
    protected Rectangle2D.Float hitbox;
    public Entity(float x,float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(int width,int height){
        hitbox = new Rectangle2D.Float(x,y,(int)(width*Game.SCALE),(int)(height*Game.SCALE));
    }
    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public int getState(){
        return state;
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
}
