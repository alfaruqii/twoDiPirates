package levels;

import entities.Crabby;
import main.Game;
import objects.*;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private ArrayList<Crabby> crabs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    int[][] lvlData;
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn(img);
        createPotions();
        createContainers();
        createSpikes();
        createCannons();
    }

    private void createCannons() {
        cannons = HelpMethods.GetCannon(img);
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotion(img);
    }

    private void createContainers() {
        containers = HelpMethods.GetContainer(img);
    }

    private void calcPlayerSpawn(BufferedImage img) {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x,int y){
        return lvlData[y][x];
    }
    public int[][] getLevelData(){
        return lvlData;
    }
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<Crabby> getCrabs(){
        return crabs;
    }
    public Point getPlayerSpawn(){
        return playerSpawn;
    }
    public ArrayList<Potion> getPotions(){
        return potions;
    }
    public ArrayList<GameContainer> getContainers(){
        return containers;
    }
    public ArrayList<Spike> getSpikes(){
        return spikes;
    }
    public ArrayList<Cannon> getCannons(){
        return cannons;
    }
}
