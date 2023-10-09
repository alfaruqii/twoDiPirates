package objects;

import main.Game;

import static utilz.Constants.ObjectConstant.BARREL;
import static utilz.Constants.ObjectConstant.BOX;

public class GameContainer extends GameObject{
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
    private void createHitbox() {
        if(objType == BOX){
            initHitbox(25,18);
            xDrawOffset = (int)( 7 * Game.SCALE);
            yDrawOffset = (int)( 12 * Game.SCALE);
        }
        if (objType == BARREL){
            initHitbox(23,25);
            xDrawOffset = (int)(8 * Game.SCALE);
            yDrawOffset = (int)(5 * Game.SCALE);
        }
    }
}