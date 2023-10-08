package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Crabby extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffset;
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x,y,(int)(22*Game.SCALE),(int)(19*Game.SCALE));
        initAttackBox();
    }
    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x,y,(int)(82*Game.SCALE),(int)(19*Game.SCALE));
        attackBoxOffset = (int)(Game.SCALE * 30);
    }
    public void update(int[][] lvlData,Player player){
        updateBehavior(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
    }
    private void updateAttackBox(){
        attackBox.x = hitbox.x - attackBoxOffset;
        attackBox.y = hitbox.y;
    }
    public void updateBehavior(int[][] lvlData,Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }
        if(inAir){
            updateInAir(lvlData);
        } else {
            switch (enemyState){
                case(IDLE):
                    newState(RUNNING);
                    break;
                case (RUNNING):
                    if(canSeePlayer(lvlData,player)){
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case (ATTACK):
                    if(aniIndex == 0)
                        attackChecked = false;
                    if(aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox,player);
                        break;
                case (HIT):
                    break;
            }
        }
    }
    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
}
