/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Main;
import gameengine.objects.Ammo;
import gameengine.objects.Mob;
import gameengine.objects.mobs.PrisonGuard;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class BreakablePrisonDoor extends PrisonDoor{
    public int hp = 200;
    public int maxhp = 200;
    @Override
    public boolean canOpen(Mob m){
        return (m instanceof PrisonGuard);
    }
    
    @Override
    public void onShot(Ammo a){
        hp-=a.damage;
        if(hp<=0){
            open();
            alertNearMobs(false, true, 100);
        }
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        if(hp<maxhp && hp>0){
        float hppc = (float)hp/(float)maxhp;
            gr.setColor(new Color(64,64,64));
            gr.fillRect(4, 0, 24, 3);
            gr.setColor(new Color((int)(64+(1-hppc)*(255-64)),(int)(hppc*255),(int)((1-hppc)*64)));
            gr.fillRect(4, 0, (int) (hppc*24), 3);
            gr.drawRect(4, 0, 24, 3);
        }
        return nimg;
    }
}
