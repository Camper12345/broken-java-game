/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;

/**
 *
 * @author Camper2012
 */
public class Marker extends DynamicObject{
    public int id = -1;
    @Override
    public void initVars(){
        super.initVars();
        isInvs = true;
        can_lighting = false;
        collideType = 0;
        staticSize = true;
        SETTING_RANGE = true;
        DefRange = -1;
        RangeSettingName = "ID";
        RangeStep = 1;
        basicIconName = "marker\\marker";
    }
    
    public void setRangeSetting(int nid){
        id = nid;
        DefRange = nid;
    }
    
    @Override
    public void init(int nx,int ny){
        super.init(nx,ny);
        Main.window.nodes.add(this);
    } 
    
    @Override
    public void remove(){
        super.remove();
        Main.window.nodes.remove(this);
    }
}
