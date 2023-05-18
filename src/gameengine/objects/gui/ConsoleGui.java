/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.*;
import gameengine.objects.*;
import gameengine.objects.mapeditor.MapEditorGUI;

/**
 *
 * @author Camper2012
 */
public class ConsoleGui extends GUI{
    NumSelector ticklag;
    ToggleButton coll;
    ToggleButton light;
    ToggleButton sight;
    NumSelector zsight;
    ToggleButton seeinvs;
    Button mapeditor;
    Button clsbtn;
    @Override
    public void init(int nx, int ny){
        Main.window.master.console = this;
        
        clsbtn = (Button) addElement(new Button(),Defines.WIDTH-16-40,16);
        clsbtn.setTxt("X");
        
        ticklag = (NumSelector) addElement(new NumSelector(), 8, 8);
        ticklag.max = 50;
        ticklag.min = 5;
        ticklag.value = Defines.TICKLAGDIV;
        ticklag.incstep = 5;
        ticklag.decstep = 5;
        ticklag.prefix = "ТИКЛАГ [";
        
        coll = (ToggleButton) addElement(new ToggleButton(), 8*2, 8*2+40);
        coll.setTxt("СТОЛКНОВЕНИЯ");
        coll.pressed = Defines.COLLIDE_ENABLED;
        
        light = (ToggleButton) addElement(new ToggleButton(), 8*2, 8*2+40*2);
        light.setTxt("ОСВЕЩЕНИЕ");
        light.pressed = Defines.LIGHT_ENABLED;
        
        sight = (ToggleButton) addElement(new ToggleButton(), 8*2, 8*2+40*3);
        sight.setTxt("ЗОНА ВИДИМОСТИ");
        sight.pressed = Defines.SIGHT_ENABLED;
        
        zsight = (NumSelector) addElement(new NumSelector(), 8*2, 8*2+40*4);
        zsight.max = 10;
        zsight.min = 0;
        zsight.clickable = false;
        zsight.value = Defines.Z_SIGHT_DIST;
        zsight.incstep = 1;
        zsight.decstep = 1;
        zsight.prefix = "Z-ВИДИМОСТЬ [";
        
        seeinvs = (ToggleButton) addElement(new ToggleButton(), 8, 8*3+40*5);
        seeinvs.setTxt("ОТЛАДКА");
        seeinvs.pressed = Main.window.master.drawInvs;
        
        mapeditor = (Button) addElement(new Button(), 8, Defines.HEIGHT-8-32);
        mapeditor.setTxt("РЕДАКТОР КАРТЫ");
    }
    
    @Override
    public void onUpdate(){
        if(ticklag.clicked){
            Defines.setTicklagDiv(ticklag.value);
        }
        
        Defines.COLLIDE_ENABLED = coll.pressed;
        Defines.LIGHT_ENABLED = light.pressed;
        Defines.SIGHT_ENABLED = sight.pressed;
        Defines.Z_SIGHT_DIST = zsight.value;
        Main.window.master.drawInvs = seeinvs.pressed;

        if(mapeditor.clicked){
            createObject(new MapEditorGUI(), 0, 0, 0);
            remove();
        }
        
        if(clsbtn.clicked){
            remove();
        }
        
        updateShowing(show);
    }
    
    @Override
    public void onRemove(){
        super.onRemove();
        Main.window.master.console = null;
    }
}
