package appium.sample.code;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumDriverFactory {

private static Logger Logger;
private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
  
public static AppiumDriver<MobileElement> getDriverInstance() {
   return get();
}
  
private static AppiumDriver<MobileElement> get() {
	AppiumDriver<MobileElement> driver = null;
	try {
		driver = getAppiumDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	} 
	catch (Exception e) {
		//Log.fail("Could not create a driver session");	
	} 
   
	return driver;
}
  
private static AppiumDriver<MobileElement> getAppiumDriver() throws MalformedURLException {
	AppiumDriver<MobileElement> driver = null;
	DesiredCapabilities capabilities = getDesiredCapabilities();
	//Log.testCaseInfo("Initialize the Appium driver...");
	try {
		String appiumURL = "http://" + configProperty.getProperty("hostName") + ":" + configProperty.getProperty("portName") + "/wd/hub";
		capabilities = getDesiredCapabilities();
		switch (configProperty.getProperty("platformName").toLowerCase()) 
		{
			case "android":
				//Log.testCaseInfo("Initializing Android Driver Instance using Appium Driver");
				driver = new AndroidDriver<>(new URL(appiumURL), capabilities);
				break;
					
			case "ios":
				//Log.testCaseInfo("Initializing IOS Driver Instance using Appium Driver");
                		driver = new IOSDriver<>(new URL(appiumURL), capabilities);
                		break;
		}		
	} 
		
	catch (Exception e) {
		//Log.fail("Unable to create driver session with given URL and DesiredCapabilities : " + e.getMessage());
	}
		
	return driver;
}
	
private static DesiredCapabilities getDesiredCapabilities() throws MalformedURLException {
	DesiredCapabilities capabilities = new DesiredCapabilities();
	if(configProperty.getProperty("platformName").equals("iOS"))
	{
		//Log.testCaseInfo("Setting Desired Capabilities for iOS Driver");
		if(configProperty.getProperty("platformName")!=null)
			capabilities.setCapability("platformName", configProperty.getProperty("platformName"));
			//Log.testCaseInfo("Platform Name set as : " + configProperty.getProperty("platformName"));
				
		if(configProperty.getProperty("platformVersioniOS")!=null)
			capabilities.setCapability("platformVersion", configProperty.getProperty("platformVersioniOS"));
			//Log.testCaseInfo("Platform Version set as : " + configProperty.getProperty("platformVersioniOS"));
				
		if(configProperty.getProperty("udidiOS")!=null)
			capabilities.setCapability("deviceName", configProperty.getProperty("udidiOS"));
			//Log.testCaseInfo("DeviceName Name set as : " + configProperty.getProperty("udidiOS"));
				
			capabilities.setCapability("udid", configProperty.getProperty("udidiOS"));
			//Log.testCaseInfo("UDID set as : " + configProperty.getProperty("udidiOS"));
				
		if (configProperty.getProperty("appPathiOS") != null) {
			capabilities.setCapability(MobileCapabilityType.APP, configProperty.getProperty("appPathiOS"));
			//Log.testCaseInfo("APP path iOS set as : " + configProperty.getProperty("appPathiOS"));	
		} 
				
	}
				
	if(configProperty.getProperty("platformName").equals("Android"))
	{
		//Log.testCaseInfo("Setting Desired Capabilities for Android Driver");
		
		if(configProperty.getProperty("deviceName")!=null)
			capabilities.setCapability("deviceName", configProperty.getProperty("deviceName"));
			//Log.testCaseInfo("DeviceName Name set as : " + configProperty.getProperty("deviceName"));
		
		if(configProperty.getProperty("platformName")!=null)
			capabilities.setCapability("platformName", configProperty.getProperty("platformName"));
			//Log.testCaseInfo("Platform Name set as : " + configProperty.getProperty("platformName"));
					
		if(configProperty.getProperty("platformVersion")!=null)
			capabilities.setCapability("platformVersion", configProperty.getProperty("platformVersion"));
			//Log.testCaseInfo("Platform Version set as : " + configProperty.getProperty("platformVersion"));
		
		if(configProperty.getProperty("installedAndroidApp").equals("false"))
		{
			//Log.testCaseInfo("Setting android app apk path in Desired Capability.. ");
			
			if(configProperty.getProperty("deviceName")!=null) {
				//Log.testCaseInfo("Device Name set as : " + configProperty.getProperty("deviceName"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, configProperty.getProperty("deviceName"));
			}
				
			if(configProperty.getProperty("appName")!=null) {
				File f = new File("src");
				File app = new File(f,configProperty.getProperty("appName"));
				capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
				//Log.testCaseInfo("App Path set as : " + app.getAbsolutePath() );
			}		
		}
		else 
		{
			if(configProperty.getProperty("appPackage")!=null) {
				//Log.testCaseInfo("App Package set as : " + configProperty.getProperty("appPackage"));
				capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, configProperty.getProperty("appPackage"));
			}
					
			if(configProperty.getProperty("appActivity")!=null) {
				//Log.testCaseInfo("App Activity set as : " + configProperty.getProperty("appActivity"));
				capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, configProperty.getProperty("appActivity"));		
			}			
		}
			
	//Log.testCaseInfo("Command Time Out set as : " + configProperty.getProperty("commandTimeOut"));
	capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, configProperty.getProperty("commandTimeOut"));		
		} 
return capabilities;
}

}