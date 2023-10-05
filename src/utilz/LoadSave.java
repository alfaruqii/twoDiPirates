package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static main.Game.*;

public class LoadSave {
    public static final String PLAYER_SPRITES = "res/player_sprites.png";
    public static final String LEVEL_SPRITES = "res/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "res/level_one_data_long.png";
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
    public static int[][] GetLevelData(){
        BufferedImage img = GetSpritesAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for(int j=0;j<img.getHeight();j++){
            for(int i=0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value >= 48){
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
