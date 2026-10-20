package helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Attach {

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain", fileExtension = ".xml")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain", fileExtension = ".txt")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo(String sessionId) {
        String videoUrl = Browserstack.videoUrl(sessionId);
        String sessionUrl = Browserstack.publicUrl(sessionId);
        String video = videoUrl == null || videoUrl.isBlank()
                ? "<p>Video is not ready yet.</p>"
                : "<video width='100%' height='100%' controls autoplay><source src='"
                + videoUrl
                + "' type='video/mp4'></video>";
        String link = sessionUrl == null || sessionUrl.isBlank()
                ? ""
                : "<p><a href='" + sessionUrl + "'>Open session in BrowserStack</a></p>";
        return "<html><body>" + video + link + "</body></html>";
    }

    @Attachment(value = "Text logs", type = "text/plain", fileExtension = ".log")
    public static String textLogs(String sessionId) {
        return Browserstack.textLogs(sessionId);
    }

    @Attachment(value = "Appium logs", type = "text/plain", fileExtension = ".log")
    public static String appiumLogs(String sessionId) {
        return Browserstack.appiumLogs(sessionId);
    }

    @Attachment(value = "Device logs", type = "text/plain", fileExtension = ".log")
    public static String deviceLogs(String sessionId) {
        return Browserstack.deviceLogs(sessionId);
    }

    public static void safely(String name, Runnable attachment) {
        try {
            attachment.run();
        } catch (Throwable error) {
            attachAsText(name + " error", error.toString());
        }
    }
}
