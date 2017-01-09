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
    private int level;
    private int score;
    private int health;
    private int budget;
    private boolean lastGameWon;

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
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        
        
        Box floorBox = new Box(33, 0, -33);
        Geometry floorGeom = new Geometry("flor box", floorBox);

        Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setColor("Color", ColorRGBA.Orange);
        floorGeom.setMaterial(floorMat);

        rootNode.attachChild(floorGeom);
        
        playerNode = new Node("player node");
        creepNode = new Node("creep node");
        towerNode = new Node("tower node");
        
        rootNode.attachChild(playerNode);
        rootNode.attachChild(creepNode);
        rootNode.attachChild(towerNode);
        
        Geometry playerBaseGeom = BoxCreator.create(
                assetManager, 3, 1, 2, "playerBase Box", 3);
        playerNode.attachChild(playerBaseGeom);
        playerBaseGeom.setLocalTranslation(0.0f, 1.0f, 0.0f);
        
        Geometry towerGeom = BoxCreator.create(assetManager, 1, 2, 1, "tower box", 4);
        towerNode.attachChild(towerGeom);
        towerGeom.setLocalTranslation(-5.0f, 2.0f, -5.0f);
        Geometry towerGeom2 = BoxCreator.create(assetManager, 1, 2, 1, "tower box", 4);
        towerNode.attachChild(towerGeom2);
        towerGeom2.setLocalTranslation(5.0f, 2.0f, -5.0f);
        towerNode.setUserData("index", 0);
        towerNode.setUserData("chargesNum", 100);
        
        Geometry creepGeom = BoxCreator.create(assetManager, 1, 1, 1, "creep box", 0);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(0.5f, 1.0f, -30.0f);
        creepGeom = BoxCreator.create(assetManager, 1, 1, 1, "creep box", 0);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(0.0f, 1.0f, -25.0f);
        creepGeom = BoxCreator.create(assetManager, 1, 1, 1, "creep box", 0);
        creepNode.attachChild(creepGeom);
        creepGeom.setLocalTranslation(-0.5f, 1.0f, -20.0f);
        creepNode.setUserData("index", 0);
        creepNode.setUserData("health", 100);
    }//initialize
    
    @Override
    public void cleanup(){
        rootNode.detachChild(creepNode);
        rootNode.detachChild(playerNode);
        rootNode.detachChild(towerNode);
    }//cleanup

   
}//class
