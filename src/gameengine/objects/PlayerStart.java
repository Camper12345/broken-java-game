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
public class PlayerStart extends Marker{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\start";
    }
    
    @Override
    public void init(int nx, int ny){
        for(Object mar : Main.window.nodes.toArray()){
            if(mar instanceof PlayerStart){
                PlayerStart ps = (PlayerStart) mar;
                ps.remove();
            }
        }
        super.init(nx, ny);
    }
}
