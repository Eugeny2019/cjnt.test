package cjnt.test.study;

import cjnt.test.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class StudyPage extends BaseView {
    private String longText;

    private boolean success = false;

    public StudyPage(WebDriver driver) {
        super(driver);
        try (BufferedReader in = new BufferedReader(new FileReader("src/main/resources/longtext.txt"))) {
            StringBuilder sb = new StringBuilder();
            in.lines().forEach(s -> sb.append(s.concat(System.lineSeparator())));
            longText = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='intro' and @data-question-answer-type='']")
    private WebElement introLocator;

    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='multiple' and @data-question-answer-type='']")
    private WebElement multipleChoiceLocator;
    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='multiple' and @data-question-answer-type='']//*[contains(@class,'btn') and contains(.,'Orange')]")
    private WebElement multipleChoiceButtonLocator;

    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='capture' and @data-question-answer-type='paragraph']")
    private WebElement paragraphLocator;

    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='starrating' and @data-question-answer-type='']")
    private WebElement starRatingLocator;

    @FindBy(xpath = "//*[contains(@class,'frame-current') and @data-question-type='constantsum' and @data-question-answer-type='']")
    private WebElement constantSumLocator;

    public StudyPage takeStudyQuestionByQuestion() {
        WebElement currentWebElement;
        while (!success) {
            webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@class,'frame-current')]"))));
            currentWebElement = driver.findElement(By.xpath("//*[contains(@class,'frame-current')]"));
            switch (currentWebElement.getAttribute("data-question-type")) {
                case "intro" -> takeIntro(currentWebElement);
                case "multiple" -> takeMultipleChoice(currentWebElement);
                case "capture" -> takeParagraph(currentWebElement);
                case "starrating" -> takeStarRating(currentWebElement);
                case "constantsum" -> takeConstantSum(currentWebElement);
                case "success" -> success = true;
            }
            if (!success) {
                webDriverWait.until(ExpectedConditions.invisibilityOf(currentWebElement));
            }
        }
        return this;
    }

    private StudyPage takeIntro(WebElement currentWebElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(introLocator));
        currentWebElement.findElement(By.xpath("//*[contains(@class,'btn') and contains(.,'Continue')]")).click();
        return this;
    }

    private StudyPage takeMultipleChoice(WebElement currentWebElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(multipleChoiceLocator));
        currentWebElement.findElement(By.xpath("//*[contains(@class,'btn') and contains(.,'Orange')]")).click();
        return this;
    }

    private StudyPage takeParagraph(WebElement currentWebElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(paragraphLocator));
//        int min = (Objects.isNull(currentWebElement.findElement(By.xpath("//textarea")).getAttribute("data-min-characters"))) ?
//                0
//                :
//                Integer.parseInt(currentWebElement.findElement(By.xpath("//textarea")).getAttribute("data-min-characters"));
//        int max = Integer.parseInt(currentWebElement.getAttribute("data-max-characters"));
//        String paragraphAnswer = longText.substring(min, min + new Random().nextInt(300));
        currentWebElement.findElement(By.xpath("//textarea")).sendKeys(longText);
        currentWebElement.findElement(By.xpath("//textarea")).sendKeys("\t");
        currentWebElement.findElement(By.xpath("//button[contains(@type,'button')]")).click();
//        currentWebElement.findElement(By.xpath("//button[contains(@type,'button')]")).sendKeys(Keys.RETURN);
        return this;
    }

    private StudyPage takeStarRating(WebElement currentWebElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(starRatingLocator));
        currentWebElement.findElement(By.xpath("//div[2]/div/div[2]/div/div/div/div/div/label[5]/i[1]")).click();
        currentWebElement.findElement(By.xpath("//div[3]/div/div[2]/div/div/div/div/div/label[3]/i[1]")).click();
        currentWebElement.findElement(By.xpath("//div[4]/div/div[2]/div/div/div/div/div/label[4]/i[1]")).click();
        currentWebElement.findElement(By.xpath("//*[contains(@class,'frame-current')]//button")).click();
        return this;
    }

    private StudyPage takeConstantSum(WebElement currentWebElement) {
        webDriverWait.until(ExpectedConditions.visibilityOf(constantSumLocator));
        //*[contains(@class,'frame-current')]//div[6]/div/div[2]/text()[1]
        int total = Integer.parseInt(currentWebElement
                .findElement(By.xpath("//div[6]/div/div[2]"))
                .getText().trim());
        int size = currentWebElement.findElements(By.xpath("//div[contains(@class,'term')]")).size();
        int[] numbers = generateNumbersConstantSum(total, size);
        for (int i = 0; i < size; i++) {
            while (!currentWebElement.findElement(By.xpath("//div[contains(@class,'term')][" + (i + 1) + "]//input[@type='number']")).isEnabled()) {
                Thread.yield();
            }
            currentWebElement
                    .findElement(
                            By.xpath(
                                    "//div[contains(@class,'term')][" + (i + 1) + "]//input[@type='number']"))
                    .sendKeys(String.valueOf(numbers[i]));
        }
        currentWebElement.findElement(By.xpath("//*[contains(@class,'frame-current')]//button[contains(.,'Continue')]")).click();
        return this;
    }

    private int[] generateNumbersConstantSum(int total, int size) {
        int[] result = new int[size];
        for (int i = 0; i < size - 1; i++) {
            if (total != 0) {
                result[i] = total - new Random().nextInt(total) - 1;
                total -= result[i];
            } else {
                result[i] = 0;
            }
        }
        result[size - 1] = total;
        return result;
    }

    public boolean isSuccess() {
        return success;
    }
}
