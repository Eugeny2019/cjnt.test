package cjnt.test;

import cjnt.test.reports.ReportPage;
import cjnt.test.settings.HelloTasksPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class CheckEmailReportTest {
    private static WebDriver driver = null;
    private static String loginPageUrl;
    private static String reportPageUrl;

//    @RegisterExtension
//    TestWatcher testWatcher = new JUnitExtention();

    @BeforeAll
    static void registerDriver() throws IOException{
        WebDriverManager.chromedriver().setup();

        Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/my.properties"));
        loginPageUrl = prop.getProperty("loginPageURL");
        reportPageUrl = prop.getProperty("reportPageUrl");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new EventFiringDecorator(new AdditionalLogger()).decorate(new ChromeDriver(options));
        driver.manage().window().setSize(new Dimension(1500, 900));
        driver.get(loginPageUrl);

        LoginPage loginPage = new LoginPage(driver);
        HelloTasksPage helloTasksPage = loginPage.loginIntoConjointly();
        Assertions.assertTrue(Objects.nonNull(helloTasksPage));
    }

    @Test
    @Feature("Проверка наличия опреденных данных в таблице ответов Email")
    public void CheckEmailReportPresentTest() {
        if (!driver.getCurrentUrl().contains("report")) {
            driver.get(reportPageUrl);
        }
        ReportPage reportPage = new ReportPage(driver);
        List<String> reportTable = reportPage.getDataFromEmailReportTextResponses();
        List<String> checkTable = List.of(
                "62842", "62843", "62845", "62846", "62847",
                "ipsum@natoque.com", "quis@ridiculus.com", "pellentesque@eget.com", "parturient@pellentesque.com", "mus@sit.com"
        );
        Assertions.assertTrue(checkTable.containsAll(reportTable));
    }

    @Test
    @Feature("Проверка, что файл экспорта в Эксел вопроса Email не пустой")
    public void CheckEmailExportExcelReportNotEmptyTest() {
        if (!driver.getCurrentUrl().contains("report")) {
            driver.get(reportPageUrl);
        }
        ReportPage reportPage = new ReportPage(driver);
        int fileSize = reportPage.makeExcelExportFileForEmailReport();

        Assertions.assertTrue(fileSize > 20000);
    }

    @Test
    @Feature("Проверка, что файл экспорта в Эксел вопроса Email не пустой")
    public void CheckEmailExportPowerPointReportNotEmptyTest() {
        if (!driver.getCurrentUrl().contains("report")) {
            driver.get(reportPageUrl);
        }
        ReportPage reportPage = new ReportPage(driver);
        int fileSize = reportPage.makePowerPointExportFileForEmailReport();

        Assertions.assertTrue(fileSize > 20000);
    }

    @AfterAll
    static void tearDown() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry log : logs) {
            Allure.addAttachment("Browser stacktrace:\n", log.getMessage());
        }
//        ((JUnitExtention) testWatcher)
//                .setScreenShot(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        driver.quit();
    }
}
