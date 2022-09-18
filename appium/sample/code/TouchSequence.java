package appium.sample.code;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TouchSequence {
	public static void performTouchSequence(AppiumDriver<MobileElement> driver, MobileElement element1,MobileElement element2){
		try {
			TouchAction action = new TouchAction();
			action.press(element1);
			action.moveTo(element2);
			action.release();
			action.perform();
		}
		catch (Exception e) {
			Log.fail("Unable to perform Touch Sequence Operation" + e.getMessage());
		} 
	}
}
