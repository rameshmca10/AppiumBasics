package appium.sample.code;

public class SingleTap {
	public static void performSingleTap(AppiumDriver<MobileElement> driver, MobileElement element){
		try {
			TouchActions action = new TouchActions(driver);
			action.singleTap(element);
			action.perform();
		}
		catch (Exception e) {
			Log.fail("Unable to perform Single Tap Operation" + e.getMessage(), driver);
		} 
	}
}
