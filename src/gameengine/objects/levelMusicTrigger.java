/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;

/**
 *
 * @author Camper2012
 */
public class levelMusicTrigger extends Trigger{
    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\levelmusic";
    }

    @Override
    public void onActivate(GameObject obj) {
        
    }
    
    
}
