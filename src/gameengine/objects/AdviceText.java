/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class AdviceText extends Text{
    public static float LEN_MULT = 1f;
    public int move = 0;
    @Override
    public void initVars() {
        super.initVars();
        color = new Color(250,180,60);
    }
    
    @Override
    public void onUpdate(){
        if(move<=text.length()*LEN_MULT){
            move += 1;
            move(x,y-1);
        }else{
            remove();
        }
        updateIcon();
    }
}
