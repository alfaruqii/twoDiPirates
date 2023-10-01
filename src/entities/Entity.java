package entities;

public abstract class Entity {
    protected float x=0f,y=0f,width=0,height=0;
    public Entity(float x,float y,float width,float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
