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
public class Ladder extends Turf{
    @Override
    public void initVars(){
        super.initVars();
        can_rotated = true;
        basicIconName = "turf\\ladder";
    }
    
    @Override
    public void onCreate(){
        updateZLadders();
    }
    @Override
    public void onRemove(){
        updateZLadders();
    }
    
    @Override
    public void update(){
        boolean isUp = Main.window.isTypeOnPos(x, y, z+1, this);
        boolean isDown = Main.window.isTypeOnPos(x, y, z-1, this);
        if(isUp && !isDown){
            navNode.zMove = 1;
        }
        if(!isUp && isDown){
            navNode.zMove = -1;
        }
        if((isUp && isDown)||(!isUp && !isDown)){
            navNode.zMove = 0;
        }
    }
    
    void updateZLadders(){
        for(Chunk ch : Main.window.map){
            for(GameObject obj : ch.contents){
                if(obj instanceof Ladder){
                    Ladder ld = (Ladder) obj;
                    ld.update();
                }
            }
        }
    }

}
