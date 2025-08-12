package ui.login;

import io.appium.java_client.AppiumDriver;
import logger.Logger;
import net.sourceforge.tess4j.Word;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.base.BaseUi;
import ui.elementrecognition.OcrHelper;
import ui.elementrecognition.Text;
import ui.elementrecognition.googleelementrecognition.GoogleText;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;

// Preconditions: On Get Started Page
// Postconditions: On Home Page
public class Login {

    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final String subdomain;
    private final String pin;

    public Login(AppiumDriver driver, WebDriverWait wait, String subdomain, String pin) {
        this.driver = driver;
        this.wait = wait;
        this.subdomain = subdomain;
        this.pin = pin;
    }
    public Login enterSubdomain(){
        // If "OK" is on the screen, click it
//        new Text(driver, wait).isOnScreen("ok");
        if (new GoogleText(driver, wait).isOnScreen("ok")){new GoogleText(driver, wait).click("ok");}

        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("com.o4partners.service.staging:id/et_sub_domain")));
        WebElement subdomainTextBox = driver.findElement(new By.ById("com.o4partners.service.staging:id/et_sub_domain"));
        subdomainTextBox.sendKeys(subdomain);
        return this;
    }
    public Login clickGetStarted(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("com.o4partners.service.staging:id/btn_submit")));
        WebElement getStartedButton = driver.findElement(new By.ById("com.o4partners.service.staging:id/btn_submit"));
        getStartedButton.click();
        return this;
    }
    public Login clickLoginWithPin(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("com.o4partners.service.staging:id/btn_pin")));
        WebElement enterPinButton = driver.findElement(new By.ById("com.o4partners.service.staging:id/btn_pin"));
        enterPinButton.click();
        return this;
    }
    public Login enterPin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("com.o4partners.service.staging:id/otp_view")));
        for (int i = 0; i < pin.length(); i++) {
            String buttonId = MessageFormat.format("com.o4partners.service.staging:id/btn_{0}", pin.charAt(i));
            driver.findElement(new By.ById(buttonId)).click();
        }

        // If "cancel" is on the screen, click it
        if (new GoogleText(driver, wait).isOnScreen("cancel")){new GoogleText(driver, wait).click("cancel");}

        return this;
    }



}

