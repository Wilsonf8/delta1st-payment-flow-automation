package ui.base;


import io.appium.java_client.AppiumDriver;
import logger.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Creates a fresh Appium session per test method. */
public abstract class BaseUi {
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    protected DeviceProfile deviceProfile() {
        // Override in subclasses or set via -Dsystem properties if you like
        return DeviceProfile.builder()
                .deviceName(System.getProperty("deviceName", "emulator-5554"))
                .platformVersion(System.getProperty("platformVersion", "16"))
                .appPath(Path.of(System.getProperty("app", "/Users/wilsonflores/WAS/apks/SS04_Service_1.17.0.apk")))
                .build();
    }

    protected String appiumServerUrl() {
        return System.getProperty("appium.url", "http://127.0.0.1:4723");
    }

    @BeforeEach
    void setUp(TestInfo info) {
        DeviceProfile profile = deviceProfile();
        driver = AppiumDriverFactory.createAndroid(profile, appiumServerUrl());
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Logger.logHeading(info.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo info) {
        try {
            if (driver != null) {
                String name = safeName(info);
            }
        } catch (Throwable ignored) {
            // best-effort for artifacts
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

//    private void saveScreenshot(String name) {
//        if (!(driver instanceof TakesScreenshot)) return;
//        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        try {
//            Path dir = Path.of("target", "screenshots");
//            Files.createDirectories(dir);
//            Path file = dir.resolve(name + ".png");
//            Files.write(file, png);
//            System.out.println("Saved screenshot: " + file.toAbsolutePath());
//        } catch (Exception e) {
//            // ignore
//        }
//    }

    private String safeName(TestInfo info) {
        String method = info.getTestMethod().map(m -> m.getName()).orElse("unknown");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return method + "_" + time;
    }
}

