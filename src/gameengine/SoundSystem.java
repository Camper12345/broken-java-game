/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Camper2012
 */
public class SoundSystem extends Thread {
    public WaveFile wave;
    public byte[] data;
    AudioInputStream ais;
    public boolean playing = true;
    public boolean active = true;
    public FloatControl volumeControl = null;
    public int volume = 100;
    public SoundSystem(String sound, int vol){
        init(Main.window.resource.getSound(sound),vol);
        }
    public SoundSystem(String sound){
        init(Main.window.resource.getSound(sound),100);
        }
        public void init(WaveFile sound, int vol){
            wave = sound;
            setVolume(vol);
            start();
        }
    
       
    public void play(){
        try{
            Clip clip;
            if(Main.isNull(wave)){
                return;
            }
            ais = new AudioInputStream((InputStream)new ByteArrayInputStream(wave.sound.clone()),wave.format,wave.framesCount);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volume);
            clip.setFramePosition(0);
            clip.start();
            playing = true;
            Thread.sleep(getLength());
            //clip.stop();
            playing = false;
            //clip.close();
        }catch (LineUnavailableException | IOException | InterruptedException ex) {
            Main.error("Exception at SoundSystem: "+ex);}
        }
    
    @Override
    public void run(){
        play();
    }   
    public void stopSound(){
        //clip.stop();
        playing = false;
    }
    
    public void setVolume(int vol){
        volume = Math.min(100, Math.max(vol,0));
        if(!Main.isNull(volumeControl)){
        float min = volumeControl.getMinimum();
	float max = volumeControl.getMaximum();
	volumeControl.setValue((max-min)*((float)volume/100f)+min);
        }
    }
    public int getTickLength(){
        return (int)(((wave.framesCount / wave.format.getFrameRate())*1000)/Defines.TICKLAGDIV);
    }
    public int getLength(){
        return (int)(wave.framesCount / wave.format.getFrameRate()*1000);
    }
    public boolean isPlaying(){
        return playing;
    }
    @Override
    public void interrupt(){
        active = false;
    }
    
    public static SoundSystem playSound(String path){
        return new SoundSystem(path);
    }
    public static SoundSystem playSound(String path, int vol){
        return new SoundSystem(path,vol);
    }
}
