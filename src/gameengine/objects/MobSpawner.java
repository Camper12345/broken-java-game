/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Main;

/**
 *
 * @author Camper2012
 */
public class MobSpawner extends Marker{
    public Mob spawnmob;
    public int spawned = 0;
    public Mob dospawn(){
        if(!Main.isNull(spawnmob)){
            Mob sp = (Mob) createObject(spawnmob, x, y, z);
            spawned++;
            return sp;
        }
        return null;
    }
    
    public Mob spawn(){
        return dospawn();
    }
}
