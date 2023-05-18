/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

/**
 *
 * @author Camper2012
 */
public class ToggleButton extends Button {

    @Override
    public void onPress(){  
    }
    
    @Override
    public void onClick(){
        pressed = !pressed;
        clicked = true;
        status = (byte) (pressed?1:2);
        updateIcon(true);
    }
    @Override
    public void resetBools(){
        clicked = false;
        over = false;
    }
    @Override
    public void onUpdate(){
       if(pressed){
            status = 2;
            updateIcon(true); 
       }else{
           status = (byte) (over?1:0);
           updateIcon(true); 
       }
    }
}
