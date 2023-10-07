package entities;

import gamestates.Gamestates;
import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    // Status Bar UI
    BufferedImage statusBarImg;
    private int statusBarWidth = (int)(192 * Game.SCALE);
    private int statusBarHeight = (int)(58 * Game.SCALE);
    private int statusBarX = (int)(10 * Game.SCALE);
    private int statusBarY = (int)(10 * Game.SCALE);
    private int healthBarWidth = (int)(150 * Game.SCALE);
    private int healthBarHeight = (int)(4 * Game.SCALE);
    private int healthBarXstart = (int)(34 * Game.SCALE);
    private int healthBarYStart = (int)(14 * Game.SCALE);
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private int flipX = 0;
    private int flipW = 1;
    private Rectangle2D.Float attackBox;
    private boolean attackCheck;
    private int[][] lvlData;
    private Playing playing;
    public Player(float x, float y, int width, int heigth, Playing playing) {
        super(x, y,width,heigth);
        this.playing = playing;
        loadAnimation();
        initAttackBox();
        initHitbox(x,y,(int)(20*Game.SCALE),(int)(27*Game.SCALE));
    }

    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x,y,(int)(20*Game.SCALE),(int)(20*Game.SCALE));
    }
    public void update(){
        updateHealthBar();
        if(currentHealth <= 0){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePos();
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }
    private void checkAttack(){
        if(attackCheck && aniInd != 1)
            return;
        attackCheck = true;
        playing.checkEnemyHit(attackBox);
    }
    private void updateAttackBox(){
        if(right){
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
        }else if(left){
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (int)(10 * Game.SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth/(float)maxHealth)*healthBarWidth);
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
        if(attacking){
            playerAction = ATTACK;
            if(startAni != ATTACK){
                aniInd = 1;
                aniTick = 0;
                return;
            }
        }
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
                attackCheck = false;
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
        if(!inAir)
            if((!left && !right) || (left && right))
                return;
        float xSpeed =0;
        if(left){
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if(right){
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
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
    public void changeHealth(int health){
        currentHealth += health;
        if(currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
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
        statusBarImg = LoadSave.GetSpritesAtlas(LoadSave.HEALTH_POWER_BAR);
        charAnimates = new BufferedImage[7][8];
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
    public void render(Graphics g,int xLvlOffset){
        g.drawImage(charAnimates[playerAction][aniInd],(int)(hitbox.x-xDrawOffset)-xLvlOffset+flipX,(int)(hitbox.y-yDrawOffset),width*flipW,height,null);
        drawUI(g);
    }
    public void drawUI(Graphics g){
        g.drawImage(statusBarImg,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
        g.setColor(Color.RED);
        g.fillRect(statusBarX+healthBarXstart,statusBarY+healthBarYStart,healthWidth,healthBarHeight);
    }
    private void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x-xLvlOffset,(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
    public void resetAll(){
        resetDirBoolean();
        attacking = false;
        moving = false;
        inAir = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;
        if(!IsEntityOnFloor(hitbox,lvlData))
            inAir = true;
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
