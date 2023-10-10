package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstant.*;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage spikeImage;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }
    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for(Potion p : potions){
            if(p.isActive())
                if(hitbox.intersects(p.getHitbox())){
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
        }
    }
    public void checkSpikesTouched(Player p){
        for(Spike s : spikes){
            if(s.getHitbox().intersects(p.getHitbox()))
                p.kill();
        }
    }
    public void applyEffectToPlayer(Potion p){
        if(p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }
    public void checkObjectHit(Rectangle2D.Float attackBox){
        for (GameContainer gc : containers)
            if(gc.isActive() && !gc.isDoAnimation())
                if(gc.getHitbox().intersects(attackBox)){
                    gc.setAnimation(true);
                    int type = 0;
                    if(gc.getObjType() == BARREL)
                        type = 1;
                    potions.add(new Potion((int)(gc.getHitbox().x + gc.getHitbox().width/2),(int)(gc.getHitbox().y - gc.getHitbox().height/2),type));
                    return;
                }
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpritesAtlas(LoadSave.POTIONS_SPRITES);
        potionImgs = new BufferedImage[2][7];
        for(int j=0;j<potionImgs.length;j++){
            for (int i=0;i<potionImgs[j].length;i++){
                potionImgs[j][i] = potionSprite.getSubimage(12*i,16*j,12,16);
            }
        }
        BufferedImage containerSprite = LoadSave.GetSpritesAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];
        for(int j=0;j<containerImgs.length;j++){
            for (int i=0;i<containerImgs[j].length;i++){
                containerImgs[j][i] = containerSprite.getSubimage(40*i,30*j,40,30);
            }
        }
        spikeImage = LoadSave.GetSpritesAtlas(LoadSave.TRAP_ATLAS);
    }
    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
    }
    public void update(){
        for(Potion p : potions)
            if(p.isActive())
                p.update();
        for (GameContainer gc : containers)
            if(gc.isActive())
                gc.update();
    }
    public void draw(Graphics g, int xLvlOffset){
        drawContainers(g,xLvlOffset);
        drawPotions(g,xLvlOffset);
        drawTraps(g,xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for(Spike s : spikes)
            g.drawImage(spikeImage,(int)(s.getHitbox().x-xLvlOffset),(int)(s.getHitbox().y-s.getyDrawOffset()),SPIKE_WIDTH,SPIKE_HEIGHT,null);
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if(p.isActive()) {
                int type = 0;
                if (p.getObjType() == RED_POTION) {
                    type = 1;
                }
                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int)(p.getHitbox().x - p.getxDrawOffset() -xLvlOffset),
                        (int)(p.getHitbox().y - p.getyDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
            }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers)
            if(gc.isActive()) {
                int type = 0;
                if (gc.getObjType() == BARREL) {
                    type = 1;
                }
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int)(gc.getHitbox().x - gc.getxDrawOffset() -xLvlOffset),
                        (int)(gc.getHitbox().y - gc.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrenLevel());
        for (Potion p : potions)
            p.reset();
        for (GameContainer gc: containers)
            gc.reset();
    }
}
