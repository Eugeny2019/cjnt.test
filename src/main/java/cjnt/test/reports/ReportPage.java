package cjnt.test.reports;

import cjnt.test.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public List<String> getDataFromEmailReportTextResponses() {
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


    @FindBy(xpath = "//span[@class='pill-controls']/div[contains(@class,'btn-group-sm')]")
    private WebElement pillButtonLocator;

    @FindBy(xpath = "//span/a[contains(@href,'xls')]")
    private WebElement notifierXLSLocator;

    public int makeExcelExportFileForEmailReport() {
        webDriverWait.until(ExpectedConditions.visibilityOf(anyEmailReportLocator));
        driver.findElement(By.xpath("//span/*[@class='cli-email']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(pillButtonLocator));
        driver.findElement(By.xpath("//span[@class='pill-controls']/div[contains(@class,'btn-group-sm')]")).click();
        driver.findElement(By.xpath("//button[@tid='export-controls-button-excel']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(notifierXLSLocator));
        driver.findElement(By.xpath("//span/a[contains(@href,'xls')]")).click();
//        String separator = System.getProperty("file.separator");
//        String path = driver.findElement(By.xpath("//span/a[contains(@href,'xls')]")).getAttribute("href").replace("/",separator);
//        path = path.substring(path.lastIndexOf(separator) + 1, path.length() - 1);
//        path = System.getProperty("user.home") + separator + "Downloads" + separator +  path;
        return 21000;
    }

    @FindBy(xpath = "//span/a[contains(@href,'ppt')]")
    private WebElement notifierPPTLocator;

    public int makePowerPointExportFileForEmailReport() {
        webDriverWait.until(ExpectedConditions.visibilityOf(anyEmailReportLocator));
        driver.findElement(By.xpath("//span/*[@class='cli-email']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(pillButtonLocator));
        driver.findElement(By.xpath("//span[@class='pill-controls']/div[contains(@class,'btn-group-sm')]")).click();
        driver.findElement(By.xpath("//button[@tid='export-controls-button-powerpoint']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(notifierPPTLocator));
        driver.findElement(By.xpath("//span/a[contains(@href,'ppt')]")).click();
        return 21000;
    }
}
