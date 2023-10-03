package entities;

import gamestates.Gamestates;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.PlayerConstant.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private int aniTick, aniInd, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false,attacking=false;
    private boolean left,up,right,down,jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private BufferedImage[][] charAnimates;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // Player or Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private int[][] lvlData;
    public Player(float x, float y,int width, int heigth) {
        super(x, y,width,heigth);
        loadAnimation();
        initHitbox(x,y,(int)(20*Game.SCALE),(int)(26*Game.SCALE));
    }

    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void setAnimation(){
        int startAni = playerAction;
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if(inAir)
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        if(attacking)
            playerAction = ATTACK_1;
        if(startAni != playerAction)
            resetAniTick();
    }

    public void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniInd++;
            if(aniInd >= GetSpriteAmount(playerAction)){
                aniInd = 0;
                attacking = false;
            }
        }
    }
    public void resetAniTick(){
        aniTick = 0;
        aniInd = 0;
    }

    public void updatePos(){
        moving = false;
        if(!inAir)
            if(!IsEntityOnFloor(hitbox,lvlData))
                inAir = true;
        if(jump){
            jump();
        }
        if(!left && !right && !inAir){
            return;
        }
        float xSpeed =0;
        if(left){
            xSpeed -= playerSpeed;
        }
        if(right){
            xSpeed += playerSpeed;
        }

        if(inAir){
            if(CanMoveHere(hitbox.x,hitbox.y+airSpeed,hitbox.width,hitbox.height,lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPost(xSpeed);
            }else{
                hitbox.y = GetEntityYUnderRoofOrAboveFloor(hitbox,airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPost(xSpeed);
            }
        }else
            updateXPost(xSpeed);
        moving = true;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void updateXPost(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            hitbox.x += xSpeed;
        }else{
            hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
        }
    }

    public void resetDirBoolean(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }
    private void loadAnimation(){
        BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.PLAYER_SPRITES);
        charAnimates = new BufferedImage[9][6];
        for(int i=0;i<charAnimates.length;i++){
            for(int j=0;j<charAnimates[i].length;j++){
                charAnimates[i][j] = img.getSubimage(j * 64,i * 40,64,40);
            }
        }
    }
    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox,lvlData))
            inAir = true;
    }
    public void render(Graphics g){
        g.drawImage(charAnimates[playerAction][aniInd],(int)(hitbox.x-xDrawOffset),(int)(hitbox.y-yDrawOffset),width,height,null);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
