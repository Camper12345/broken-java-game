/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.DynamicObject;
import gameengine.GameObject;
import gameengine.Main;

/**
 *
 * @author Camper2012
 */
public class ExplodeTrigger extends Trigger{

    @Override
    public void onActivate(GameObject obj) {
        for(DynamicObject mrk : Main.window.nodes){
            if(mrk instanceof Exploder){
                Exploder ex = (Exploder) mrk;
                if(ex.id == id){
                trigger(ex);
                }
            }
        }
    }
    
    public void trigger(Exploder ex){
        ex.explode();
    }
    
}
