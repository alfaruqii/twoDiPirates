package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Crabby extends Enemy {
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x,y,(int)(22* Game.SCALE),(int)(19*Game.SCALE));
    }
}
