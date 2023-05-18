/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.*;
import gameengine.objects.gui.MainMenuGUI;
import gameengine.objects.mapeditor.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;



/**
 *
 * @author Camper2012
 */
public class GameController extends Thread{
    private boolean init = true;
    private boolean active = true;
    public boolean tick = false;
    public File loadmap;
    public boolean loadPlayer = false;
    public File currentMap;
    public boolean levelRestart = false;
    int restartcount = 0;
    public boolean isInGame = false;
    public GameController(){
        //this.start();
    }
    
    @Override
    public void run(){
        Main.info("GameController started!");
        process();
    }
    int lradd = 0;
    public void process(){
        while(active){
            if(tick){
            if(!Main.isNull(Main.window.master) ){ 
                if(init){
                goToMainMenu();
                createStaticObjects();               
                init = false;
                }
                }
            if(!Main.isNull(loadmap)){
                restartcount = 0;
                levelRestart = false;
                if(loadmap.exists()){
                Main.window.loadMap(loadmap);
                if(loadPlayer){
                Main.window.loadPlayer();
                loadPlayer = false;
                }        
                }
                loadmap = null;
            }
            
            if(levelRestart){
                if(restartcount<=254){
                    if(lradd>=15){
                        lradd = 0;
                        restartcount += 1;
                    }else{
                        lradd++;
                    }
                }else{
                    loadMap(currentMap, true);
                }
            }
            
        }
        try {sleep(1);} catch (InterruptedException ex) {Main.error("GameController was interrupted!");}
        }
    }
    
    public void createStaticObjects(){
        createObject(new ZoneOfSight(),0,0,0);
        //test
        //createObject(new MapEditorGUI(),0,0,0);
    }
    
    public void goToMainMenu(){
        Main.window.resetMap();
        currentMap = new File("");
        isInGame = false;
        createObject(new MainMenuGUI(), 0, 0, 0);
    }
    
    public void startNewGame(){
        loadMap("level0");
        Main.window.savePlayer();
    }
    
    public void loadGame(){
        String map = Main.window.getGameMap();
        loadMap(map, true);
    }
    
    public void loadMap(File mfile){
        loadMap(mfile, false);
    }
    
    public void loadMap(File mfile, boolean loadplayer){
        loadmap = mfile;
        currentMap = mfile;
        loadPlayer = loadplayer;
        isInGame = true;
    }
    
    public void loadMap(String mpath,boolean loadplayer){
        File mfile = new File(Main.window.mapDir+mpath+Main.window.mapFormat);
        loadmap = mfile;
        currentMap = mfile;
        loadPlayer = loadplayer;
        isInGame = true;
    }
    
    public void loadMap(String mpath){
        loadMap(mpath, false);
    }
    
    public void tick(){
        tick = true;
    }  
    
    public GameObject createObject(GameObject obj, int x, int y, int z){
        return Main.window.createObject(obj,x,y,z);
    }
    public GameObject createObject(GameObject obj,int nx, int ny, int nz, boolean init){
        return Main.window.createObject(obj,nx,ny,nz,init);
    }
    
    @Override
    public void interrupt(){
        active = false;
        Main.error("GameController was interrupted!");
    }
}
