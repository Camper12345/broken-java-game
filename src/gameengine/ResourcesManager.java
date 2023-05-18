/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Camper2012
 */
public class ResourcesManager{
    HashMap<String, WaveFile> sounds = new HashMap();
    HashMap<String, ImageFile> icons = new HashMap();
    boolean isReady = false;
    public ResourcesManager(){
        //start();
    }
    
    /*@Override
    public void run(){
        loadResources();
    }*/
    public void loadResources(){
        Main.info("------======Resource manager is loading icons======------");
        loadIcons();
        Main.info("------======Resource manager is loading sounds======------");
        loadSounds();
        Main.info("------======Resource manager loading finished!======------");
        isReady = true;
        gameengine.Frame.tick.start();
        
    }
    
    //SOUNDS
    
    public void loadSounds(){
        loadSound("music\\nk");
        loadSound("music\\gfn");
        loadSoundVariant("ambience\\boom",3);
        loadSoundVariant("object\\glassbreak",3);
        loadSound("object\\glasshit");
        loadSoundVariant("weapon\\meleehit",4);
        loadSoundVariant("weapon\\rat",1);
        loadSound("weapon\\empty");
        loadSound("weapon\\rifle_reload");
        loadSoundVariant("object\\doormove",1);
        loadSound("object\\doornoaccess");
        loadStepSounds();
        
        loadSound("weapon\\prisongun");
        loadSound("weapon\\basegun");
        loadSound("weapon\\pulser");
        loadSound("weapon\\turret");
        loadSound("weapon\\policegun");
    }
    
    public void loadSoundVariant(String p, int max){
        for(int i = 0; i<=max; i++){
            loadSound(p+i);
        }
    }
    
    public void loadStepSounds(){
        String p = "player\\step";
        loadSoundVariant(p+"\\tile", 3);
        loadSoundVariant(p+"\\concrete", 3);
        loadSoundVariant(p+"\\metal", 3);
        loadSoundVariant(p+"\\dirt", 3);
        loadSoundVariant(p+"\\mud", 3);
        loadSoundVariant(p+"\\metalgrate", 3);
    }
    
    public void loadSound(String path){
        String sound = Main.window.soundDir+path+Main.window.soundFormat;
        try{
        WaveFile sf = new WaveFile(sound);
        sounds.put(path,sf);
        Main.info(sound+" loaded.");
        }catch(IOException | UnsupportedAudioFileException ex){
            Main.critical("Cannot load "+sound+" due to "+ex);}
    }
    public WaveFile getSound(String path){
        WaveFile str = null;
        if(sounds.containsKey(path)){
            try{
            str = sounds.get(path);
            }catch(Exception ex){
                Main.error("Exception "+ex+" while getting "+path);
            }
        }
        else
        {
            //Main.error(Main.window.soundDir+path+Main.window.soundFormat+" not found.");
        }
        return str;
    }
    
    
    //ICONS
    
    public void loadIcons(){
        loadIcon("obj\\object");
        
        loadIcon("turf\\floor");
        loadIcon("turf\\wall");
        loadIconVariant("turf\\floor\\dirt\\dirt",2);
        loadIcon("turf\\floor\\dirt\\dirt_ladder");
        loadIconVariant("turf\\floor\\grass\\grass",2);
        loadIconVariant("turf\\floor\\concrete\\concrete",2);
        loadIcon("turf\\floor\\concrete\\concrete_ladder");
        loadIcon("turf\\wall\\concrete\\concrete");
        loadIcon("turf\\wall\\brick\\rbrick");
        loadIcon("turf\\wall\\brick\\wbrick");
        loadIcon("turf\\floor\\metal\\rusted_metal");
        loadIcon("turf\\floor\\metal\\metal");
        loadIcon("turf\\floor\\metal\\rusted_metal_ladder");
        loadIcon("turf\\floor\\metal\\metal_ladder");
        loadIconVariant("turf\\floor\\smoothstone\\smoothstone",2);
        loadIconVariant("turf\\wall\\stonewall\\stonewall",2);
        loadIcon("turf\\floor\\metal\\rusted_metal");
        loadIcon("turf\\wall\\metal\\metal");
        loadIcon("turf\\wall\\metal\\rusted_metal");
        loadIcon("turf\\floor\\metal\\catwalk");
        loadIcon("turf\\floor\\metal\\catwalk_ladder");
        loadIcon("turf\\floor\\smoothstone\\smoothstone_ladder");
        loadIcon("turf\\ladder");
        
        loadBreakable("obj\\light\\lamp");
        loadBreakable("obj\\light\\longlamp");
        loadIcon("obj\\light\\redlamp");
        loadIcon("obj\\light\\lantern");
        
        loadMob("mob\\player");
        loadMob("mob\\prisonguard");
        loadMob("mob\\hobo");
        loadMob("mob\\rat");
        loadMob("mob\\police");
        loadMob("mob\\baseguard");
        loadMob("mob\\turret");
        
        loadWeapon("obj\\weapon\\weapon");
        loadWeapon("obj\\weapon\\prison\\prison");
        loadWeapon("obj\\weapon\\basegun\\basegun");
        loadWeapon("obj\\weapon\\police\\police");
        loadMeleeWeapon("obj\\weapon\\fist\\fist");
        
        loadIcon("interface\\weaponalert");
        loadIcon("interface\\weapondrawrim");
        
        loadIcon("obj\\item\\l0key");
        loadIcon("obj\\item\\health");
        loadIcon("obj\\item\\battery");
        
        loadIcon("obj\\solid\\fence");
        loadIcon("obj\\solid\\hfence");
        loadIcon("obj\\solid\\window");
        loadIcon("obj\\solid\\rwindow");
        loadIcon("obj\\solid\\girder");
        loadIcon("obj\\solid\\barrier");
        
        loadAnim("obj\\buttons\\button");
        
        loadIcon("obj\\decoration\\pipe");
        loadIcon("obj\\decoration\\expl_pipe");
        loadIcon("obj\\decoration\\pipe_corner");
        loadIcon("obj\\decoration\\pipe_invcorner");
        loadAnim("obj\\decoration\\pipe_pump");
        loadIcon("obj\\decoration\\wooden_crate");
        loadIcon("obj\\decoration\\border");
        loadIcon("obj\\decoration\\engine");
        loadIcon("obj\\decoration\\bigengine");
        loadAnim("obj\\decoration\\big_fan");
        loadAnim("obj\\decoration\\fan");
        loadIcon("obj\\decoration\\table\\table");
        loadIcon("obj\\decoration\\table\\table_flipped");
        loadIcon("obj\\decoration\\chair");
        loadIcon("obj\\decoration\\bench");
        loadIcon("obj\\decoration\\policecar");
        loadIcon("obj\\decoration\\policecar_open");
        loadIcon("obj\\decoration\\policecar_crashed");

        loadIconVariant("obj\\decoration\\derbis\\glassshard",2);
        
        loadIcon("marker\\marker");
        loadIcon("marker\\light");
        loadIcon("marker\\invswall");
        loadIcon("marker\\mapchange");
        loadIcon("marker\\trigger");
        loadIcon("marker\\damage");
        loadIcon("marker\\exploder");
        loadIcon("marker\\navigation");
        loadIcon("marker\\pathnode");
        loadIcon("marker\\sound");
        loadIcon("marker\\levelmusic");
        loadIcon("marker\\start");
        loadIcon("marker\\aiblocker");
        loadIcon("marker\\gbchanger");
        loadIcon("marker\\mover");
        loadIcon("marker\\allowedzone");
        loadIcon("marker\\advice");
        loadIcon("marker\\tele");
        loadIcon("marker\\teleout");
        
        loadIcon("obj\\projectile\\laser");
        loadIcon("obj\\projectile\\smalllaser");
        loadIcon("obj\\projectile\\heavylaser");
        
        loadAnim("obj\\doors\\hslidingdoor");
        loadAnim("obj\\doors\\rhslidingdoor");
        loadAnim("obj\\doors\\slidingdoor");
        loadAnim("obj\\doors\\policedoor");
        loadAnim("obj\\doors\\rmainternancedoor");
        
        loadButton("buttons\\button");

        loadAnim("effects\\explosion");
        loadAnim("lowerzanim");
    }
    
    public void loadButton(String path){
        loadAnim(path+"\\_c");
        loadAnim(path+"\\_r");
        loadAnim(path+"\\_l");
    }
    
    public void loadMob(String p){
        loadIcon(p+"\\body");
        loadIcon(p+"\\body_crawl");
        loadIcon(p+"\\dead");
        loadIcon(p+"\\hands");
        loadIcon(p+"\\hands_wh");
        loadIcon(p+"\\hands_w2h");
        loadIcon(p+"\\hands_2wh");
        loadIcon(p+"\\hands_mw");
        
        loadAnim(p+"\\legs");
        loadAnim(p+"\\legs_crawl");
        loadAnim(p+"\\legs_sprint");
    }
    
    public void loadBreakable(String p){
        loadIcon(p);
        loadIcon(p+"_broken");
    }
    
    public void loadWeapon(String p){
        loadIcon(p+"_draw");
        loadIcon(p+"_hold");
        loadIcon(p+"_lying");
    }
    
    public void loadMeleeWeapon(String p){
        loadIcon(p+"_draw");
        loadIcon(p+"_hold");
        loadIcon(p+"_hit");
        loadIcon(p+"_lying");
    }
    
    public void loadIconVariant(String p, int max){
        for(int i = 0; i<=max; i++){
            loadIcon(p+i);
        }
    }
    
    public void loadIcon(String path){
        String image = Main.window.iconDir+path+Main.window.iconFormat;
        try {
            BufferedImage img = ImageIO.read(new File(image));
            ImageFile ifl = new ImageFile(img);
            icons.put(path, ifl);
            Main.info(image+" loaded.");
        } catch (IOException ex) {
            Main.critical("Cannot load "+image+" due to "+ex);
        }
    }
    public void loadAnim(String path){
        String fpath = Main.window.iconDir+path;
        ImageFile ifl = new ImageFile();
        try {
            File dfl = new File(fpath);
            File gif = dfl;
            if(!dfl.exists()){
                throw new IOException("File not found");
            }
            
            if(gif.isDirectory()){
                String[] imgs = gif.list();
                if(Main.isNull(imgs) || imgs.length<=0){
                    throw new IOException("No files detected in directory");
                }
                for(String fn : imgs){
                    if(fn.endsWith(".png")){
                        //System.out.println(fpath+"\\"+fn);
                        ifl.add((Image)ImageIO.read(new File(fpath+"\\"+fn)));
                    }
                }
                icons.put(path,ifl);
                Main.info(fpath+" loaded as animation directory.");
            }
        } catch (IOException ex) {
            Main.critical("Cannot load "+fpath+" due to "+ex);
        }
    }
    
    public ImageFile getIcon(String path){
        ImageFile img = null;
        if(icons.containsKey(path)){
            img = icons.get(path);
        }
        else
        {
            img = new ImageFile();
        }
        return img;
    }
}
