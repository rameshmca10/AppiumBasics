package appium.sample.code;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class WaitForElementVanish {
	public boolean waitForElementToVanish(AppiumDriver driver, MobileElement element, int maxWait) {
		try {
			int i = 0;
			while (element.isDisplayed() && i < 3) {
				Thread.sleep(maxWait);
				i++;
			}
			return !element.isDisplayed();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
