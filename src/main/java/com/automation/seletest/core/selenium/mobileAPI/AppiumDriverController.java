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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * AppiumDriverController api for iOS-Android native app interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Scope("prototype")
public class AppiumDriverController implements AppiumController<AppiumDriverController>{

    @Getter @Setter AppiumDriver appiumDriver;

    @Override
    public AppiumDriverController launchApp() {
        appiumDriver.launchApp();
        return this;
    }

    @Override
    public AppiumDriverController resetApp() {
        appiumDriver.resetApp();
        return this;
    }

    @Override
    public AppiumDriverController runAppinBackground(int sec) {
        appiumDriver.runAppInBackground(sec);
        return this;
    }

    @Override
    public AppiumDriverController closeApp() {
        appiumDriver.closeApp();
        return this;
    }

    @Override
    public AppiumDriverController installApp(String bundleId, String appPath) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("appPath", appPath);
        appiumDriver.installApp(appPath);
        return this;
    }

    @Override
    public AppiumDriverController performTouchAction(TouchAction e) {
        appiumDriver.performTouchAction(e);
        return this;
    }

    @Override
    public AppiumDriverController performMultiTouchAction(MultiTouchAction e) {
        appiumDriver.performMultiTouchAction(e);
        return this;
    }

    @Override
    public AppiumDriverController hideKeyboard() {
        appiumDriver.hideKeyboard();
        return this;
    }

    @Override
    public AppiumDriverController rotate(ScreenOrientation e) {
        appiumDriver.rotate(e);
        return this;
    }

    @Override
    public ScreenOrientation getscreen() {
        return appiumDriver.getOrientation();
    }

    @Override
    public MultiTouchAction getMultiTouchAction() {
        return new MultiTouchAction(appiumDriver);
    }

    @Override
    public boolean isAppInstalled(String bundleId) {
        return appiumDriver.isAppInstalled(bundleId);
    }

    @Override
    public AppiumDriverController pinch(int x, int y) {
        appiumDriver.pinch(x, y);
        return this;
    }

    @Override
    public AppiumDriverController lockScreen(int sec) {
        appiumDriver.lockScreen(sec);
        return this;
    }

    @Override
    public AppiumDriverController tap(int finger, int y, int z, int duration) {
        appiumDriver.tap(finger,y,z,duration);
        return this;
    }

    @Override
    public AppiumDriverController shake() {
        appiumDriver.shake();
        return this;
    }

    @Override
    public AppiumDriverController zoom(int x, int y) {
        appiumDriver.zoom(x, y);
        return this;
    }

    @Override
    public AppiumDriverController swipe(int startx, int starty, int endx, int endy,
            int duration) {
        appiumDriver.swipe(startx, starty, endx, endy, duration);
        return this;
    }

    @Override
    public Dimension getScreenDimensions() {
        return appiumDriver.manage().window().getSize();
    }

    @Override
    public AppiumDriverController executeScript(String driverCommand, HashMap<String, ?> parameters) {
        appiumDriver.execute(driverCommand, parameters);
        return this;
    }

    @Override
    public AppiumDriverController navigateBack() {
        appiumDriver.navigate().back();
        return this;
    }

    @Override
    public String getCurrentActivity() {
        return appiumDriver.currentActivity();
    }
}
