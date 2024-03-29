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
package com.bigboots.gui;

import java.util.Properties;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;

/**
 *
 * @author @author Ulrich Nzuzi <ulrichnz@code.google.com>
 */
public class BBProgressbarController implements Controller{
    private Element progressBarElement;
  private Element progressTextElement;

  public void bind(
      final Nifty nifty,
      final Screen screenParam,
      final Element element,
      final Properties parameter,
      final Attributes controlDefinitionAttributes) {
    progressBarElement = element.findElementByName("#progress");
    progressTextElement = element.findElementByName("#progress-text");
  }

  @Override
  public void init(final Properties parameter, final Attributes controlDefinitionAttributes) {
  }

  public void onStartScreen() {
      progressBarElement.setConstraintWidth(new SizeValue("96px"));
      progressBarElement.getParent().layoutElements();
  }

  public void onFocus(final boolean getFocus) {
  }

  public boolean inputEvent(final NiftyInputEvent inputEvent) {
    return false;
  }

  public void setProgress(final float progressValue) {
    float progress = progressValue;
    if (progress < 0.0f) {
      progress = 0.0f;
    } else if (progress > 1.0f) {
      progress = 1.0f;
    }
    final int MIN_WIDTH = 3; 
    int pixelWidth = (int)(MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
    progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
    progressBarElement.getParent().layoutElements();

    String progressText = String.format("%3.0f", progress * 100);
    progressTextElement.getRenderer(TextRenderer.class).setText(progressText);
  }

}
