package appium.sample.code;

import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class DoubleTap {
	public static void performDoubleTap(AppiumDriver<MobileElement> driver, MobileElement element) {
		try {
			TouchActions action = new TouchActions(driver);
			action.doubleTap(element);
			action.perform();
		} catch (Exception e) {
			//Log.fail("Unable to perform Double Tap Operation" + e.getMessage(), driver);
		}
	}
}
