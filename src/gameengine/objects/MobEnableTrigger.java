/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import gameengine.Main;

/**
 *
 * @author Camper2012
 */
public class MobEnableTrigger extends Trigger{
    @Override
    public void onActivate(GameObject obj){
        for(Mob m : Main.window.mobs){
            if(m.id == id){
                m.thinking = true;
            }
        }
    }
}
