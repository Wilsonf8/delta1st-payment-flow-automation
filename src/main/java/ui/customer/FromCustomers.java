package ui.customer;

import com.google.cloud.vision.v1.EntityAnnotation;
import io.appium.java_client.AppiumDriver;
import logger.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.elementrecognition.GestureHelper;
import ui.elementrecognition.OcrHelper;
import ui.elementrecognition.googleelementrecognition.GoogleOcrHelper;
import ui.elementrecognition.googleelementrecognition.GoogleText;

import java.awt.*;

public class FromCustomers {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    public FromCustomers(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public FromCustomers clickPlus(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("com.o4partners.service.staging:id/fab_add")));
        WebElement plusButton = driver.findElement(new By.ById("com.o4partners.service.staging:id/fab_add"));
        plusButton.click();
        return this;
    }
    public FromCustomers deleteCustomer(String firstAndLastName){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityAnnotation onScreen = null;
        try {onScreen = new GoogleOcrHelper().waitForText(driver, firstAndLastName, 5, 300);System.out.println(onScreen);}
        catch (Exception e) {throw new RuntimeException(e);}

        if (onScreen != null){
            Point center = new GoogleOcrHelper().getWordCenterCoordinates(onScreen);
            GestureHelper.swipeLeftFrom(driver, center.x, center.y, 300, 300);

            new GoogleText(driver, wait).click("delete").click("yes");
            Logger.logIndented(firstAndLastName + " deleted");

        }else{
            Logger.logIndented(firstAndLastName + " not found");
        }

        return this;
    }


}
