/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class LightSource extends DynamicObject {
    public int range = 0;
    public int brightness = 0;
    public float size = 180;
    public Color color = new Color(255,255,255);
    @Override
    public void initVars(){
        basicIconName = "";
        SETTING_COLOR = true;
        SETTING_RANGE = true;
        SETTING_STRENGTH = true;
        DefColor = Color.white;
        isMovable = false;
        saveable = false;
        ColorSettingName = "ЦВЕТ";
        RangeSettingName = "РАДИУС";
        StrengthSettingName = "ЯРКОСТЬ";
        StatusSettingName = "";
        RangeStep = 10;
        StrengthStep = 5;
        staticSize = true;
        width = 1;
        height = 1;
        DefRange = 250;
        DefStrength = 200;
    }
    
    @Override
    public void init(int nx, int ny){
        Main.window.lights.add(this);
    }
    
    @Override
    public void update(){
    }
       
    public void setColor(Color cl){
        color = cl;
    }
    
    public void setRange(int rng){
        range = rng;
    }
    
    public void setBrightness(int br){
        brightness = br;
    }
    
    @Override
    public void onRemove(){
        Main.window.lights.remove(this);
    }
                
    @Override
    public void setColorSetting(Color col){
        color = col;
        DefColor = col;
    }
    
    @Override
    public void setRangeSetting(int ran){
        range = ran;
        DefRange = ran;
    }
    
    @Override
    public void setStrengthSetting(int str){
        brightness = str;
        DefStrength = str;
    }
}
