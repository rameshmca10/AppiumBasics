package appium.sample.code;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ElementLocators {
	public static MobileElement getAnyElementByAnyLocator(AppiumDriver<MobileElement> driver, String locatorPath){
		try{
			if(locatorPath.startsWith("css_")){
				return driver.findElement(By.cssSelector(locatorPath.replace("css_", "")));
			}
		
			else if(locatorPath.startsWith("xpath_")){
				return driver.findElement(By.xpath(locatorPath.replace("xpath_", "")));
			}
			
			else if(locatorPath.startsWith("id_")){
				return driver.findElement(By.id(locatorPath.replace("id_", "")));
			}
			
			else if(locatorPath.startsWith("name_")){
				return driver.findElement(By.name(locatorPath.replace("name_", "")));
			}
			
			else if(locatorPath.startsWith("link_")){
				return driver.findElement(By.linkText(locatorPath.replace("link_", "")));
			}
			
			else if(locatorPath.startsWith("partiallink_")){
				return driver.findElement(By.partialLinkText(locatorPath.replace("partiallink_", "")));
			}
			
			else if(locatorPath.startsWith("class_")){
				return driver.findElement(By.className(locatorPath.replace("class_", "")));
			}
			
			else if(locatorPath.startsWith("tag_")){
				return driver.findElement(By.tagName(locatorPath.replace("tag_", "")));
			}
			
			else 
				return null;
			}
					
		catch (Exception e) {
			//Log.fail("Unable to get element" + e.getMessage(), driver);
			return null;
		} 
	}
}
