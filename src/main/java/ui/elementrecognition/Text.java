package ui.elementrecognition;

import io.appium.java_client.AppiumDriver;
import logger.Logger;
import net.sourceforge.tess4j.Word;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Text {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public Text(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public Text click(String word) {

        Word found = null;
        try {
            OcrHelper ocrHelper = new OcrHelper();
            found = ocrHelper.waitForText(driver, word, 10, 500);
            boolean foundAndClicked = ocrHelper.tapWord(driver, found);
            if (foundAndClicked){Logger.logIndented("Clicked on " + word);}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }
    public Text enter(String textToEnter) {

        Map<String, Object> args = new HashMap<>();
        args.put("command", "input");
        args.put("args", List.of("text", escapeForAdb(textToEnter)));
        driver.executeScript("mobile: shell", args);
        Logger.logIndented("Entered " + textToEnter);
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
        Word OKOnScreen = null;
        try {OKOnScreen = new OcrHelper().waitForText(driver, "ok", 3, 300);
        } catch (Exception e) {throw new RuntimeException(e);}
        if (OKOnScreen != null){return true;}
        return false;
    }


}
