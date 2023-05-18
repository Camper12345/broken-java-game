/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Defines;
import gameengine.GameObject;

/**
 *
 * @author Camper2012
 */
public class AdviceTrigger extends Trigger{
    public String text = "Здесь могла быть ваше реклама.";
    public boolean show = true;
    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\advice";
    }

    @Override
    public void onActivate(GameObject obj) {
        if(show){
            AdviceText txt = (AdviceText) createObject(new AdviceText(), 16, Defines.HEIGHT-128, z);
            txt.updateText(text);
            txt.updateSize(10);
            setTimer(0,100);
            show = false;
        }
        if(!isTimer(0)){
            show = true;
        }
        
    }
    
    
}
