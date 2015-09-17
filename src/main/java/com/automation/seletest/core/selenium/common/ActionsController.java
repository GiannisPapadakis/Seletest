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
package com.automation.seletest.core.selenium.common;


public interface ActionsController<T extends ActionsController> {

    /**
     * Clicks in the middle of the given element. Equivalent to: Actions.moveToElement(onElement).click()
     * @param locator Object locator ca be String or WebElement
     * @return instance of ActionBuilder
     */
    T click(Object locator);

    /**
     * Moves the mouse to the middle of the element
     * @param locator Object locator ca be String or WebElement
     * @return instance of ActionBuilder
     */
    T mouseOver(Object locator);

    /**
     * Performs a modifier key release.
     * Releasing a non-depressed modifier key will yield undefined behaviour.
     * @param key keyInfo event
     * @return instance of ActionBuilder
     */
    T mouseUp(KeyInfo key);

    /**
     * Performs a modifier key press.
     * Does not release the modifier key - subsequent interactions may assume it's kept pressed.
     * Note that the modifier key is never released implicitly - either keyUp(theKey) or sendKeys(Keys.NULL)
     *  must be called to release the modifier.
     * @param key KeInfo event
     * @return instance of ActionBuilder
     */
    T mouseDown(KeyInfo key);

    /**
     * Move to element first and then perform mouseDown
     * @param locator Object locator ca be String or WebElement
     * @param key KeyInfo event
     * @return instance of ActionBuilder
     */
    T mouseDown(Object locator, KeyInfo key);

    /**
     * Move to element and then perform mouseUp
     * @param locator Object locator ca be String or WebElement
     * @param key KeyInfo event
     * @return instance of ActionBuilder
     */
     T mouseUp(Object locator, KeyInfo key);

    /**
     * Clicks (without releasing) in the middle of the given element. This is equivalent to: Actions.moveToElement(onElement).clickAndHold()
     * @param locator of WebElement to click and hold
     * @return instance of ActionBuilder
     */
     T clickAndHold(Object locator);

    /**
     * Performs the sequence of webSync represented by this instance
     * @return instance of ActionBuilder
     */
     T performActions();

    /**
     * Performs the sequence of TouchAction represented by this instance
     * @return instance of ActionBuilder
     */
     T performTouchActions();

    /**
     * Tap on WebElement in native apps
     * @param locator element to tap
     * @return instance of ActionBuilder
     */
     T tap(Object locator);

    /**
     * Tap an element, offset from upper left corner
     * @param locator locator element to tap
     * @param x x offset
     * @param y y offset
     * @return instance of ActionBuilder
     */
     T tap(Object locator,int x, int y);

    /**
     * Tap on specific location in screen
     * @param x x coordinate
     * @param y y coordinate
     * @return instance of ActionBuilder
     */
     T tap(int x, int y);

    /**
     * Press on an absolute position on the screen
     * @param x x coordinate
     * @param y y coordinate
     * @return ActionsBuilder
     */
     T press(int x, int y);

    /**
     * Press on WebElement in native apps
     * @param locator element to press
     * @return instance of ActionBuilder
     */
     T press(Object locator);

    /**
     * Press an element, offset from upper left corner
     * @param locator locator element to press
     * @param x x offset
     * @param y y offset
     * @return instance of ActionBuilder
     */
     T press(Object locator, int x, int y);

    /**
     * Drags elements from one location to another
     * @param draglocator Object tto drag
     * @param droplocator Object to drop
     * @return instance of ActionBuilder
     */
     T dragndrop(Object draglocator, Object droplocator);

    /**
     * Press a keyboard key
     * @param key KeyInfo event
     * @return instance of ActionBuilder
     */
     T press(KeyInfo key);


}
