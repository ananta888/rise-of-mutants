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
package com.bigboots;


/**
 *
 * @author Ulrich Nzuzi <ulrichnz@code.google.com>
 */
public class BBGlobals {
    public static final String VERSION = "0.0.1";
    public static final String NAME = "Rise Of Mutants";
    public static final String TYPE = "SCG"; //Side-Scrolling Game
    public static final int SCENE_FPS = 60;
    public static final float PHYSICS_TPT = 1f/60f; //Physic time pr. tick. Convention might require renaming.

    public static final boolean PHYSICS_THREADED = true; // Function not implemented.
    public static final boolean PHYSICS_DEBUG = false; // Function not implemented.
}
