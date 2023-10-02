package entities;

import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.PlayerConstant.*;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity{
    private int aniTick, aniInd, aniSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false,attacking=false;
    private boolean left,up,right,down;
    private float playerSpeed = 2.0f;
    private BufferedImage[][] charAnimates;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private int[][] lvlData;
    public Player(float x, float y,int width, int heigth) {
        super(x, y,width,heigth);
        loadAnimation();
        initHitbox(x,y,20*Game.SCALE,28*Game.SCALE);
    }

    public void updateGame(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void setAnimation(){
        int startAni = playerAction;
        if(moving){
            playerAction = RUNNING;
        }else{
            playerAction = IDLE;
        }
        if(attacking){
            playerAction = ATTACK_1;
        }
        if(startAni != playerAction){
            resetAniTick();
        }
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
        if(!left && !right && !up && !down){
            return;
        }
        float xSpeed =0,ySpeed=0;
        if(left && !right){
            xSpeed = -playerSpeed;
        } else if(right && !left){
            xSpeed = playerSpeed;
        }
        if(up && !down){
            ySpeed = -playerSpeed;
        } else if(down && !up){
            ySpeed = playerSpeed;
        }
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y+ySpeed,hitbox.width,hitbox.height,lvlData)){
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
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
    }
    public void render(Graphics g){
        g.drawImage(charAnimates[playerAction][aniInd],(int)(hitbox.x-xDrawOffset),(int)(hitbox.y-yDrawOffset),width,height,null);
        drawHitbox(g);
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
}
