/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.*;
import gameengine.objects.TestObject;
import gameengine.objects.mobs.Player;
import gameengine.objects.weapon.Fists;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author Camper2012
 */
public class Frame extends JFrame {
    public static ArrayList<Chunk> map = new ArrayList();
    public static ArrayList<LightSource> lights = new ArrayList();
    public static ArrayList<DynamicObject> nodes = new ArrayList();
    public static ArrayList<GUI> guis = new ArrayList();
    public static ArrayList<Mob> mobs = new ArrayList();
    public HashMap<Integer, Boolean> keys = new HashMap();
    public HashMap<Integer, Boolean> mouse = new HashMap();
    public MasterController master;
    public GameController game;
    public MusicController music;
    public ResourcesManager resource = new ResourcesManager();
    public static TicklagController tick = new TicklagController();
    public static String mainDir;
    public static String dataDir;
    public static String iconDir;
    public static String soundDir;
    public static String savesDir;
    public static String configDir;
    public static String mapDir;
    public static String iconFormat = ".png";
    public static String soundFormat = ".wav";
    public static String mapFormat = ".map";
    public boolean windowActive = true;
    public static int mouseX = 0;
    public static  int mouseY = 0;
    public static int mouseWheelRotation = 0;
    public Frame() {
        initDir();
        resource.loadResources();
        game_init();
        windowInit();
        master = new MasterController(getGraphics());
        master.start();
        game = new GameController();
        game.start();
        music = new MusicController();
        music.start();
        
    }
    
    public void windowInit(){
        initComponents();
    }
    
    private void game_init(){
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setWindowSize(sSize.width,sSize.height);
        Main.info("Screen size aligned to "+sSize.width+"x"+sSize.height);
    }
    
    public void tick(){
        game.tick();
        music.tick();
        master.tick();
    }
    
    public void setWindowSize(int w,int h){
        Defines.RES_MULT = Math.min((float)w/(float)Defines.WIDTH,(float)h/(float)Defines.HEIGHT);
        Defines.SCREEN_WIDTH = (int)(Defines.WIDTH*Defines.RES_MULT);
        Defines.SCREEN_HEIGHT = (int)(Defines.HEIGHT*Defines.RES_MULT);
        //System.out.println(Defines.SCREEN_WIDTH+" & "+Defines.SCREEN_HEIGHT+" *"+Defines.RES_MULT+" ("+w+";"+h+")");
        Defines.SCR_X = (w-Defines.SCREEN_WIDTH)/2;
        Defines.SCR_Y = (h-Defines.SCREEN_HEIGHT)/2;
        Defines.SCREEN_WIDTH_F = w;
        Defines.SCREEN_HEIGHT_F = h;
        setResizable(true);
            setMinimumSize(new Dimension(w, h));
            setUndecorated(true);
            setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
    }
    
    private void initDir(){
        mainDir = System.getProperty("user.dir")+"\\";
        dataDir = mainDir+"data\\";
        iconDir = dataDir+"icon\\";
        soundDir = dataDir+"sound\\";
        savesDir = dataDir;
        configDir = dataDir+"config\\";
        mapDir = dataDir+"maps\\";
        Main.info("Directory is "+mainDir);
    }
    
    public GameObject createObject(GameObject obj, int x, int y, int z, boolean init){      
        obj.assignToChunk();
        obj.initVars();
        if(init){
        obj.preInit(x,y,z);
        }
        return obj;
    }
    public GameObject createObject(GameObject obj, int x, int y, int z){
        return createObject(obj,x,y,z,true);
    }
    public void objtest(int num){
        Main.info(num*5+" objects created by objtest.");
        for(int i = num; i>0; i--)
        {
        createObject(new TestObject(),Defines.WIDTH/2-16,Defines.HEIGHT/2-16,0);
        createObject(new TestObject(),Defines.WIDTH/2+100-16,Defines.HEIGHT/2-16,0);
        createObject(new TestObject(),Defines.WIDTH/2-16,Defines.HEIGHT/2+100-16,0);
        createObject(new TestObject(),Defines.WIDTH/2-100-16,Defines.HEIGHT/2-16,0);
        createObject(new TestObject(),Defines.WIDTH/2-16,Defines.HEIGHT/2-100-16,0);
        }
    }
    public Graphics getScreen(){
        return this.getGraphics();
    }
    
    public boolean issObjectExists(GameObject eobj){
        boolean is = false;
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject) gobj;
                if(eobj.equals(obj)){
                    is = true;
                    break;
                }
            }
        }
        return is;
    }
    
    public boolean isTypeExists(GameObject eobj){
        boolean is = false;
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            for(Object gobj : ch.contents.toArray()){
            GameObject obj = (GameObject) gobj;
            if(eobj.getClass().equals(obj.getClass())){
                is = true;
                break;
            }
        }
        }
        return is;
    }
    
    public ArrayList getObjects(int nx, int ny, int nz){
        ArrayList<GameObject> objs = new ArrayList();
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject) gobj;
                if(nx == obj.x && ny == obj.y && nz == obj.z){
                    objs.add(obj);
                }
            }
        }
        return objs;
    }
    
    public boolean isObjectOnPos(int nx, int ny, Object cobj){
        return isTypeOnPos(nx,ny,master.currentZ,cobj);
    }
    
    public boolean isObjectOnPos(int nx, int ny, int nz, GameObject cobj){
        boolean is = false;
        for(Object gobj : getObjects(nx,ny, nz).toArray()){
            GameObject obj = (GameObject) gobj;
            if(nx == obj.x && ny == obj.y && nz == obj.z && obj.equals(cobj)){
                is = true;
                break;
            }
        }
        return is;
    }
    
    public boolean isTypeOnPos(int nx, int ny, Object cls){
        return isTypeOnPos(nx,ny,master.currentZ,cls);
    }
    
    public boolean isTypeOnPos(int nx, int ny, int nz, Object cls){
        boolean is = false;
        for(Object gobj : getObjects(nx,ny,nz).toArray()){
            GameObject obj = (GameObject) gobj;
            if(nx == obj.x && ny == obj.y && nz == obj.z && (obj.getClass() == cls.getClass())){
                is = true;
                break;
            }
        }
        return is;
    }  
    
    public void resetMap(){
        if(!Main.isNull(master.playerObj)){
        master.playerObj.remove();
        }
        if(!Main.isNull(master.console)){
        master.console.remove();
        }
        master.playerObj = null;
        master.console = null;
        master.objShiftX = 0;
        master.objShiftY = 0;
        map.clear();
        Defines.GRAPHICS_BACKGROUND_COLOR = new Color(5,5,5);
        lights.clear();
        nodes.clear();
        guis.clear();
        master.pause = false;
    }
        
    public void savePlayer(){
        savePlayer(game.currentMap.getName());
    }
    
    public void savePlayer(String smap){
        File file = new File(savesDir+"save.sav");
        try{
            if(!Main.isNull(master.playerObj)){
                FileOutputStream os = new FileOutputStream(file);
                OutputStreamWriter fw = new OutputStreamWriter(os);
                Player pl = master.playerObj;
                fw.write(smap+" ");
                fw.write(pl.hp+" ");
                fw.write(pl.weapon.getClass().getName()+" ");
                fw.write(pl.weapon.ammo+" ");
                fw.write(pl.weapon.holdammo+" ");
                fw.write((pl.flashlight.brightness>0)+" ");
                fw.write(""+pl.scenarioFlags);
                
                fw.close();
                os.close();
            }
        }catch(IOException ex){
            Main.critical("Cannot save player object "+ex);
        }
    }
    
    public String getGameMap(){
        File file = new File(savesDir+"save.sav");
        String lmap = "level0";
        try{
            if(file.exists()){
                FileReader is = new FileReader(file);
                BufferedReader fr = new BufferedReader(is);
                String inp = fr.readLine();
                String[] data;
                data = inp.split(" ");
                lmap = data[0];
                lmap = lmap.substring(0, lmap.length()-4);              
                fr.close();
                is.close();
            }
        }catch(IOException | NumberFormatException ex){
            Main.critical("Cannot load player object "+ex);
        }
        return lmap;
    }
    
    public void loadPlayer(){
        File file = new File(savesDir+"save.sav");

        String[] data;
        PlayerStart ps  = null;
        for(DynamicObject mrk : nodes){
            if(mrk instanceof PlayerStart){
                ps = (PlayerStart) mrk;
                break;
            }
        }
        if(Main.isNull(ps)){
            return;
        }
        Player pl = (Player) createObject(new Player(), ps.x, ps.y, ps.z);
        int hp = 300;
        Weapon wp = (Weapon) createObject(new Fists(), pl.x, pl.y, pl.z);
        int ammo = 0;
        int holdammo = 0;
        boolean flash = false;
        int scflags = 0;
        try{
            if(file.exists()){
                FileReader is = new FileReader(file);
                BufferedReader fr = new BufferedReader(is);
                String inp = fr.readLine();
                data = inp.split(" ");
                hp = Integer.parseInt(data[1]);
                wp = (Weapon) createObject((GameObject)Class.forName(data[2]).newInstance(), pl.x, pl.y, pl.z);
                ammo = Integer.parseInt(data[3]);
                holdammo = Integer.parseInt(data[4]);
                flash = Boolean.parseBoolean(data[5]);        
                scflags = Integer.parseInt(data[6]);
                fr.close();
                is.close();
                }
            }catch(IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | NumberFormatException ex){
            Main.critical("Cannot load player object "+ex);
            }

        pl.hp = hp;
        pl.weapon.remove();
        pl.weapon = wp;
        wp.addToContainer(pl);
        wp.ammo = ammo;
        wp.holdammo = holdammo;
        if(flash){
            pl.enableFlashlight();
        }else{
            pl.disableFlashlight();
        }
        pl.scenarioFlags = scflags;
    }
    
    public void loadMap(File file){
        master.loading = true;
        master.currentZ = 0;
        master.loadingname = "Preparing...";
        master.loadingstatus = 1;
        master.loadingtext = "";
        master.loadingcolor = Defines.LOADING_YELLOW;
        /*for(Object gobj : scrObjects.toArray()){
            GameObject obj = (GameObject) gobj;
            if((obj instanceof DynamicObject && obj.saveable)){
                obj.remove();
            }
        }*/
        resetMap();
        game.createStaticObjects();
        Main.info("Map cleared. Loading "+file.getName());
        try{
        master.loadingname = "Loading "+file.getName();
        master.loadingcolor = Defines.LOADING_GREEN;
        FileReader is = new FileReader(file);
        BufferedReader fr = new BufferedReader(is);
        int maxload = Integer.parseInt(fr.readLine());
        int count = 0;
        while(count<maxload){
            count+=1;
            try{
            String[] data = new String[9];
            String inp = fr.readLine();
            data = inp.split(" ");        
            Object gobj = Class.forName(data[0]).newInstance();
            DynamicObject obj = (DynamicObject) gobj;
            obj.initVars();
            obj.x = Integer.parseInt(data[1]);
            obj.y = Integer.parseInt(data[2]);
            obj.z = Integer.parseInt(data[3]);
            master.currentZ = obj.z;
            obj.direction = Float.parseFloat(data[4]);
            obj.DefColor = new Color(Integer.parseInt(data[5]));
            obj.DefRange = Integer.parseInt(data[6]);
            obj.DefStrength = Integer.parseInt(data[7]);
            obj.DefStatus = Boolean.parseBoolean(data[8]);
            obj.setColorSetting(obj.DefColor);
            obj.setRangeSetting(obj.DefRange);
            obj.setStrengthSetting(obj.DefStrength);
            obj.setStatusSetting(obj.DefStatus);
            obj.preInit(obj.x,obj.y,obj.z);
            master.loadingstatus = (int) (((float)count/(float)maxload)*100);
            master.loadingtext = "Loading \""+obj.getClass().getSimpleName()+"\" at "+obj.x+":"+obj.y+":"+obj.z;
            }catch(IOException | ClassNotFoundException | InstantiationException | 
                    IllegalAccessException | NumberFormatException | NullPointerException ex){
                Main.error("Error occurred while loading object "+file.getName()+", object was skipped.");
                continue;
            }
        }
        game.currentMap = file;
        master.loading = false;
        Main.info(count-1+" objects in "+map.size()+" chunks loaded from map "+file.getAbsolutePath());
        is.close();
        fr.close();
        }catch(IOException | NumberFormatException ex){
            Main.critical("Error while loading map "+file.getAbsolutePath()+" "+ex);
            master.loading = false;
        }
                
    }
    
    public void loadMap(String name){
        loadMap(new File(mapDir+name+mapFormat));
    }
    
    public void saveMap(File file){
        try{
        FileOutputStream os = new FileOutputStream(file);
        OutputStreamWriter fw = new OutputStreamWriter(os);
        ArrayList<DynamicObject> sobjs = new ArrayList();
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject) gobj;
                if(obj instanceof DynamicObject && obj.saveable){
                    sobjs.add((DynamicObject)obj);

                }
            }
        }
        fw.write(sobjs.size()+"\n");
        for(Object gobj : sobjs.toArray()){
            DynamicObject obj = (DynamicObject) gobj;
            if(obj.saveable){
            fw.write(obj.getClass().getName()+" ");
            fw.write(obj.x+" ");
            fw.write(obj.y+" ");
            fw.write(obj.z+" ");
            fw.write(obj.direction+" ");
            fw.write(obj.DefColor.getRGB()+" ");
            fw.write(obj.DefRange+" ");
            fw.write(obj.DefStrength+" ");
            fw.write(obj.DefStatus+"\n");
            }
        }
        fw.close();
        Main.info("Map "+file.getAbsolutePath()+" saved succesfully!");
        }catch(IOException ex){
            Main.error("Error while loading map "+file.getAbsolutePath()+" "+ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mouseMove(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mouseMove(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                mouseWheel(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        windowActive = true;
    }//GEN-LAST:event_formFocusGained

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        windowActive = false;
    }//GEN-LAST:event_formFocusLost

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        keys.put(evt.getKeyCode(),true);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        keys.put(evt.getKeyCode(),false);
    }//GEN-LAST:event_formKeyReleased

    private void mouseWheel(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_mouseWheel
         mouseWheelRotation += evt.getWheelRotation();
    }//GEN-LAST:event_mouseWheel

    private void mouseMove(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseMove
        mouseX = (int) (evt.getX()/Defines.RES_MULT-Defines.SCR_X/Defines.RES_MULT);
        mouseY = (int) (evt.getY()/Defines.RES_MULT-Defines.SCR_Y/Defines.RES_MULT);
    }//GEN-LAST:event_mouseMove

    private void MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MousePressed
        mouse.put(evt.getButton(), true);
    }//GEN-LAST:event_MousePressed

    private void MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MouseReleased
        mouse.put(evt.getButton(), false);
    }//GEN-LAST:event_MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
