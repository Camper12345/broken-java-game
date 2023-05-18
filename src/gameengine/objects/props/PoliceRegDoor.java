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
public class PoliceRegDoor extends SlidingDoor{
    
    @Override
    public boolean canOpen(Mob m){
        return m instanceof Police;      
    }
}

