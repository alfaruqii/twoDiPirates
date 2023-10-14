package entities;

import audio.AudioPlayer;
import gamestates.Gamestates;
import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.GRAVITY;
import static utilz.Constants.PlayerConstant.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private boolean moving = false,attacking=false;
    private boolean left,right,jump;
    private int tileY = 0;
    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerGrowSpeed = 15;
    private int powerGrowTick;
    private BufferedImage[][] charAnimates;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // Player or Gravity
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

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
    private int healthWidth = healthBarWidth;
    private int powerBarWidth = (int)(104 * Game.SCALE);
    private int powerBarHeight = (int)(2 * Game.SCALE);
    private int powerBarXStart = (int)(44 * Game.SCALE);
    private int powerBarYStart = (int)(34 * Game.SCALE);
    private int powerWidth = powerBarWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackCheck;
    private int[][] lvlData;
    private Playing playing;
    public Player(float x, float y, int width, int heigth, Playing playing) {
        super(x, y,width,heigth);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * Game.SCALE;
        loadAnimation();
        initAttackBox();
        initHitbox(20,26);
    }
    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }
    private void checkAttack(){
        if(attackCheck && aniIndex != 1)
            return;
        attackCheck = true;
        if(powerAttackActive){
            attackCheck = false;
        }
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSond();
    }
    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x,y,(int)(20*Game.SCALE),(int)(20*Game.SCALE));
        resetAttackBox();
    }
    public void update(){
        updateHealthBar();
        updatePowerBar();
        if(currentHealth <= 0){
            //playing.setGameOver(true);
            if(state != DEAD){
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            }else if(aniIndex == GetSpriteAmount(DEAD)-1 && aniTick >= ANI_SPEED-1){
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            }else
                updateAnimationTick();
            return;
        }
        updateAttackBox();
        updatePos();
        if(moving){
            checkPotionTouched();
            checkSpikesTouced();
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
            if(powerAttackActive){
                powerAttackTick++;
                if(powerAttackTick >= 35){
                    powerAttackActive = false;
                    powerAttackTick = 0;
                }
            }
        }
        if(attacking || powerAttackActive)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouced() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void updateAttackBox(){
        if(left && right){
            if(flipW == -1){
                attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
            }
            if(flipW == 1){
                attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
            }
        } else if(right || (powerAttackActive || flipW == 1)){
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
        }else if(left || (powerAttackActive || flipW == -1)){
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (int)(10 * Game.SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }
    private void updatePowerBar(){
        powerWidth = (int)((powerValue/(float)powerMaxValue)*powerBarWidth);
        powerGrowTick++;
        if(powerGrowTick >= powerGrowSpeed){
            powerGrowTick = 0;
            changePower(1);
            changePower(1);
        }
    }

    public void setAnimation(){
        int startAni = state;
        if(moving)
            state = RUNNING;
        else
            state = IDLE;
        if(inAir)
            if(airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        if(powerAttackActive){
            state = ATTACK;
            aniIndex = 1;
            aniTick = 0;
            return;
        }
        if(attacking){
            state = ATTACK;
            if(startAni != ATTACK){
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if(startAni != state)
            resetAniTick();
    }

    public void updateAnimationTick(){
        aniTick++;
        if(aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(state)){
                aniIndex = 0;
                attacking = false;
                attackCheck = false;
            }
        }
    }
    public void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    public void updatePos(){
        moving = false;
        if(jump){
            jump();
        }
        if(!inAir)
            if(!powerAttackActive)
                if((!left && !right) || (left && right))
                    return;
        float xSpeed =0;
        if(left && !right){
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if(right && !left){
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }
        if(powerAttackActive){
            if((!left && !right) || (left && right)){
                if(flipW == -1)
                    xSpeed = -walkSpeed;
                else
                    xSpeed = walkSpeed;
            }
            xSpeed *= 3;
        }
        if(!inAir)
            if(!IsEntityOnFloor(hitbox,lvlData))
                inAir = true;

        if(inAir && !powerAttackActive){
            if(CanMoveHere(hitbox.x,hitbox.y+airSpeed,hitbox.width,hitbox.height,lvlData)){
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
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
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void updateXPost(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            hitbox.x += xSpeed;
        }else{
            hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed);
            if(powerAttackActive){
                powerAttackActive = false;
                powerAttackTick = 0;
            }
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
        g.drawImage(charAnimates[state][aniIndex],(int)(hitbox.x-xDrawOffset)-xLvlOffset+flipX,(int)(hitbox.y-yDrawOffset),width*flipW,height,null);
        drawUI(g);
    }
    public void drawUI(Graphics g){
        // draw a background
        g.drawImage(statusBarImg,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);

        //draw a health
        g.setColor(Color.RED);
        g.fillRect(statusBarX+healthBarXstart,statusBarY+healthBarYStart,healthWidth,healthBarHeight);

        //draw a power
        g.setColor(Color.YELLOW);
        g.fillRect(powerBarXStart+statusBarX,powerBarYStart+statusBarY,powerWidth,powerBarHeight);
    }
    public void resetAll(){
        resetDirBoolean();
        attacking = false;
        airSpeed = 0f;
        moving = false;
        inAir = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;
        if(!IsEntityOnFloor(hitbox,lvlData))
            inAir = true;
        resetAttackBox();
    }
    public void resetAttackBox(){
        if(flipW == -1){
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
        }
        if(flipW == 1){
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void changePower(int value) {
        powerValue += value;
        if(powerValue >= powerMaxValue)
            powerValue = powerMaxValue;
        else if(powerValue <= 0)
            powerValue = 0;
    }

    public void kill() {
        currentHealth = 0;
    }
    public void powerAttack(){
        if(powerAttackActive)
            return;
        if(powerValue >= 60){
            powerAttackActive = true;
            changePower(-60);
        }
    }
    public int getTileY(){
        return  tileY;
    }
}
