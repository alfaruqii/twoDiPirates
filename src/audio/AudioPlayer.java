package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {
    public static int PIRATES = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;
    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETED = 3;
    public static int ATTACK_ONE = 4;
    public static int ATTACK_TWO = 5;
    public static int ATTACK_THREE = 6;
    private Clip[] songs,effects;
    private int currentSongId;
    private float volume = 0.5f;
    private boolean songMute,effectMute;
    private Random rand = new Random();
    public AudioPlayer(){
        loadSongs();
        loadEffects();
        playSong(PIRATES);
    }
    public void loadSongs(){
        String[] names = {"pirates","level1","level2"};
        songs = new Clip[names.length];
        for (int i=0;i<names.length;i++){
            songs[i] = getClip(names[i]);
        }
    }
    public void loadEffects(){
        String[] effectNames = {"die","jump","gameover","lvlcompleted","attack1","attack2","attack3"};
        effects = new Clip[effectNames.length];
        for (int i=0;i<effectNames.length;i++){
            effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }
    private Clip getClip(String name){
        URL url = getClass().getResource("/res/audio/"+name+".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            AudioFormat format = audio.getFormat();
            DataLine.Info lineInfo = new DataLine.Info(Clip.class, format);

            Mixer.Info selectedMixer = null;

            for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                if (mixer.isLineSupported(lineInfo)) {
                    selectedMixer = mixerInfo;
                    break;
                }
            }

            if (selectedMixer != null) {
                Clip c = AudioSystem.getClip(selectedMixer);
                c.open(audio);
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setVolume(float volume){
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }
    public void stopSong(){
        if(songs[currentSongId].isActive()){
            songs[currentSongId].stop();
        }
    }
    public void setLevelSong(int lvlIndex){
        if(lvlIndex % 2 == 0){
            playSong(LEVEL_1);
        }else
            playSong(LEVEL_2);
    }
    public void lvlCompleted(){
        stopSong();
        playEffect(LVL_COMPLETED);
    }
    public void playAttackSond(){
        int start = 4;
        start += rand.nextInt(3);
        playEffect(start);
    }
    public void playEffect(int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }
    public void playSong(int song){
        stopSong();
        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void toggleSongMute(){
        this.songMute = !songMute;
        for(Clip c : songs){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }
    public void toggleEffectMute(){
        this.effectMute = !effectMute;
        for(Clip c : effects){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if(!effectMute)
            playEffect(JUMP);
    }
    private void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range*volume)+gainControl.getMinimum();
        gainControl.setValue(gain);
    }
    private void updateEffectsVolume(){
        for (Clip c : effects){
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range*volume)+gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
