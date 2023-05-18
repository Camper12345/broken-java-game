/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Defines;
import gameengine.DynamicObject;
import gameengine.Main;

/**
 *
 * @author Camper2012
 */
public class GameButton extends DynamicObject{
    public int id;
    public Trigger trigger = new Trigger();
    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\buttons\\button";
        cycleAnim = false;
        SETTING_RANGE = true;
        RangeSettingName = "ID";
        RangeStep = 1;
        layer = Defines.LAYER_WALL+0.1f;
    }
    
    @Override
    public void init(int nx, int ny){
        trigger.initVars();
        Main.window.nodes.add(this);
    }

    @Override
    public void onRemove(){
        super.onRemove();
        Main.window.nodes.remove(this);
    }
    
    @Override
    public void setRangeSetting(int ran) {
        id = ran;
        DefRange = ran;
    }

    @Override
    public void onTimer(int id) {
        if(id == 0){
            animIndex = 0;
        }
    }
    
    
    
    @Override
    public void onPlayerUse(){
        trigger.id = id;
        if(playerCanPress()){
            onActivate();
            animIndex = 1;
            setTimer(0,10);
        }else{
            playSound("object\\doornoaccess",getSoundVolume()-10);
        }
    }
    
    public boolean playerCanPress(){
        return true;
    }
    
    public void onActivate(){
        trigger.onActivate(Main.window.master.playerObj);
    }
    
}
