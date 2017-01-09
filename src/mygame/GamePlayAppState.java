/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Line;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author mac
 */
public class GamePlayAppState extends AbstractAppState{
    
    private AssetManager assetManager;
    private Node rootNode;
    private SimpleApplication app;
    Node playerNode;
    Node creepNode;
    Node towerNode;
    Node beamNode;
    private int level;
    private int score;
    private int health = 100;
    private int budget = 100;
    private boolean lastGameWon = false;
    private float timer_budget = 0;
    private float timer_beam = 0;

    @Override
    public void initialize(AppStateManager stateManager, Application app){
        
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        
        
        Box floorBox = new Box(33, 0, -33);
        Geometry floorGeom = new Geometry("floor box", floorBox);

        Material floorMat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setColor("Color", ColorRGBA.Orange);
        floorGeom.setMaterial(floorMat);

        rootNode.attachChild(floorGeom);
        
        playerNode = new Node("player node");
        creepNode = new Node("creep node");
        towerNode = new Node("tower node");
        beamNode = new Node("beam node");
        
        
        rootNode.attachChild(playerNode);
        rootNode.attachChild(creepNode);
        rootNode.attachChild(towerNode);
        rootNode.attachChild(beamNode);
        
        Geometry playerBaseGeom = BoxCreator.create(
                getAssetManager(), 3, 1, 2, "playerBase Box", 3);
        playerNode.attachChild(playerBaseGeom);
        playerBaseGeom.setLocalTranslation(0.0f, 1.0f, 0.0f);
        
        Geometry towerGeom = BoxCreator.create(getAssetManager(), 1, 2, 1, "tower box", 4);
        towerGeom.addControl(new TowerControl(this));
        towerGeom.getControl(TowerControl.class).setChargesNum(1);
        towerGeom.getControl(TowerControl.class).setIndex(0);
        towerGeom.getControl(TowerControl.class).setHeight(2.0f);
        towerNode.attachChild(towerGeom);
        towerGeom.setLocalTranslation(-5.0f, 2.0f, -5.0f);
        Geometry towerGeom2 = BoxCreator.create(getAssetManager(), 1, 2, 1, "tower box", 4);
        towerGeom2.addControl(new TowerControl(this));
        towerGeom2.getControl(TowerControl.class).setChargesNum(1);
        towerGeom2.getControl(TowerControl.class).setIndex(1);
        towerGeom2.getControl(TowerControl.class).setHeight(2.0f);
        towerNode.attachChild(towerGeom2);
        towerGeom2.setLocalTranslation(5.0f, 2.0f, -5.0f);
        
        Geometry creepGeom = BoxCreator.create(getAssetManager(), 1, 1, 1, "creep box", 0);
        creepGeom.addControl(new CreepControl(this));
        creepGeom.getControl(CreepControl.class).setHealth(100);
        creepGeom.getControl(CreepControl.class).setIndex(0);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(0.5f, 1.0f, -30.0f);
        creepGeom = BoxCreator.create(getAssetManager(), 1, 1, 1, "creep box", 0);
        creepGeom.addControl(new CreepControl(this));
        creepGeom.getControl(CreepControl.class).setHealth(100);
        creepGeom.getControl(CreepControl.class).setIndex(1);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(0.0f, 1.0f, -25.0f);
        creepGeom = BoxCreator.create(getAssetManager(), 1, 1, 1, "creep box", 0);
        creepGeom.addControl(new CreepControl(this));
        creepGeom.getControl(CreepControl.class).setHealth(100);
        creepGeom.getControl(CreepControl.class).setIndex(2);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(-0.5f, 1.0f, -20.0f);
    }//initialize
    
        
   @Override
    public void update(float tpf){
        timer_budget += tpf;
        timer_beam += tpf;
        if(timer_budget > 10){
            timer_budget = 0;
            budget += 10;
        }//if
        if(timer_beam > 1){
            timer_beam = 0;
            beamNode.detachAllChildren();
        }//if        
        if(health <= 0){
            lastGameWon = false;
            app.getStateManager().detach(this);
        }//if
        if(health > 0 && creepNode.getChildren().isEmpty()){
            lastGameWon = true;
            app.getStateManager().detach(this);
        }//if
    }//update
    
    @Override
    public void cleanup(){
        rootNode.detachChild(creepNode);
        rootNode.detachChild(playerNode);
        rootNode.detachChild(towerNode);
        rootNode.detachChild(beamNode);
    }//cleanup   

    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }
   
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * @return the lastGameWon
     */
    public boolean isLastGameWon() {
        return lastGameWon;
    }
    
     /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * @param lastGameWon the lastGameWon to set
     */
    public void setLastGameWon(boolean lastGameWon) {
        this.lastGameWon = lastGameWon;
    }
    
}//class
