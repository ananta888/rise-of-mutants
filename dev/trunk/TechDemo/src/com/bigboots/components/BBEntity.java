/*
 * Copyright (C) 2011  BigBoots Team
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * See <http://www.gnu.org/licenses/>.
 */
package com.bigboots.components;

import com.bigboots.components.BBComponent.CompType;
import com.bigboots.core.BBSceneManager;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author @author Ulrich Nzuzi <ulrichnz@code.google.com>
 * 
 * Composite of our Object component Design
 */
public class BBEntity extends BBObject{
    private BBComponent mNode;
    private BBComponent mAudio;
    private BBComponent mAnimation;
    private BBComponent mLstr;
    private BBComponent mCollision;
    private BBComponent mControl;
    private BBComponent mLight;

    
    protected boolean mEnable = true;
    private Node tmpSpatial;
    private boolean mCloned = false;
    
    //Collection of child graphics.
    private List<BBObject> mChildComponents = new ArrayList<BBObject>();
    //Collection of meshes
    private List<Geometry> mChildMeshes = new ArrayList<Geometry>();
    //Collection of Audio
    private HashMap<String, BBAudioComponent> mapAudioNode = new HashMap<String, BBAudioComponent>();
    
    
    public BBEntity(String name){
        super(name);
       
    }
    
    public BBEntity(String name, Node sp){
        this(name, sp, false);
    }
    
    public BBEntity(String name, Node sp, boolean clone){
        super(name);
        tmpSpatial = sp;
        tmpSpatial.setName(name);
        mCloned = clone;
        
        // Deprecated
        //Localy translate the entity spatial to go dow a bit.
//        tmpSpatial.setLocalTranslation(0, -0.85f, 0);

    }
    
    
    public void loadModel(String mesh){
       if(mNode == null){
           throw new IllegalStateException("Try loading a mesh file in Entity class without setting a Node Component first .\n"
                    + "Problem with Entity name: " + mObjectName);
       }
       
       // Deprecated
       if(!mesh.isEmpty()){
            tmpSpatial =  BBSceneManager.getInstance().loadSpatial(mesh);
            //Localy translate the entity spatial to go dow a bit. So, it align with collision shape
//            tmpSpatial.setLocalTranslation(0, -0.85f, 0);
       }
       
       
       tmpSpatial.setShadowMode(ShadowMode.CastAndReceive);
       this.getComponent(BBNodeComponent.class).attachChild(tmpSpatial);
       
       //Populate the list of meshes
       this.recurseNode((Node) tmpSpatial);

       //Set skills for TEST
       this.setSkills("HEALTH", 100);
    }
    
    public void attachToRoot(){
        BBSceneManager.getInstance().addChild((BBNodeComponent) mNode);
    }
    
    public void attachToNode(Node thenode){
        if(thenode == null){
           throw new IllegalStateException("Try setting null parent Node to Entity name: " + mObjectName);
        }
        BBNodeComponent node = (BBNodeComponent) mNode;     
        thenode.attachChild(node);
    }
    
    //Read the node child to find geomtry and stored it to the map for later access as submesh
    private void recurseNode(Node node){
        Node nd_temp = node;
        for (int i = 0; i < nd_temp.getChildren().size(); i++){
           if(nd_temp.getChildren().get(i) instanceof Node){
               recurseNode((Node) nd_temp.getChildren().get(i));
           }else{
            Geometry geom = (Geometry) nd_temp.getChildren().get(i);
            System.out.println("omomomomoomomomo GEOMETRY ADDED : "+geom.getName()+" for Entity "+mObjectName);
            mChildMeshes.add(geom);
           }
        }
    }
   
    public <T> T getSkills(String key) {
        return tmpSpatial.getUserData(key);
    }
    
    public void setSkills(String key, Object data) {
         tmpSpatial.setUserData(key, data);
    }
    
    public void setEnabled(boolean enabled) {
        mEnable = enabled;
    }

    public boolean isEnabled() {
        return mEnable;
    }
    //Adds the BBObject to the composition.
    public void addObjectComponent(BBObject obj) {
        mChildComponents.add(obj);
    }
 
    //Removes the BBObject from the composition.
    public void removeObjectComponent(BBObject obj) {
        mChildComponents.remove(obj);
    }
    
    public void addAudio(String name, BBAudioComponent audio){
        //TODO : Check if name or audio instance already exist in the map
        mapAudioNode.put(name, audio);
    }
    
    public BBAudioComponent getAudio(String name){
        return mapAudioNode.get(name);
    }
    
    public void stopAllAudio(){
        for (BBAudioComponent audio : mapAudioNode.values()) {
            audio.stop();
        }
    }
    
    public <T extends BBComponent>T addComponent(CompType type){
        if(type.equals(CompType.NODE)){
            mNode = new BBNodeComponent(mObjectName);
            BBNodeComponent node = (BBNodeComponent)mNode;
            //BBSceneManager.getInstance().addChild(node);
            return (T)mNode;
        }
        if(type.equals(CompType.ANIMATION)){
            mAnimation = new BBAnimComponent(tmpSpatial.getControl(AnimControl.class).createChannel());
            return (T)mAnimation;
        }
/*        if(type.equals(CompType.AUDIO)){
            mAudio = new BBAudioComponent();
            return (T)mAudio;
        }*/
        if(type.equals(CompType.LISTENER)){
            mLstr = new BBListenerComponent();
            return (T)mLstr;
        }
        if(type.equals(CompType.COLSHAPE)){
            mCollision = new BBCollisionComponent();
            return (T)mCollision;
        }
        if(type.equals(CompType.CONTROLLER)){
            mControl = new BBControlComponent();
            return (T)mControl;
        }
        
        return null;
    }
    
    public <T extends BBComponent>T getComponent(Class<T> name){
        if(name.equals(BBNodeComponent.class)){
            return (T)mNode;
        }
/*        if(name.equals(BBAudioComponent.class)){
            return (T)mAudio;
        }*/
        if(name.equals(BBAnimComponent.class)){
            return (T)mAnimation;
        }
        if(name.equals(BBListenerComponent.class)){
            return (T)mLstr;
        }
        if(name.equals(BBCollisionComponent.class)){
            return (T)mCollision;
        }
        if(name.equals(BBControlComponent.class)){
            return (T)mControl;
        }

       return null;

    }

    
    public void getChildComponent(){
        
    }
    
    public Geometry getChildMesh(String name){
        for (Geometry mc : mChildMeshes) {
            if(name.equals(mc.getName())){
                return mc;
            }
        }
        return null;
    }
    
    public void setMaterial(String matName){
       Material mat = BBSceneManager.getInstance().getAssetManager().loadMaterial(matName);
       tmpSpatial.setMaterial(mat);
    }
    
    public void setMaterialToMesh(String meshName, String matName){
       Material mat = BBSceneManager.getInstance().getAssetManager().loadMaterial(matName);
       Geometry mcomp = getChildMesh(meshName);
       if(mcomp == null){
           throw new IllegalStateException("Try loading an unexisting geometry part .\n"
                    + "Searching name [" + meshName+"] for "+mObjectName+" Entity");
       }

       mcomp.setMaterial(mat);
       System.out.println(" mmmmmmm MESH name : "+mcomp.getMaterial().getName());
       //mcomp.updateModelBound();
       //TangentBinormalGenerator.generate(mcomp);
    }

    public BBEntity clone(String name){
        BBEntity entCopy = new BBEntity(name, tmpSpatial.clone(false), true);
        entCopy.addComponent(CompType.NODE);
        entCopy.loadModel("");
        
        return entCopy;
    }
    
    public void destroy(){
        
        ((BBNodeComponent)mNode).detachAllChildren();
        ((BBNodeComponent)mNode).removeFromParent();
        ((BBNodeComponent)mNode).getWorldLightList().clear();
        ((BBNodeComponent)mNode).getLocalLightList().clear();
        mNode = null;
        
        tmpSpatial.removeFromParent();
        
        ((BBAudioComponent)mAudio).destroy();
        mAudio = null;
        
        mAnimation = null;
        mLstr = null;
        mCollision = null;
        mControl = null;
    }
   
}
