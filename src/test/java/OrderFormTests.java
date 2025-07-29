import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderFormTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Test
    public void orderFormTest() {
        // Подключаем драйвер + ожидание
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Открываем страницу
        driver.get("https://formdesigner.ru/examples/order.html");

        // Принимаем cookie
        WebElement cookie = driver.findElement(By.xpath("//button[contains(text(),'Принять все')]"));
        cookie.click();

        // Переключаемся на iframe
        WebElement iframe = driver.findElement(By.cssSelector("#form_1006 > iframe"));
        driver.switchTo().frame(iframe);

        // Скролл к форме
        WebElement titleForm = driver.findElement(By.cssSelector("#pr > h3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", titleForm);

        // Нажимаем кнопку "Отправить"
        WebElement submit = driver.findElement(By.cssSelector("[name='submit']"));
        submit.click();

        // Проверяем текст ошибки
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'error')]")));

        String errorText = errorElement.getText().trim();
        Assertions.assertTrue(errorText.contains("Необходимо заполнить поле ФИО:."), "Actual error: " + errorText);
        Assertions.assertTrue(errorText.contains("Необходимо заполнить поле E-mail."), "Actual error: " + errorText);
        Assertions.assertTrue(errorText.contains("Необходимо заполнить поле Количество."), "Actual error: " + errorText);
        Assertions.assertTrue(errorText.contains("Необходимо заполнить поле Дата доставки."), "Actual error: " + errorText);

        driver.quit();
    }
}
