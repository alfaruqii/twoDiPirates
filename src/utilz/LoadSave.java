package utilz;

import entities.Crabby;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.CRABBY;


public class LoadSave {
    public static final String PLAYER_SPRITES = "res/player_sprites.png";
    public static final String LEVEL_SPRITES = "res/outside_sprites.png";
    public static final String BUTTON_ATLAS = "res/button_atlas.png";
    public static final String MENU_BACKGROUND = "res/menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "res/menu_background_img.png";
    public static final String PLAYING_BG_IMG = "res/playing_bg_img.png";
    public static final String PAUSE_MENU = "res/pause_menu.png";
    public static final String SOUNDS_BUTTON = "res/sound_button.png";
    public static final String URM_BUTTON = "res/urm_buttons.png";
    public static final String VOLUME_BUTTON = "res/volume_buttons.png";
    public static final String BIG_CLOUDS = "res/big_clouds.png";
    public static final String SMALL_CLOUDS = "res/small_clouds.png";
    public static final String MOON = "res/moon.png";
    public static final String CRABBY_SPRITE = "res/crabby_sprite.png";
    public static final String HEALTH_POWER_BAR = "res/health_power_bar.png";
    public static final String COMPLETED_IMG = "res/completed_sprite.png";
    public static final String CONTAINER_ATLAS = "res/objects_sprites.png";
    public static final String POTIONS_SPRITES = "res/potions_sprites.png";
    public static final String TRAP_ATLAS = "res/trap_atlas.png";
    public static final String CANNON_ATLAS = "res/cannon_atlas.png";
    public static final String CANNON_BALL = "res/ball.png";
    public static final String DEATH_SCREEN = "res/death_screen.png";
    public static BufferedImage GetSpritesAtlas(String path){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/"+path);
        try {
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }
    public static BufferedImage[] GetAllLevels(){
        URL url = LoadSave.class.getResource("/res/lvls");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];
        for(int i=0;i<filesSorted.length;i++){
            for(int j=0;j<files.length;j++){
                if(files[j].getName().equals((i+1)+".png")){
                    filesSorted[i] = files[j];
                }
            }
        }
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++){
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }
}
