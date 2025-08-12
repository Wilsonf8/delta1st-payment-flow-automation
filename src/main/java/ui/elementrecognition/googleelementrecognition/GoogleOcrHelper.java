package ui.elementrecognition.googleelementrecognition;

import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import javax.imageio.ImageIO;
import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class GoogleOcrHelper {

    public int textLength(String text) {
        String[] textList = text.split(" ");
        return textList.length;
    }

    private BufferedImage removeGreenPixels(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                if (color.getGreen() > 120 && color.getRed() < 120 && color.getBlue() < 100) {
                    result.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    result.setRGB(x, y, color.getRGB());
                }
            }
        }

        return result;
    }

    private String encodeImageToBase64(BufferedImage image) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public List<EntityAnnotation> detectTextFromScreenshot(AppiumDriver driver) throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screenshot);
//        image = removeGreenPixels(image);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        ByteString imgBytes = ByteString.copyFrom(baos.toByteArray());

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse res = response.getResponses(0);

            if (res.hasError()) {
                System.err.println("Error: " + res.getError().getMessage());
                return Collections.emptyList();
            }
            return res.getTextAnnotationsList();
        }
    }

    public List<String> getAllWordsOnScreen(AppiumDriver driver) throws Exception {
        List<EntityAnnotation> annotations = detectTextFromScreenshot(driver);
        List<String> words = new ArrayList<>();

        for (int i = 1; i < annotations.size(); i++) {
            words.add(annotations.get(i).getDescription());
        }
        return words;
    }

    public EntityAnnotation findText(AppiumDriver driver, String wordToFind, int textSize) throws Exception {
        List<EntityAnnotation> annotations = detectTextFromScreenshot(driver);
        for (int i = textSize - 1; i < annotations.size(); i++) {
            StringBuilder patchedText = new StringBuilder();
            for (int j = textSize - 1; j >= 0; j--) {
                if (!patchedText.toString().isEmpty()) {
                    patchedText.append(" ");
                }
                patchedText.append(annotations.get(i - j).getDescription());
            }
            if (wordToFind.equalsIgnoreCase(patchedText.toString())) {
                return annotations.get(i);
            }
        }
        return null;
    }

    public EntityAnnotation waitForText(AppiumDriver driver, String wordToFind, int timeoutSeconds, int pollIntervalMillis) throws Exception {
        int textSize = textLength(wordToFind);
        long start = System.currentTimeMillis();
        long end = start + timeoutSeconds * 1000L;

        while (System.currentTimeMillis() < end) {
            EntityAnnotation word = findText(driver, wordToFind, textSize);
            if (word != null) return word;
            Thread.sleep(pollIntervalMillis);
        }

        return null;
    }

    public boolean tapWord(AppiumDriver driver, EntityAnnotation word) {
        if (word == null || word.getBoundingPoly().getVerticesCount() < 2) return false;

        int x1 = word.getBoundingPoly().getVertices(0).getX();
        int y1 = word.getBoundingPoly().getVertices(0).getY();
        int x2 = word.getBoundingPoly().getVertices(2).getX();
        int y2 = word.getBoundingPoly().getVertices(2).getY();

        int centerX = (x1 + x2) / 2;
        int centerY = (y1 + y2) / 2;

        System.out.printf("Tapping on word '%s' at (%d, %d)%n", word.getDescription(), centerX, centerY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tapSequence = new Sequence(finger, 0);
        tapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tapSequence));
        return true;
    }
    public Point getWordCenterCoordinates(EntityAnnotation word) {
        if (word == null || word.getBoundingPoly().getVerticesCount() < 2) {
            return null; // Can't calculate center
        }

        int x1 = word.getBoundingPoly().getVertices(0).getX();
        int y1 = word.getBoundingPoly().getVertices(0).getY();
        int x2 = word.getBoundingPoly().getVertices(2).getX();
        int y2 = word.getBoundingPoly().getVertices(2).getY();

        int centerX = (x1 + x2) / 2;
        int centerY = (y1 + y2) / 2;

        return new Point(centerX, centerY);
    }
}

