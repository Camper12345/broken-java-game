/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.Defines;
import gameengine.GUI;
import gameengine.Main;
import gameengine.StaticObject;
import gameengine.objects.Weapon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class PlayerAmmoBar extends GUI{
    Weapon weapon;
    
    @Override
    public void initVars(){
        super.initVars();
        staticSize = true;
        width = 140;
        height = 32;
        isVisible = true;
    }
    
    public void setDrawWeapon(Weapon wep){
        weapon = wep;
    }
    
    @Override
    public BufferedImage specialUpdateIcon(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        if(!Main.isNull(weapon)){
            //AMMO
            gr.setColor(new Color(16,16,16,128));
            gr.fillRect(0, 0, 140, 32);
            gr.drawImage(Main.window.resource.getIcon("interface\\weapondrawrim").getImage(), 140-96-Defines.ICON_SIZE-8, 0, null);
            gr.drawImage(Main.window.resource.getIcon(weapon.wpIcon+"_draw").getImage(), 140-96-Defines.ICON_SIZE-8, 0, null);
            if(!weapon.canShot() && !weapon.isMelee()){
                gr.drawImage(Main.window.resource.getIcon("interface\\weaponalert").getImage(), 140-96, 32-20,null);
            }
        }
        return nimg;
    }
}
