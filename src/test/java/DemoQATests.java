import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DemoQA {
    private WebDriver driver; // Переменная класса

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome"); // chrome по умолчанию
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
    }

        @Test
        public void formFilling() {
            driver.get("https://demoqa.com/automation-practice-form"); // открываем страницу с формой
        // Заполняем форму
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.sendKeys("Кот");

            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("Котофеев");

            WebElement userEmail = driver.findElement(By.id("userEmail"));
            userEmail.sendKeys("kot.kotofeev@mail.ru");

            WebElement gender = driver.findElement(By.cssSelector("input[name='gender'][value='Male']"));
            gender.click();

            WebElement userNumber = driver.findElement(By.id("userNumber"));
            userNumber.sendKeys("0123456789");


        }
        }