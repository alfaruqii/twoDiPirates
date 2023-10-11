package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstant.*;
import static utilz.Constants.Projecttiles.CANNON_BALL_HEIGHT;
import static utilz.Constants.Projecttiles.CANNON_BALL_WIDTH;
import static utilz.HelpMethods.CanCannonSeePlayer;
import static utilz.HelpMethods.IsProjectileHittingLevel;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage spikeImage,cannonBallImg;
    private BufferedImage[] cannonImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        cannons = newLevel.getCannons();
        projectiles.clear();
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
        cannonImgs = new BufferedImage[7];
        BufferedImage temp = LoadSave.GetSpritesAtlas(LoadSave.CANNON_ATLAS);
        for(int i=0;i<cannonImgs.length;i++)
            cannonImgs[i] = temp.getSubimage(i*40,0,40,26);
        cannonBallImg = LoadSave.GetSpritesAtlas(LoadSave.CANNON_BALL);
    }
    public void update(int[][] lvlData, Player player){
        for(Potion p : potions)
            if(p.isActive())
                p.update();
        for (GameContainer gc : containers)
            if(gc.isActive())
                gc.update();
        updateCannons(lvlData,player);
        updateProjectiles(lvlData,player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles){
            if(p.isActive())
                p.updatePos();
            if(p.getHitbox().intersects(player.getHitbox())){
                player.changeHealth(-25);
                p.setActive(false);
            }else if(IsProjectileHittingLevel(p,lvlData)){
                p.setActive(false);
            }
        }
    }

    private void updateCannons(int[][] lvlData, Player player) {
        for(Cannon c :cannons){
            if(!c.doAnimation){
                if(c.getTileY() == player.getTileY()){
                    if(isPlayerInRange(c,player))
                        if(isPlayerInfrontOfCannon(c,player))
                            if(CanCannonSeePlayer(lvlData,player.getHitbox(),c.getHitbox(),c.getTileY())){
                                // shoot (tembak)
                                c.setAnimation(true);
                            }
                }
            }
            c.update();
            if(c.getAniIndex() == 4 && c.getAniTick() == 0){
                shootCannon(c);
            }
        }
    }
    private boolean isPlayerInRange(Cannon c, Player player) {
        int absVal = (int)Math.abs(player.getHitbox().x-c.getHitbox().x);
        return absVal <= Game.TILES_SIZE * 5;
    }
    private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
        if(c.getObjType() == CANNON_LEFT){
            if(c.getHitbox().x > player.getHitbox().x)
                return true;
        } else if(c.getHitbox().x < player.getHitbox().x)
                return true;
        return false;
    }

    private void shootCannon(Cannon c) {
        int dir = 1;
        if(c.getObjType() == CANNON_LEFT)
            dir = -1;
        projectiles.add(new Projectile((int)c.getHitbox().x,(int)c.getHitbox().y,dir));
    }
    public void draw(Graphics g, int xLvlOffset){
        drawContainers(g,xLvlOffset);
        drawPotions(g,xLvlOffset);
        drawTraps(g,xLvlOffset);
        drawCannons(g,xLvlOffset);
        drawProjecttiles(g,xLvlOffset);
    }

    private void drawProjecttiles(Graphics g, int xLvlOffset) {
        for(Projectile p : projectiles){
            if(p.isActive())
                g.drawImage(cannonBallImg,(int)(p.getHitbox().x-xLvlOffset),(int)p.getHitbox().y,CANNON_BALL_WIDTH,CANNON_BALL_HEIGHT,null);
        }
    }

    private void drawCannons(Graphics g, int xLvlOffset) {
        for(Cannon c : cannons){
            int x = (int)(c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;
            if(c.getObjType() == CANNON_RIGHT){
                x += width;
                width *= -1;
            }
            g.drawImage(cannonImgs[c.getAniIndex()],x,(int)(c.getHitbox().y),width,CANNON_HEIGHT,null);
        }
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
        for(Cannon c : cannons)
            c.reset();
    }
}
