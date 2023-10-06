package entities;

import main.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity{
    private int aniIndex, enemyState,enemyType;
    private int aniTick,aniSpeed = 25;
    private boolean firstUpdate = true;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.35f * Game.SCALE;
    private int walkDir = LEFT;
    private boolean inAir;
    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }
    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(enemyType,enemyState)){
                aniIndex = 0;
            }
        }
    }
    public void update(int[][] lvlData){
        updateMove(lvlData);
        updateAnimationTick();
    }
    public void updateMove(int[][] lvlData){
        if(firstUpdate){
            if(!IsEntityOnFloor(hitbox,lvlData))
                inAir = true;
            firstUpdate = false;
        }
        if(inAir){
            if(CanMoveHere(hitbox.x,hitbox.y+fallSpeed, hitbox.width, hitbox.height,lvlData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }else{
                inAir = false;
                hitbox.y = GetEntityYUnderRoofOrAboveFloor(hitbox,fallSpeed);
            }
        } else {
            switch (enemyState){
                case(IDLE):
                    enemyState = RUNNING;
                    break;
                case (RUNNING):
                    float xSpeed = 0;
                    if(walkDir == LEFT){
                        xSpeed = -walkSpeed;
                    }else{
                        xSpeed = walkSpeed;
                    }
                    if(CanMoveHere(hitbox.x+xSpeed,hitbox.y, hitbox.width, hitbox.height, lvlData)){
                        if(IsFloor(hitbox,xSpeed,lvlData)){
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();
                    break;
            }
        }
    }
    public void changeWalkDir(){
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
