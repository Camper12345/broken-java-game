/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.Explosion;
import gameengine.objects.Ladder;
import gameengine.objects.NavNode;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 *
 * @author Camper2012
 */
public class Turf extends DynamicObject{
    public float mmove_dir = 0;
    public float mmove_speed = 0;
    
    public boolean isNavNode = false;
    public NavNode navNode = null;
    public String stepSound = "player\\step\\tile";
    @Override
    public void initVars(){
        super.initVars();
        collideType = 0;
        isMovable = false;
        can_rotated = false;
        isNavNode = true;
        layer = Defines.LAYER_TURF;
        basicIconName = "turf\\floor";
    }
    
    @Override
    public void init(int nx,int ny){
        if(!isDensity && isNavNode){
            navNode = (NavNode) createObject(new NavNode(), nx, ny, z);
        }
    }
    
    @Override
    public void remove(){
        super.remove();
        if(!isDensity && isNavNode && !Main.isNull(navNode)){
        navNode.remove();
        }
    }
       
    @Override
    public void onMiddleClick(){
        //explode();
    }
    
    @Override
    public void process(){
        super.process();
        mmove_speed = 0;
    }
    
    @Override
    public void bump(){
    }
    
}
