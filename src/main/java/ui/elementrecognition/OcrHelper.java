package ui.elementrecognition;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OcrHelper {
    private final Tesseract tesseract;

    public OcrHelper() {
        tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("/opt/homebrew/share/tessdata");
        System.setProperty("jna.library.path", "/opt/homebrew/lib");
    }

    public int textLength(String text){
        String[] textList = text.split(" ");
        return textList.length;
    }

    private BufferedImage removeGreenPixels(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));

                // Detect "green" (tweak the thresholds as needed)
                if (color.getGreen() > 120 && color.getRed() < 120 && color.getBlue() < 100) {
                    result.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    result.setRGB(x, y, color.getRGB());
                }
            }
        }

        return result;
    }

    public List<Word> detectTextFromScreenshot(AppiumDriver driver) throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screenshot);
        image = removeGreenPixels(image);

        return tesseract.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_WORD)
                .stream()
                .filter(word -> word.getConfidence() >= 0)
                .collect(Collectors.toList());
    }
    public List<String> getAllWordsOnScreen(AppiumDriver driver) throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screenshot);

        return tesseract.getWords(image, ITessAPI.TessPageIteratorLevel.RIL_WORD)
                .stream()
                .filter(word -> word.getConfidence() >= 90)
                .map(Word::getText)
                .collect(Collectors.toList());
    }

    public Word findText(AppiumDriver driver, String wordToFind, int textSize) throws Exception {
        List<Word> wordList = detectTextFromScreenshot(driver);
        for (int i=textSize-1; i < wordList.size(); i++){
            Word word = wordList.get(i);
            StringBuilder patchedText = new StringBuilder();
            for (int j=textLength(wordToFind)-1; j >= 0; j--){
                if (!patchedText.toString().equalsIgnoreCase("")){
                    patchedText.append(" ");
                }
                patchedText.append(wordList.get(i - j).getText());
            }
            if (wordToFind.equalsIgnoreCase(patchedText.toString())) {
                return word;
            }
        }

        return null;
    }
    public Word waitForText(AppiumDriver driver, String wordToFind, int timeoutSeconds, int pollIntervalMillis) throws Exception {
        int textSize = textLength(wordToFind);
        long start = System.currentTimeMillis();
        long end = start + timeoutSeconds * 1000L;

        while (System.currentTimeMillis() < end) {
            Word word = findText(driver, wordToFind, textSize);
            if (word != null) return word;

            Thread.sleep(pollIntervalMillis);
        }

        return null; // Timeout: word not found
    }


    public boolean tapWord(AppiumDriver driver, Word word) {
        if (word == null) return false;

        int centerX = word.getBoundingBox().x + word.getBoundingBox().width / 2;
        int centerY = word.getBoundingBox().y + word.getBoundingBox().height / 2;

        System.out.printf("Tapping on word '%s' at (%d, %d)%n", word.getText(), centerX, centerY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tapSequence = new Sequence(finger, 0);
        tapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tapSequence));
        return true;
    }

}

