/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class ImageFile {
    ArrayList<Image> images = new ArrayList();
    public ImageFile(Image img){
        images.add(img);
    }
    
    public ImageFile(){
        
    }
    
    public int getLength(){
        return images.size();
    }
    public Image getImage(int num){
        return (getLength()>=num+1 && num>=0)?(isAnimation()?images.get(num):images.get(0)):(Image)(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
    }
    
    public Image getImage(){
        return getImage(0);
    }
    
    public Image getRandomImage(){
        return images.get((int)(Math.random()*(getLength()-1)));
    }
    
    public boolean isAnimation(){
        return images.size()>1;
    }
    public void add(Image img){
        images.add(img);
    }
    public void del(int ind){
        images.remove(ind);
    }
    public void set(int ind, Image img){
        images.set(ind,img);
    }
}
