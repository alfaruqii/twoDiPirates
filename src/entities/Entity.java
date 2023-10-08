package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x=0f,y=0f;
    protected int width=0,height=0;
    protected int aniTick, aniInd;
    protected Rectangle2D.Float hitbox;
    public Entity(float x,float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(float x,float y,int width,int height){
        hitbox = new Rectangle2D.Float(x,y,width,height);
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
}
