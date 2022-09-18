package appium.sample.code;

public class MultiTouchSequence {
	public static void performMultiTouch(AppiumDriver<MobileElement> driver, MobileElement element1,MobileElement element2,MobileElement element3,MobileElement element4){
		try {
			TouchAction actionOne = new TouchAction(driver);
			actionOne.press(element1);
			actionOne.moveTo(element2);
			actionOne.release();
				
			TouchAction actionTwo = new TouchAction(driver);
			actionTwo.press(element3);
			actionTwo.moveTo(element4);
			actionTwo.release();
				
			MultiTouchAction action = new MultiTouchAction(driver);
			action.add(actionOne);
			action.add(actionTwo);
			action.perform();
		}
		catch (Exception e) {
			Log.fail("Unable to perform Multi Touch Operation" + e.getMessage(), driver);
		} 
	}
}
