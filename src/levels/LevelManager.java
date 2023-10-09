package levels;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_SIZE;

public class LevelManager {
    Game game;
    private Level levelOne;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    public LevelManager(Game game){
        this.game = game;
//        levelSprite = LoadSave.GetSpritesAtlas(LoadSave.LEVEL_SPRITES);
        importOutsideSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    public void loadNextLevel(){
        lvlIndex++;
        if(lvlIndex >= levels.size()){
            lvlIndex = 0;
            System.out.println("no more levels, game completed!");
            Gamestates.state = Gamestates.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img : allLevels){
            levels.add(new Level(img));
        }
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
    public void draw(Graphics g,int xLvlOffset){
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++){
            for(int i=0;i<levels.get(lvlIndex).getLevelData()[0].length;i++){
                int index = levels.get(lvlIndex).getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],TILES_SIZE*i-xLvlOffset,TILES_SIZE*j,TILES_SIZE,TILES_SIZE,null);
            }
        }
    }
    public void update(){
    }
    public Level getCurrenLevel(){
        return levels.get(lvlIndex);
    }
    public int getAmountOfLevels(){
        return levels.size();
    }
}
