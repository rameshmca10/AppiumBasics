package appium.sample.code;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSElement;

public class TouchAction {

	@Test
	public void swipeTest() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		IOSElement slider = webDriverWait.until(driver1 -> driver.findElementByClassName("XCUIElementTypeSlider"));
		Dimension size = slider.getSize();
		ElementOption press = element(slider, size.width / 2 + 2, size.height / 2);
		ElementOption move = element(slider, 1, size.height / 2);
		TouchAction swipe = new TouchAction(driver).press(press).waitAction(waitOptions(ofSeconds(2))).moveTo(move)
				.release();
		swipe.perform();
		assertEquals("0%", slider.getAttribute("value"));
	}

	@Test
	public void dragNDropByElementTest() {
		Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
		driver.startActivity(activity);
		WebElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
		WebElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));
		WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
		assertEquals("Drag text not empty", "", dragText.getText());
		TouchAction dragNDrop = new TouchAction(driver).longPress(element(dragDot1)).moveTo(element(dragDot3))
				.release();
		dragNDrop.perform();
		assertNotEquals("Drag text empty", "", dragText.getText());
	}

	@Test
	public void tapActionTestByElement() throws Exception {
		Activity activity = new Activity("io.appium.android.apis", ".view.ChronometerDemo");
		driver.startActivity(activity);
		AndroidElement chronometer = driver.findElementById("io.appium.android.apis:id/chronometer");
		TouchAction startStop = new TouchAction(driver)
				.tap(tapOptions().withElement(element(driver.findElementById("io.appium.android.apis:id/start"))))
				.waitAction(waitOptions(ofSeconds(2)))
				.tap(tapOptions().withElement(element(driver.findElementById("io.appium.android.apis:id/stop"))));
		startStop.perform();
		String time = chronometer.getText();
		assertNotEquals(time, "Initial format: 00:00");
		Thread.sleep(2500);
		assertEquals(time, chronometer.getText());
	}

	@Test
	public void tapActionTestByCoordinates() throws Exception {
		Activity activity = new Activity("io.appium.android.apis", ".view.ChronometerDemo");
		driver.startActivity(activity);
		AndroidElement chronometer = driver.findElementById("io.appium.android.apis:id/chronometer");
		Point center1 = driver.findElementById("io.appium.android.apis:id/start").getCenter();
		TouchAction startStop = new TouchAction(driver).tap(point(center1.x, center1.y))
				.tap(element(driver.findElementById("io.appium.android.apis:id/stop"), 5, 5));
		startStop.perform();
		String time = chronometer.getText();
		assertNotEquals(time, "Initial format: 00:00");
		Thread.sleep(2500);
		assertEquals(time, chronometer.getText());
	}

	public void longPress() {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.longPress(gestureBox).perform();
	}

	@ReplayOnError
	public void pinch() {
		PerformsTouchActions performTouchActions = checkForMobile();
		MobileElement mobElement = (MobileElement) getUnderlyingElement(element);

		// code taken from appium
		MultiTouchAction multiTouch = new MultiTouchAction(performTouchActions);

		Point upperLeft = mobElement.getLocation();
		Point center = mobElement.getCenter();
		int yOffset = center.getY() - upperLeft.getY();

		TouchAction<?> action0 = createTouchAction()
				.press(ElementOption.element(mobElement, center.getX(), center.getY() - yOffset))
				.moveTo(ElementOption.element(mobElement)).release();
		TouchAction<?> action1 = createTouchAction()
				.press(ElementOption.element(mobElement, center.getX(), center.getY() + yOffset))
				.moveTo(ElementOption.element(mobElement)).release();

		multiTouch.add(action0).add(action1).perform();
	}

	public void nTaps(final int times) {
		perform(format("Performing [%d] taps on", times), e -> {
			final Point center = e.getCenter();
			for (int index = 0; index < times; index++) {
				this.touch.press(PointOption.point(center.getX(), center.getY())).release().perform();
			}
		});
	}

	public void longPress() {
		perform("Performing long press on",
				e -> this.touch.waitAction(WaitOptions.waitOptions(ofMillis(this.afterTap)))
						.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(e)))
						.waitAction(WaitOptions.waitOptions(ofMillis(this.afterTap))).perform());
	}

	public void tap() {
		perform("Tapping on",
				e -> this.touch.waitAction(WaitOptions.waitOptions(ofMillis(this.beforeTap)))
						.tap(TapOptions.tapOptions().withElement(ElementOption.element(e)))
						.waitAction(WaitOptions.waitOptions(ofMillis(this.afterTap))).perform());
	}

	@ReplayOnError(replayDelayMs = 1000)
	public void tap() {
		findElement();
		createTouchAction()
				.moveTo(PointOption.point(detectedObjectRectangle.x + detectedObjectRectangle.width / 2,
						detectedObjectRectangle.y + detectedObjectRectangle.height / 2))
				.tap(TapOptions.tapOptions().withTapsCount(1)).perform();
	}

	@ReplayOnError
	public void tap(int fingers, int duration) {
		PerformsTouchActions performTouchActions = checkForMobile();
		MobileElement mobElement = (MobileElement) getUnderlyingElement(element);

		// code from appium
		MultiTouchAction multiTouch = new MultiTouchAction(performTouchActions);
		for (int i = 0; i < fingers; i++) {
			TouchAction<?> tap = createTouchAction();
			multiTouch.add(tap.press(ElementOption.element(mobElement))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration))).release());
		}
		multiTouch.perform();
	}

	public void cancel() {
		ActionParameter action = new ActionParameter("cancel");
		parameterBuilder.add(action);
		this.perform();
	}

	@Test
	public void multiTouchTest() {
		MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
		MobileElement e2 = driver.findElementByAccessibilityId("show alert");
		TouchAction tap1 = new TouchAction(driver).tap(tapOptions().withElement(element(e)));
		TouchAction tap2 = new TouchAction(driver).tap(tapOptions().withElement(element(e2)));
		new MultiTouchAction(driver).add(tap1).add(tap2).perform();
		WebDriverWait waiting = new WebDriverWait(driver, 10000);
		assertNotNull(waiting.until(alertIsPresent()));
		driver.switchTo().alert().accept();
	}

	protected TouchAction<?> createTouchAction() {
		String platform = SeleniumTestsContextManager.getThreadContext().getPlatform();
		PerformsTouchActions performTouchActions = checkForMobile();
		if (platform.toLowerCase().startsWith("android")) {
			TouchAction<AndroidTouchAction> touchAction = new TouchAction<>(performTouchActions);
			return touchAction;
		} else if (platform.toLowerCase().startsWith("ios")) {
			TouchAction<IOSTouchAction> touchAction = new TouchAction<>(performTouchActions);
			return touchAction;
		} else {
			throw new ConfigurationException(String.format("%s platform is not supported", platform));
		}
	}

	@Test
	public void tapTest() {
		IOSElement intA = driver.findElementById("IntegerA");
		IOSElement intB = driver.findElementById("IntegerB");
		intA.clear();
		intB.clear();
		intA.sendKeys("2");
		intB.sendKeys("4");
		MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
		new TouchAction(driver).tap(tapOptions().withElement(element(e))).perform();
		assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
	}

	@Override
	public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
		final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class)
				.orElseThrow(() -> new AtlasException("AppiumDriver is missing")).getValue();

		final TouchAction action = new TouchAction(driver);
		final Point location = ((WebElement) proxy).getLocation();
		final Dimension size = ((WebElement) proxy).getSize();
		final int x = location.getX() + size.width / 2;
		final int y = location.getY() + size.height / 2;
		action.longPress(longPressOptions().withDuration(LONG_TAP_DURATION)
				.withPosition(new PointOption().withCoordinates(x, y))).perform();
		return proxy;
	}

	}

	@ReplayOnError
	public void zoom() {
		PerformsTouchActions performTouchActions = checkForMobile();
		MobileElement mobElement = (MobileElement) getUnderlyingElement(element);

		MultiTouchAction multiTouch = new MultiTouchAction(performTouchActions);
		Point upperLeft = mobElement.getLocation();
		Point center = mobElement.getCenter();
		int yOffset = center.getY() - upperLeft.getY();
		TouchAction<?> action0 = createTouchAction().press(PointOption.point(center.getX(), center.getY()))
				.moveTo(ElementOption.element(mobElement, center.getX(), center.getY() - yOffset)).release();
		TouchAction<?> action1 = createTouchAction().press(PointOption.point(center.getX(), center.getY()))
				.moveTo(ElementOption.element(mobElement, center.getX(), center.getY() + yOffset)).release();
		multiTouch.add(action0).add(action1).perform();
	}

	public void cancel() {
		ActionParameter action = new ActionParameter("cancel");
		parameterBuilder.add(action);
		this.perform();
	}

	@Test
	public void pressByElementTest() {
		Activity activity = new Activity("io.appium.android.apis", ".view.Buttons1");
		driver.startActivity(activity);
		new TouchAction(driver).press(element(driver.findElementById("io.appium.android.apis:id/button_toggle")))
				.waitAction(waitOptions(ofSeconds(1))).release().perform();
		assertEquals("ON", driver.findElementById("io.appium.android.apis:id/button_toggle").getText());
	}

	@Test
	public void dragNDropByCoordinatesTest() {
		Activity activity = new Activity("io.appium.android.apis", ".view.DragAndDropDemo");
		driver.startActivity(activity);
		AndroidElement dragDot1 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
		AndroidElement dragDot3 = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3"));
		WebElement dragText = driver.findElement(By.id("io.appium.android.apis:id/drag_text"));
		assertEquals("Drag text not empty", "", dragText.getText());
		Point center1 = dragDot1.getCenter();
		Point center2 = dragDot3.getCenter();
		TouchAction dragNDrop = new TouchAction(driver).longPress(point(center1.x, center1.y))
				.moveTo(point(center2.x, center2.y)).release();
		dragNDrop.perform();
		assertNotEquals("Drag text empty", "", dragText.getText());
	}

}
