/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Defines;
import gameengine.Main;
import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class GraphicsBackgroundChanger extends Marker{
    Color bcolor = new Color(5,5,5);
    @Override
    public void initVars(){
        super.initVars();
        basicIconName= "marker\\gbchanger";
        SETTING_COLOR = true;
        DefColor = new Color(5,5,5);
    }
    
    @Override
    public void setColorSetting(Color col){
        bcolor = col;
        DefColor = col;
    }
    
    @Override
    public void init(int nx, int ny){
        for(Object mar : Main.window.nodes.toArray()){
            if(mar instanceof GraphicsBackgroundChanger){
                GraphicsBackgroundChanger gbc = (GraphicsBackgroundChanger) mar;
                gbc.remove();
            }
        }
        super.init(nx, ny);
        Defines.GRAPHICS_BACKGROUND_COLOR = bcolor;
    }
}
