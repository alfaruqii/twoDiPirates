package entities;

import java.awt.*;

public abstract class Entity {
    protected float x=0f,y=0f;
    protected int width=0,height=0;
    protected Rectangle hitbox;
    public Entity(float x,float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox();
    }

    public void drawHitbox(Graphics g){
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
    }
    public void updateHitbox(){
        hitbox.x = (int)x;
        hitbox.y = (int)y;
        hitbox.width = width;
        hitbox.height = height;
    }
    public void initHitbox(){
        hitbox = new Rectangle((int)x,(int)y,width,height);
    }
}
