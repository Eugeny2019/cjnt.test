package cjnt.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BaseView {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div[1]/div[1]/div[2]/div[1]/header/div/div/div[5]/div/div/a")
    private WebElement loginLink;
    @Step("Клик на Login")
    public LoginPage clickLogin() {
        webDriverWait.until(ExpectedConditions.visibilityOf(loginLink));
        loginLink.click();
        return new LoginPage(driver);
    }
}
