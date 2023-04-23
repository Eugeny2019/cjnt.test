package cjnt.test;

import cjnt.test.settings.HelloTasksPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginPage extends BaseView {
    static Properties prop = new Properties();
    private static String login;
    private static String password;

    public LoginPage(WebDriver driver) {
        super(driver);

        InputStream configFile;
        try {
            configFile = new FileInputStream("src/main/resources/my.properties");
            prop.load(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        login = prop.getProperty("userLogin");
        password = prop.getProperty("userPassword");
    }

    @FindBy(xpath = "//input[@id='email-input']")
    private WebElement loginFieldLocator;

    @FindBy(id = "__BVID__50")
    private WebElement passwordFieldLocator;

    @FindBy(xpath = "//h1[contains(.,'Launch a new experiment')]")
    private WebElement helloTasksPageLocator;

    public HelloTasksPage loginIntoConjointly() {
        webDriverWait.until(ExpectedConditions.visibilityOf(passwordFieldLocator));
        driver.findElement(By.xpath("//button[@type='button' and contains(@class,'btn no-wrap')][1]")).click();
        driver.findElement(By.id("email-input")).sendKeys(login);
        driver.findElement(By.id("__BVID__50")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='button' and contains(@class,'btn-success')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(helloTasksPageLocator));
        return new HelloTasksPage(driver);
    }
}
