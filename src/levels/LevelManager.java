package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static main.Game.TILES_SIZE;

public class LevelManager {
    Game game;
    private Level levelOne;
    BufferedImage[] levelSprite;
    public LevelManager(Game game){
        this.game = game;
//        levelSprite = LoadSave.GetSpritesAtlas(LoadSave.LEVEL_SPRITES);
        levelOne = new Level(LoadSave.GetLevelData());
        importOutsideSprite();
    }

    public void importOutsideSprite(){
        BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.LEVEL_SPRITES);
        levelSprite = new BufferedImage[48];
        for(int j=0;j<4;j++){
            for(int i=0;i<12;i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32,j*32,32,32);
            }
        }
    }
    public void draw(Graphics g){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++){
            for(int i=0;i<Game.TILES_IN_WIDTH;i++){
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],TILES_SIZE*i,TILES_SIZE*j,TILES_SIZE,TILES_SIZE,null);
            }
        }
    }
    public void update(){
    }
}
