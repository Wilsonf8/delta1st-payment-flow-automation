package ui.elementrecognition.googleelementrecognition;

import com.google.cloud.vision.v1.EntityAnnotation;
import io.appium.java_client.AppiumDriver;
import logger.Logger;
import net.sourceforge.tess4j.Word;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.elementrecognition.OcrHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleText {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public GoogleText(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public GoogleText click(String word) {

        EntityAnnotation found = null;
        try {
            GoogleOcrHelper ocrHelper = new GoogleOcrHelper();
            found = ocrHelper.waitForText(driver, word, 10, 500);
            boolean foundAndClicked = ocrHelper.tapWord(driver, found);
            if (foundAndClicked){Logger.logIndented("Clicked on " + word);}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }
    public GoogleText enter(String textToEnter) {

        Map<String, Object> args = new HashMap<>();
        args.put("command", "input");
        args.put("args", List.of("text", escapeForAdb(textToEnter)));
        driver.executeScript("mobile: shell", args);
        return this;
    }
    private String escapeForAdb(String s) {
        return s
                .replace(" ", "%s")
                .replace("&", "\\&")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("<", "\\<")
                .replace(">", "\\>")
                .replace("|", "\\|");
    }

    public boolean isOnScreen(String target){
        EntityAnnotation OKOnScreen = null;
        try {OKOnScreen = new GoogleOcrHelper().waitForText(driver, target, 3, 300);
        } catch (Exception e) {throw new RuntimeException(e);}
        if (OKOnScreen != null){return true;}
        return false;
    }


}
