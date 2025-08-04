import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DemowebshopTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Laptop", "Smartphone", "Fiction"})

    public void addCardTests(String searchQuery) {
        // Открываем главную страницу
        driver.get("https://demowebshop.tricentis.com/");

        // Вводим запрос поиска
        WebElement search = driver.findElement(By.id("small-searchterms"));
        search.sendKeys(searchQuery);
        search.submit();

        // Ожидаем загрузки страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-item")));

        // Scroll к товару
        WebElement productItem = driver.findElement(By.cssSelector(".product-item"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productItem);

        // Добавляем в корзину первый найденный товар
        WebElement firstProduct = driver.findElement(By.cssSelector(".product-item:first-child"));
        WebElement addCartButton = firstProduct.findElement(By.cssSelector("[value='Add to cart']"));
        addCartButton.click();

        // Ожидаем добавления товара
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));

        // Scroll наверх страницы
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        // Переходим в корзину
        driver.findElement(By.cssSelector("#topcartlink > a > span.cart-label")).click();

        // Проверяем товар
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-item-row")));
        Assertions.assertTrue(cartItem.getText().contains(searchQuery),
                "Товар " + searchQuery + "не найден в корзине");
    }

    @Test
    public void displayPerPageTest() {
        // Открываем раздел books
        driver.get("https://demowebshop.tricentis.com/books");

        // Проверяем что в фильтре по умолчанию установлено отображение "8" товаров
        WebElement displayFilter = driver.findElement(By.cssSelector("[value*='pagesize=8']"));
        Assertions.assertEquals("8", displayFilter.getText());

        // Проверяем, что количество элементов в сетке <=8
        verifyProductCount(8);

        // Выбираем в фильтре отображение 4х товаров
        WebElement pagesize = driver.findElement(By.id("products-pagesize"));
        pagesize.click();
        Select sizeSelect = new Select(pagesize);
        sizeSelect.selectByVisibleText("4");

        // Проверяем, что количество элементов в сетке <=4
        verifyProductCount(4);
    }

    // Метод для проверки количества элементов в сетке
    private void verifyProductCount(int maxCount) {
        WebElement productGrid = driver.findElement(By.cssSelector("[class='product-grid']"));
        List<WebElement> products = productGrid.findElements(By.cssSelector("[class='item-box']"));
        Assertions.assertTrue(products.size() <= maxCount, "Количество элементов больше " + maxCount);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
