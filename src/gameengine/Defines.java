 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.awt.Color;
import java.awt.image.AffineTransformOp;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Camper2012
 */
public class Defines extends Application {
    //Global defines
    public static final int DEF_WIDTH = 700;
    public static final int DEF_HEIGHT = 430;
    public static int WIDTH = 700;
    public static int HEIGHT = 430;
    public static int SCREEN_WIDTH = 600;
    public static int SCREEN_HEIGHT = 450;
    public static int SCREEN_WIDTH_F = 800;
    public static int SCREEN_HEIGHT_F = 600;
    public static int X_OFFSET = 0;
    public static int Y_OFFSET = 0;
    public static int SCR_X = 0;
    public static int SCR_Y = 0;
    public static float RES_MULT = 1;
    public static int TICKLAGDIV = 10;
    public static int TICKLAG = 1000/TICKLAGDIV;
    
    
    ///////FLAGS///////
    public static final int FLAG_NULL = 0;
    //Object flags
    public static final int OBJECT_IS_CONTAINER = 1;
    public static final int PLAYER_CONTROLS_OVERRIDE = 2;
    public static final int MOB_NOFALL = 4;
    //Pass flags
    public static final int PASS_PROJECTILE_COLLIDE = 1;
    public static final int PASS_PROJECTILE_PASS = 2;
    public static final int PASS_AI_BLOCK = 4;
        
    //Layers
    public static float LAYER_TURF = 1;
    public static float LAYER_WALL = 2.5f;
    public static float LAYER_MOB = 2.3f;
    public static float LAYER_LIGHT = 2.8f;
    public static float LAYER_ITEM = 2.1f;
    public static float LAYER_ITEM_UP = 2.7f;
    public static float LAYER_OBJECT = 2;
    public static float LAYER_PROJECTILE = 2.7f;
    public static float LAYER_BUTTON = 5f;
    public static float LAYER_TEXT = 6f;
    public static final float LAYER_MAX = 10;
    //////////////////////
    //SETTINGS
    public static boolean LIGHT_ENABLED = true;
    public static boolean SIGHT_ENABLED = true;
    public static boolean COLLIDE_ENABLED = true;
    
    public static int ICON_TRANSFORM_TYPE = AffineTransformOp.TYPE_BILINEAR;
    public static Color GRAPHICS_BACKGROUND_COLOR = new Color(5,5,5);
    public static Color LOADING_GREEN = new Color(64,255,64);
    public static Color LOADING_RED = new Color(255,0,0);
    public static Color LOADING_YELLOW = new Color(255,255,0);
    public static int Z_SIGHT_DIST = 2;
    public static int Z_MAX = 100;
    public static int Z_MIN = -100;
    public static int ICON_SIZE = 32;
    public static int CHUNK_SIZE = 8*ICON_SIZE;
    public static boolean SHOW_COLLIDERS = false;
    
    public static int MUSIC_VOLUME = 80;
    public static int SOUND_VOLUME = 100;
    public static int MAX_SOUND_DIST = 1000;
    //MOBS
    
    public static int ALERT_LEVEL_MAX = 1000;    
    public static int MELEE_ATTACK_DIST = 20;    
    
    //Light system
    public static int LIGHT_MAX_DIST = 1024;
    public static Color LIGHT_DARK_COLOR = new Color(0,0,3,253);
    //////////////////////
    
    
    public Defines getInstance(){
        return this;
    }
    
    public static int getSoundVolume(int dist){
        int rt = 0;
        if(Main.in_range(dist, 0, MAX_SOUND_DIST)){
            rt = (int) (((float)(MAX_SOUND_DIST-dist)/(float)MAX_SOUND_DIST)*100)/2+50-(100-SOUND_VOLUME);
        }
        return rt;        
    }
    
    public static boolean checkFlag(int flags, int flag){
        boolean fl = false;
        if((flags & flag)>0){
            fl = true;
        }
        return fl;
    }
    
    public static void setTicklagDiv(int tick){    
        TICKLAGDIV = Math.max(1, tick);
        TICKLAG = (int)(1000/TICKLAGDIV);
    }
    public static void setTicklag(int tick){
        TICKLAG = Math.max(1, tick);
        TICKLAGDIV = (int)(1000/TICKLAG);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    }
}
