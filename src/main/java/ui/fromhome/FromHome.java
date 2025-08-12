package ui.fromhome;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import logger.Logger;
import net.sourceforge.tess4j.Word;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.elementrecognition.OcrHelper;
import ui.login.Login;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FromHome {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public FromHome(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public FromHome goTo(String word) {

        Word found = null;
        try {
            OcrHelper ocrHelper = new OcrHelper();
            found = ocrHelper.waitForText(driver, word, 10, 500);
            boolean foundAndClicked = ocrHelper.tapWord(driver, found);
            if (foundAndClicked){Logger.logIndented("On " + word);}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }


}
