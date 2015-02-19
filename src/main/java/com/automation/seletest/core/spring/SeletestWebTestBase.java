
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
package com.automation.seletest.core.spring;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.automation.seletest.core.listeners.InitListener;
import com.automation.seletest.core.selenium.configuration.ConfigurationDriver;
import com.automation.seletest.core.selenium.threads.SessionContext;

/**
 * This class serves as the Base Class for Web Test Preparation
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@Listeners(InitListener.class)
@ContextConfiguration(classes=ConfigurationDriver.class)
public class SeletestWebTestBase extends AbstractTestNGSpringContextTests {
	
	@Autowired
	Environment env;

    /**Μessage initialize new session*/
    private static final String INITIALIZE_SESSION="Event for initializing Session occured at: {} !!!";

    /**Message for exception during application context load*/
    private static final String ERROR_IOC="Error during initializing spring container ";

    @BeforeSuite(alwaysRun = true)
    @BeforeClass(alwaysRun = true)
    @BeforeTest(alwaysRun = true)
    @Override
    protected void springTestContextPrepareTestInstance() throws Exception {
        prepareTest();
    }

    @BeforeSuite(alwaysRun = true)
    protected void suiteSettings(ITestContext ctx) throws Exception {
        log.debug("Suite : "+ctx.getCurrentXmlTest().getSuite().getName()+" started at: {}",ctx.getStartDate());
        applicationContext.getBean(ThreadPoolTaskExecutor.class).getThreadPoolExecutor().allowCoreThreadTimeOut(true);
 
    }

    @BeforeTest(alwaysRun = true)
    protected void beforeTest(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0 || ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){
            log.debug("############### Initialize session upon parallel level: <<\"parallel={}\">> ###############", ctx.getCurrentXmlTest().getParallel());
            initializeSession(ctx);
        }
    }

    @BeforeClass(alwaysRun = true)
    protected void beforeClass(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){
            log.debug("############### Initialize session upon parallel level: <<\"parallel={}\">> ###############", ctx.getCurrentXmlTest().getParallel());
            initializeSession(ctx);
        }
    }

    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){
            log.debug("############### Initialize session: <<\"parallel={}\">> ###############", ctx.getCurrentXmlTest().getParallel());
            initializeSession(ctx);
        }
    }

    @AfterClass(alwaysRun = true)
    protected void cleanSessionOnClass(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("classes")==0){
            log.debug("############### Clean session on @AfterClass ###############");
            SessionContext.cleanSession();
        }
    }

    @AfterTest(alwaysRun = true)
    public void cleanSessionOnTest(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("false")==0||
                ctx.getCurrentXmlTest().getParallel().compareTo("tests")==0){
            log.debug("############### Clean session on @AfterTest ###############");
            SessionContext.cleanSession();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void cleanSessionOnMethod(ITestContext ctx) throws Exception {
        if(ctx.getCurrentXmlTest().getParallel().compareTo("methods")==0){
            log.debug("############### Clean session on @AfterMethod ###############");
            SessionContext.cleanSession();
        }
    }

    @AfterSuite(alwaysRun = true)
    protected void cleanSuite() throws Exception {
          SessionContext.cleanSessionsFromStack();
    }


    private void initializeSession(ITestContext ctx) throws Exception{
        ApplicationContextProvider publisher = applicationContext.getBean(ApplicationContextProvider.class);
        publisher.publishInitializationEvent(INITIALIZE_SESSION, ctx.getCurrentXmlTest().getParameter(env.getProperty("host")),ctx.getCurrentXmlTest().getParameter(env.getProperty("performance"))!=null && Boolean.parseBoolean(ctx.getCurrentXmlTest().getParameter(env.getProperty("performance"))) ? true : false,ctx);
    }

    private void prepareTest() throws Exception{
        try {
            if (applicationContext == null) {
                super.springTestContextPrepareTestInstance();
            }
        } catch (Exception e1) {
            log.error(ERROR_IOC+e1.getMessage());
            throw e1;
        }
    }


}
