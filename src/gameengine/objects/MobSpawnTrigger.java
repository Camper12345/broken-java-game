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
public class MobSpawnTrigger extends Trigger{
    @Override
    public void onActivate(GameObject obj){
        for(DynamicObject mar : Main.window.nodes){
            if(mar instanceof MobSpawner){
                MobSpawner ms = (MobSpawner) mar;
                if(ms.id == id){
                    spawn(ms);
                }
            }
        }
    }
    
    public void spawn(MobSpawner ms){
        ms.spawn();
    }
}
