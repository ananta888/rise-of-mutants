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


/**
 *
 * @author @author Ulrich Nzuzi <ulrichnz@code.google.com>
 */
public class BBObject {
    
    public enum ObjectTag{
        NONE,
        PLAYER,
        MONSTER,
        BUILDING
    }
    
    
    //The name of the object.
    protected String name;
    //The id of the object
    private String id;
    private CompType type = CompType.NONE;
    protected ObjectTag mTag = ObjectTag.NONE;
    
    public BBObject(String name){
        this.name = name;
    }
    
    public String getObjectName(){
        return name;
    }
    
    public String getObjectID(){
        return id;
    }
    protected CompType getType(){
        return type;
    }
    
    protected void setType(CompType type){
        this.type = type;
    }
   

}
