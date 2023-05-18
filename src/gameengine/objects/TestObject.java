/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import static java.lang.Math.random;

/**
 *
 * @author Camper2012
 */
public class TestObject extends DynamicObject {
    
    @Override
    public void initVars(){
        basicIconName = "anim\\anim_test";
        isDensity = true;
        mass = 7;
    }
        
    @Override
    public void onUpdate(){
        if(Main.prob(33)){
        int nx = (int)(8-(random()*16));
        int ny = (int)(8-(random()*16));
        //move(x+nx,y+ny);
        }
        if(Main.prob(1)){
        setAngle((int) (direction+(Math.random()*180)-90));}
    }
}
