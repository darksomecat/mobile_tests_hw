package helpers;

import config.Config;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class Browserstack {

    private static final Pattern BUILD_ID = Pattern.compile("/builds/([^/]+)/sessions/");
    private static final int VIDEO_ATTEMPTS = 10;
    private static final int VIDEO_WAIT_MS = 3000;

    private static String cachedSessionId;
    private static JsonPath cachedSession;

    public static String videoUrl(String sessionId) {
        return session(sessionId).getString("automation_session.video_url");
    }

    public static String publicUrl(String sessionId) {
        return session(sessionId).getString("automation_session.public_url");
    }

    public static String textLogs(String sessionId) {
        String logsUrl = session(sessionId).getString("automation_session.logs");
        String buildId = buildId(logsUrl);
        String apiUrl = String.format(
                "https://api-cloud.browserstack.com/app-automate/builds/%s/sessions/%s/logs",
                buildId, sessionId);
        return downloadText(apiUrl);
    }

    public static String appiumLogs(String sessionId) {
        return downloadText(session(sessionId).getString("automation_session.appium_logs_url"));
    }

    public static String deviceLogs(String sessionId) {
        return downloadText(session(sessionId).getString("automation_session.device_logs_url"));
    }

    private static JsonPath session(String sessionId) {
        if (sessionId.equals(cachedSessionId) && cachedSession != null) {
            return cachedSession;
        }

        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);
        JsonPath body = null;
        for (int attempt = 1; attempt <= VIDEO_ATTEMPTS; attempt++) {
            body = given()
                    .auth().basic(Config.browserStack.user(), Config.browserStack.key())
                    .get(url)
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath();

            String videoUrl = body.getString("automation_session.video_url");
            if (videoUrl != null && !videoUrl.isBlank()) {
                break;
            }
            if (attempt < VIDEO_ATTEMPTS) {
                pause();
            }
        }

        cachedSessionId = sessionId;
        cachedSession = body;
        return body;
    }

    private static String buildId(String logsUrl) {
        Matcher matcher = BUILD_ID.matcher(logsUrl == null ? "" : logsUrl);
        if (!matcher.find()) {
            throw new IllegalStateException("BrowserStack build id is missing");
        }
        return matcher.group(1);
    }

    private static String downloadText(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("BrowserStack log url is empty");
        }
        return read(url).then().statusCode(200).extract().asString();
    }

    private static Response read(String url) {
        Response response = given()
                .auth().basic(Config.browserStack.user(), Config.browserStack.key())
                .redirects().follow(false)
                .get(url);

        int status = response.statusCode();
        if (status == 301 || status == 302 || status == 303 || status == 307) {
            return given()
                    .redirects().follow(true)
                    .get(response.getHeader("Location"))
                    .then()
                    .extract()
                    .response();
        }
        return response;
    }

    private static void pause() {
        try {
            Thread.sleep(VIDEO_WAIT_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
