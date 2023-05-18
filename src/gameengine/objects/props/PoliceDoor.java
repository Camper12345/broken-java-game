/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.objects.Mob;
import gameengine.objects.mobs.Police;

/**
 *
 * @author Camper2012
 */
public class PoliceDoor extends SlidingDoor{

    @Override
    public void initVars() {
        super.initVars();
        opacity = true;
        isOpacity = false;
        basicIconName = "obj\\doors\\policedoor";
    }

    @Override
    public void open() {
        super.open();
        setTimer(0, 10);
    }

    @Override
    public void onTimer(int id) {
        if(id == 0){
            close();
        }
    }
    
    
    
    @Override
    public boolean canOpen(Mob m){
        return m instanceof Police;      
    }
}
