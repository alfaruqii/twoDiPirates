package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstant.*;

public class Player extends Entity{
    private int aniTick, aniInd, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false,attacking=false;
    private boolean left,up,right,down;
    private float playerSpeed = 2.0f;
    private BufferedImage[][] charAnimates;
    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
    }

    public void updateGame(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void setPlayerDir(int direction){
        this.playerDir = direction;
        moving = true;
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
            }
        }
    }
    public void resetAniTick(){
        aniTick = 0;
        aniInd = 0;
    }

    public void updatePos(){
        moving = false;
            if(left && !right){
                x -= playerSpeed;
                moving = true;
            } else if(right && !left){
                x += playerSpeed;
                moving = true;
            }
            if(up && !down){
                y -= playerSpeed;
                moving = true;
            } else if(down && !up){
                y += playerSpeed;
                moving = true;
            }
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }
    private void loadAnimation(){
        InputStream is = getClass().getResourceAsStream("/res/player_sprites.png");
        try {
            BufferedImage img = ImageIO.read(is);
            charAnimates = new BufferedImage[9][6];
            for(int i=0;i<charAnimates.length;i++){
                for(int j=0;j<charAnimates[i].length;j++){
                    charAnimates[i][j] = img.getSubimage(j * 64,i * 40,64,40);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void render(Graphics g){
        g.drawImage(charAnimates[playerAction][aniInd],(int)x,(int)y,256,160,null);
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
