/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Camper2012
 */
public class MusicController extends Thread {
    ArrayList<String> playlist = new ArrayList();
    SoundSystem music;
    int playIndex = 0;
    private boolean active = true;
    public boolean tick = false;
    boolean loop = true;
    boolean playing = false;
    public MusicController(){
        //this.start();
    }
    @Override
    public void run(){
        Main.info("MusicController started!");
         process();   
    }
    public void process(){
        while(active){
        if(tick){
        if(playlist.isEmpty()){
                playing = false;
            }
            if(playing){
                if(Main.isNull(music) || !music.isPlaying()){
                    if(playIndex>playlist.size()-1){
                        if(loop){
                            playIndex = 0;
                            Main.info("MusicController's playlist is ended. Replaying...");}
                        else{
                            setStatus(false);
                            Main.info("MusicController's playlist is ended. Stopping.");
                        }
                    }else{
                        try{
                        music = new SoundSystem(playlist.get(playIndex),Defines.MUSIC_VOLUME);
                        Main.info("MusicController is now playing: "+playlist.get(playIndex)+" for "+music.getTickLength()+" ticks.");
                        }catch(NullPointerException ex)
                        {Main.error("Music file "+playlist.get(playIndex)+" not found."); music = null;}
                        playIndex += 1;
                    }
                }
                if(!Main.isNull(music)){
                    music.setVolume(Defines.MUSIC_VOLUME);
                }       
            }
        tick = false;  
        }
        try {sleep(1);} catch (InterruptedException ex) {Main.error("MusicController was interrupted!");}
        }
    }
    public void tick(){
        tick = true;
    }

    public void add(String sound){
        playlist.add(sound);
    }
    public void remove(String sound){
        playlist.remove(sound);
    }
    public void clear(){
        playlist.clear();
    }
    public boolean check(String sound){
        return playlist.contains(sound);
    }
    public String getPlaying(){
        return playlist.get(playIndex);
    }
    public void setPlaying(int index){
        playIndex = index;
    }
    public void setPlaying(String sound){
        if(playlist.contains(sound)){
            playIndex = playlist.indexOf(sound);
        }
    }
    public void stopPlaying(){
        music.stopSound();
    }
    public void setStatus(boolean st){
        playing = st;
    }
    public boolean getStatus(){
        return playing;
    }
    @Override
    public void interrupt(){
        active = false;
        Main.error("MusicController was interrupted!");
    }
}
