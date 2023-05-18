/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.GameObject;
import gameengine.objects.Ammo;
import gameengine.objects.MeleeAmmo;
import gameengine.objects.props.Pipes;

/**
 *
 * @author Camper2012
 */
public class PJExplodingPipe extends Pipes{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\decoration\\expl_pipe";
    }
    
    @Override
    public void onCollide(GameObject obj) {
        if(obj instanceof Ammo && !(obj instanceof MeleeAmmo)){
            explode(130);
            remove();
        }
    }
    
}
