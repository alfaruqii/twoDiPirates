package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game = game;
    }
    public boolean isIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }

    public Game getGame() {
        return game;
    }
    public void setGameState(Gamestates state){
        switch (state){
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.PIRATES);
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
        }
        Gamestates.state = state;
    }
}
