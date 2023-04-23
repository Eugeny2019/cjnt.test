package cjnt.test.reports;

import cjnt.test.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ReportPage extends BaseView {
    public ReportPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span/*[@class='cli-email']")
    private WebElement anyEmailReportLocator;

    @FindBy(xpath = "//div[contains(@class,'f-table__column')][1]/div[2]/span")
    private WebElement emailTableIsLoadedLocator;

    public List<String> checkEmailReportPresent() {
        webDriverWait.until(ExpectedConditions.visibilityOf(anyEmailReportLocator));
        driver.findElement(By.xpath("//span/*[@class='cli-email']")).click();

        webDriverWait.until(ExpectedConditions.visibilityOf(emailTableIsLoadedLocator));
        List<String> table = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                int col = i + 1;
                int row = i + 2;
                table.add(driver.findElement(By.xpath("//div[contains(@class,'f-table__column')][" + col + "]/div[" + row + "]/span")).getText());
            }
        }
        return table;
    }
}
