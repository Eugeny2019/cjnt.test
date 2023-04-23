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

public class CheckEmailReportPresentTest {
    private WebDriver driver;
    private static String loginPageUrl;
    private static String reportPageUrl;

//    @RegisterExtension
//    TestWatcher testWatcher = new JUnitExtention();

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void ReadPropertiesAndInitDriver() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/my.properties"));
        loginPageUrl = prop.getProperty("loginPageURL");
        reportPageUrl = prop.getProperty("reportPageUrl");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new EventFiringDecorator(new AdditionalLogger()).decorate(new ChromeDriver(options));
        driver.manage().window().setSize(new Dimension(1500, 900));
        driver.get(loginPageUrl);
    }

    @Test
    @Feature("Вход по логину и паролю")
    public void signIn() {
        LoginPage loginPage = new LoginPage(driver);
        HelloTasksPage helloTasksPage = loginPage.loginIntoConjointly();
        Assertions.assertTrue(Objects.nonNull(helloTasksPage));

        driver.get(reportPageUrl);
        ReportPage reportPage = new ReportPage(driver);
        List<String> reportTable = reportPage.checkEmailReportPresent();
        List<String> checkTable = List.of(
                "62842", "62843", "62845", "62846", "62847",
                "ipsum@natoque.com", "quis@ridiculus.com", "pellentesque@eget.com", "parturient@pellentesque.com", "mus@sit.com"
        );
        Assertions.assertTrue(checkTable.containsAll(reportTable));
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
