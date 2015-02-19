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

package com.automation.seletest.core.listeners;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.AnnotationUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestClass;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.utilities.PerformanceUtils;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.PostConfiguration;
import com.automation.seletest.core.testNG.PreConfiguration;
import com.automation.seletest.core.testNG.assertions.SoftAssert;

@Slf4j
@SuppressWarnings("unchecked")
public class InitListener implements IInvokedMethodListener{

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		PreConfiguration preconfigure = null;
		ITestClass testClass=method.getTestMethod().getTestClass();
		Class<?> webClass=testClass.getRealClass();

		if(testResult.getMethod().isBeforeClassConfiguration() && testResult.getMethod().getMethodName().equalsIgnoreCase("beforeClass")) {
			preconfigure=webClass.getAnnotation(PreConfiguration.class);
		}

		//Set session as testNG attribute for after configuration methods
		try{
			testResult.setAttribute("session", SessionContext.session());}
		catch(Exception ex) {}

		if(method.getTestMethod().isTest()){
			log.debug("Set assertion type parameter for test method: {}!!!", method.getTestMethod().getMethodName());
			SeleniumTest seleniumTest=AnnotationUtils.findAnnotation(method.getTestMethod().getConstructorOrMethod().getMethod(), SeleniumTest.class);
			ApplicationContextProvider.getApplicationContext().getBean(ApplicationContextProvider.class).publishTestNGEvent(seleniumTest, "Initialize objects for the @Test method: "+method.getTestMethod().getMethodName()); 
			preconfigure = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(PreConfiguration.class);
		}

		//Execute Method from Page Object or Page Facade prior to @Test execution
		if(preconfigure!=null && method.getTestMethod().getCurrentInvocationCount()==0) {
			log.debug("Preconfiguration steps will be executed now for @Test {} !!!",method.getTestMethod().getMethodName());
			executionConfiguration(preconfigure);
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		PostConfiguration postconfigure = null;
		ITestClass testClass=method.getTestMethod().getTestClass();
		Class<?> webClass=testClass.getRealClass();

		if(testResult.getMethod().isAfterClassConfiguration() && testResult.getMethod().getMethodName().equalsIgnoreCase("afterClass")) {
			postconfigure=webClass.getAnnotation(PostConfiguration.class);
		}

		try{
			testResult.setAttribute("session", SessionContext.session());}
		catch(Exception ex){}

		if(method.getTestMethod().isTest()){

			//Wait for verifications to complete before finishing @Test (only for SoftAssert)
			if(SessionContext.getSession().getAssertion().getAssertion() instanceof SoftAssert){
				for(Future<?> a : (ArrayList<Future<?>>) SessionContext.getSession().getVerifications()){
					while(!a.isDone()){
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							log.error("Exception waiting future task to complete: "+e);
						}
					}
				}
				log.debug("Async verifications finished for @Test {}",method.getTestMethod().getMethodName());
			}

			postconfigure = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(PostConfiguration.class);
			PerformanceUtils perf=SessionContext.session().getPerformance();
			SessionControl.verifyController().assertAll();
			if(perf!=null) {
				perf.getPerformanceData(perf.getServer());
				perf.writePerformanceData(new File("./target/surefire-reports/logs/"+testResult.getName()+".har").getAbsolutePath(), perf.getHar());
				perf.stopServer(perf.getServer());
				log.debug("Performance data collected for test method: {} !!!",method.getTestMethod().getMethodName());
			}
		}

		//Execute Method from Page Object or Page Facade prior to @Test execution
		if(postconfigure!=null && method.getTestMethod().getCurrentInvocationCount()==0) {
			log.debug("Postconfiguration steps will be executed now for @Test {} !!!",method.getTestMethod().getMethodName());
			executionConfiguration(postconfigure);
		}
	}

	/**
	 * Execute PreConfiguration
	 * @param configure Object for custom annotation PreConfigure-PostConfigure
	 * @throws SkipException
	 */
	private void executionConfiguration(Object configure) throws SkipException{
		try{
			String method="";
			Class<?>  classRef=null;
			if(configure instanceof PreConfiguration){
				method=((PreConfiguration)configure).method();
				classRef=((PreConfiguration)configure).classReference();
			} else if(configure instanceof PostConfiguration){
				method=((PostConfiguration)configure).method();
				classRef=((PostConfiguration)configure).classReference();
			}

			Constructor<?> constructor = ApplicationContextProvider.getApplicationContext().getBean(classRef).getClass().getDeclaredConstructors()[0];
			constructor.setAccessible(true);
			Object innerObject = constructor.newInstance();
			Method[] _methods = ApplicationContextProvider.getApplicationContext().getBean(classRef).getClass().getDeclaredMethods();
			for(Method _method:_methods){
				if(_method.getName().equals(method)){
					_method.setAccessible(true);
					_method.invoke(innerObject);
				}
			}

			log.debug("{} steps executed successfully for!!!", configure instanceof PreConfiguration ? "Preconfiguration" : "Postconfiguration");
		} catch (Exception e) {
			log.error("Skip the test because of failure to preconfiguration with exception "+e.getLocalizedMessage()+"!!");
			throw new SkipException("Skip the test because of failure to configuration of test with message: "+e.getCause().toString()+"!!");
		}
	}
}
