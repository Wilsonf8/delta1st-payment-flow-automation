package ui.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public final class AppiumDriverFactory {
    private AppiumDriverFactory() {}

    public static AppiumDriver createAndroid(DeviceProfile p, String serverUrl) {
        UiAutomator2Options opts = new UiAutomator2Options()
                .setDeviceName(p.getDeviceName())
                .setPlatformVersion(p.getPlatformVersion())
                .setAutomationName("UiAutomator2")
                .amend("appium:newCommandTimeout", 120);

        if (p.getAppPackage() != null) opts.setAppPackage(p.getAppPackage());
        if (p.getAppActivity() != null) opts.setAppActivity(p.getAppActivity());
        if (p.getAppPath() != null) opts.setApp(p.getAppPath().toString());

        try {
            AndroidDriver driver = new AndroidDriver(new URL(serverUrl), opts);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // prefer explicit waits
            return driver;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad Appium server URL: " + serverUrl, e);
        }
    }
}


