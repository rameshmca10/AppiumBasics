package appium.sample.code;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class WaitForElement {

	public static boolean waitForElement(AppiumDriver<MobileElement> driver,  MobileElement element, int maxWait) {
	 	boolean statusOfElementToBeReturned = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
		try {
		    WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
		    if (null != waitElement && waitElement.isDisplayed() && waitElement.isEnabled())
		    {
		    	statusOfElementToBeReturned = true;
		    //	Log.event("Element is displayed: " +  element.toString());
		    }
		} 
		catch (Exception ex) {
		    	statusOfElementToBeReturned = false;
		}
		    return statusOfElementToBeReturned;
		    //return statusOfFile
	}

}
