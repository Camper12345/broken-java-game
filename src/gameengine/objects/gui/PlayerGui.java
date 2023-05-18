/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.*;
import gameengine.objects.*;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class PlayerGui extends GUI{
    public Player player = null;
    
    public Text health;
    public Text ammo;
        
    public PlayerHealthBar healthbar;
    public PlayerAmmoBar ammobar;
    
    @Override
    public void init(int nx, int ny){
        layer = Defines.LAYER_BUTTON-0.12f;
        isVisible = true;
        health = (Text) addElement(new Text(), 8, Defines.HEIGHT-32);
        health.updateColor(new Color(255,128,0));
        health.updateSize(18);
        health.updateText("100");
        health.layer = Defines.LAYER_BUTTON-0.11f;
        
        ammo = (Text) addElement(new Text(), Defines.WIDTH-96, Defines.HEIGHT-32);
        ammo.updateColor(new Color(255,128,0));
        ammo.updateSize(14);
        ammo.updateText("0/0");
        ammo.layer = Defines.LAYER_BUTTON-0.11f;
        
        healthbar = (PlayerHealthBar) addElement(new PlayerHealthBar(), 0, Defines.HEIGHT-32);
        healthbar.layer = Defines.LAYER_BUTTON-0.12f;
        
        ammobar = (PlayerAmmoBar) addElement(new PlayerAmmoBar(), Defines.WIDTH-140, Defines.HEIGHT-32);
        ammobar.layer = Defines.LAYER_BUTTON-0.12f;
    }
    
    @Override
    public void onUpdate(){
        if(!Main.isNull(player)){
        health.updateText(""+(int)((float)player.hp/(float)player.maxhp*100f));
        if(!player.weapon.isMelee()){
            ammo.updateText(player.weapon.ammo+"/"+player.weapon.holdammo);
        }else{
            ammo.updateText("-/-");
        }
        healthbar.setDrawHp(((float)player.hp/(float)player.maxhp));
        ammobar.setDrawWeapon(player.weapon);
        }
        updateShowing(show);
    }
    
    /*@Override
    public BufferedImage specialUpdateIcon(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        if(!Main.isNull(player)){
            //HEALTH
            gr.setColor(new Color(16,16,16,128));
            gr.fillRect(0, Defines.HEIGHT-32, 216, 32);
            float hppc = ((float)player.hp/(float)player.maxhp);
            gr.setColor(new Color((int)(64+(1-hppc)*(255-64)),(int)(hppc*255),(int)((1-hppc)*64)));
            gr.fillRect(8, Defines.HEIGHT-10, (int) (hppc*200), 6);
            gr.drawRect(8, Defines.HEIGHT-10, 200, 6);

            //AMMO
            gr.setColor(new Color(16,16,16,128));
            gr.fillRect(Defines.WIDTH-140, Defines.HEIGHT-32, 140, 32);
            gr.drawImage(Main.window.resource.getIcon("weapondrawrim").getImage(), Defines.WIDTH-96-Defines.ICON_SIZE-8, Defines.HEIGHT-32, null);
            gr.drawImage(Main.window.resource.getIcon(player.weapon.drawIcon).getImage(), Defines.WIDTH-96-Defines.ICON_SIZE-8, Defines.HEIGHT-32, null);
            if(!player.weapon.canShot()){
                gr.drawImage(Main.window.resource.getIcon("weaponalert").getImage(), Defines.WIDTH-96, Defines.HEIGHT-20,null);
            }
        }
        return nimg;
    }*/
}
