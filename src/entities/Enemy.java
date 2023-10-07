package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity{
    protected int aniIndex, enemyState,enemyType;
    protected int aniTick,aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean inAir;
    protected int maxHealth,currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;
    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }
    protected void firstUpdateCheck(int[][] lvlData){
        if(!IsEntityOnFloor(hitbox,lvlData))
            inAir = true;
        firstUpdate = false;
    }
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(enemyType,enemyState)){
                aniIndex = 0;
                switch (enemyState){
                    case ATTACK,HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }
    protected void changeWalkDir(){
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x,hitbox.y+fallSpeed, hitbox.width, hitbox.height,lvlData)){
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }else{
            inAir = false;
            hitbox.y = GetEntityYUnderRoofOrAboveFloor(hitbox,fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }
    protected void newState(int enemyState){
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }
    public void hurt(int amount){
        currentHealth -= amount;
        if(currentHealth <= 0)
            newState(DEAD);
        else
            newState(HIT);
    }
    protected void move(int[][] lvlData){
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
    }

    protected boolean isPlayerInRange(Player player) {
        int absVal = (int)Math.abs(player.hitbox.x-hitbox.x);
        return absVal <= attackDistance * 5;
    }
    protected boolean isPlayerCloseForAttack(Player player){
        int absVal = (int)Math.abs(player.hitbox.x-hitbox.x);
        int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);
        if(playerTileY == tileY)
            return absVal <= attackDistance;
        return false;
    }

    protected void turnTowardsPlayer(Player player){
        if(player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);
        if(playerTileY == tileY)
            if(isPlayerInRange(player))
                if(IsSightIsClear(lvlData,hitbox,player.hitbox,tileY))
                    return true;
        return false;
    }

    protected void checkPlayerHit(Rectangle2D attackBox, Player player){
        if(attackBox.intersects(player.hitbox)){
            player.changeHealth(-GetEnemyDamage(enemyType));
        }
        attackChecked = true;
    }


    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
    public int getFlipX(){
        if(walkDir == RIGHT)
            return width;
        else
            return 0;
    }
    public int getFlipW(){
        if(walkDir == RIGHT)
            return -1;
        return 1;
    }
    public boolean isActive(){
        return active;
    }
    public void resetEnemy(){
        hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
    }
}
