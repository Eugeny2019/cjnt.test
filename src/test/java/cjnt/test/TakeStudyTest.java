package cjnt.test;

import cjnt.test.study.StudyPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TakeStudyTest {
    WebDriver driver;
    static Properties prop = new Properties();
    private static String url;
    StudyPage studyPage;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void initDriver() throws IOException {
        prop.load(new FileInputStream("src/main/resources/my.properties"));
        url = prop.getProperty("studyUrl");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new EventFiringDecorator(new AdditionalLogger()).decorate(new ChromeDriver(options));
        driver.manage().window().setSize(new Dimension(1500, 900));
        driver.get(url);
        studyPage = new StudyPage(driver);
    }

    @Test
    @Feature("Пройти опросник с известными вопросами")
    public void signIn() {
        System.out.println(studyPage.takeStudyQuestionByQuestion());
        assertTrue(studyPage.isSuccess());
    }

    @AfterEach
    void tearDown() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry log : logs) {
            Allure.addAttachment("Browser stacktrace:\n", log.getMessage());
        }
//        ((JUnitExtention) testWatcher)
//                .setScreenShot(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        driver.quit();
    }
}
