/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.automation.seletest.core.selenium.mobileAPI;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.util.HashMap;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;



/**
 * Appium Controller interface
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
public interface AppiumController<T> {

    /**
     * This method launch application in mobile device
     * @param <T>
     * @param remoteWebDriver
     */
    T launchApp();

    /**
     * This method resets application in mobile device
     * @return
     */
    T resetApp();


    /**
     * This method resets application in mobile device
     * @return
     */
    T runAppinBackground(int sec);

    /**
     * This method closes application in mobile device
     * @return
     */
    T closeApp();

    /**
     * This method installs an app or ipa file to mobile device
     * @param appPath
     * @param remoteWebDriver
     */
    T installApp(String bundleId, String appPath);


    /**
     * Performs a touch action
     * @param e
     * @return
     */
    T performTouchAction(TouchAction e);

    /**
     * Performs multitouch action
     * @param e
     * @return
     */
    T performMultiTouchAction(MultiTouchAction e);

    /**
     * Hide the kieyboard
     * @return
     */
    T hideKeyboard();

    /**
     * rotate screen of mobile device
     * @param e
     * @return
     */
    T rotate(ScreenOrientation e);

    /**
     * gets the screen orientation
     * @return
     */
    ScreenOrientation getscreen();

    /**
     * Gets multi touch action
     * @return
     */
    MultiTouchAction getMultiTouchAction();


    /**
     * checks if application is installed in device
     * @param bundleId
     * @return
     */
    boolean isAppInstalled(String bundleId);

    /**
     * Pinch to specific coordinates
     * @param x
     * @param y
     * @return
     */
    T pinch(int x,int y);

    /**
     * Locks the screen for specific amount of time
     * @param sec
     * @return
     */
    T lockScreen(int sec);


    /**
     * Tap to element with coordinates for specific duration
     * @param fingers
     * @param y
     * @param z
     * @param duration
     * @return
     */
    T tap(int fingers, int y, int z, int duration);

    /**
     * Shake the screen
     * @return
     */
    T shake();

    /**
     * Zoom to element with coordinates
     * @param x
     * @param y
     * @return
     */
    T zoom(int x, int y);

    /**
     * swipe gesture
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @param duration
     * @return
     */
    T swipe(int startx, int starty, int endx, int endy, int duration);


    /**
     * Get the Dimensions of the screen
     * @return Dimensions
     */
    Dimension getScreenDimensions();

    /**
     * Executes javascript
     * @param driverCommand
     * @param parameters
     * @return
     */
    T executeScript(String driverCommand, HashMap<String, ?> parameters);

    /**
     * Navigate Back
     * @return
     */
    T navigateBack();

    /**
     * Gets the currently focused activity
     * @return String CurrentActivity
     */
    String getCurrentActivity();



}
