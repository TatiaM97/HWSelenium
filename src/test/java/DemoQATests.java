import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class DemoQATests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Получаем параметр браузера из командной строки (по умолчанию chrome)
        String browser = System.getProperty("browser", "chrome");
        // Инициализация нужного драйвера
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void formFilling() {
        // Открываем страницу с формой
        driver.get("https://demoqa.com/automation-practice-form");

        // Заполняем имя
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Кот");

        // Заполняем фамилию
        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Котофеев");

        // Заполняем email
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        userEmail.sendKeys("kot.kotofeev@mail.ru");

        // Выбираем пол
        WebElement gender = driver.findElement(By.cssSelector("label[for='gender-radio-1']"));
        gender.click();

        // Заполняем номер телефона
        WebElement userNumber = driver.findElement(By.id("userNumber"));
        userNumber.sendKeys("0123456789");

        // Устанавливаем дату рождения
        WebElement dateBirth = driver.findElement(By.id("dateOfBirthInput"));
        dateBirth.click(); // Клик по Input
        // Выбираем год в Dropdown
        WebElement yearDropdown = driver.findElement(By.className("react-datepicker__year-select"));
        Select yearSelect = new Select(yearDropdown);
        yearSelect.selectByVisibleText("2019");
        // Выбираем месяц в Dropdown
        WebElement monthDropdown = driver.findElement(By.className("react-datepicker__month-select"));
        Select monthSelect = new Select(monthDropdown);
        monthSelect.selectByVisibleText("September");
        // Выбираем день
        WebElement dayElement = driver.findElement(By.xpath("//div[contains(@class,'react-datepicker__day') and text()='13']"));
        dayElement.click();

        // Выбираем предмет
        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
        wait.until(ExpectedConditions.elementToBeClickable(subjectsInput));
        subjectsInput.sendKeys("Computer Science"); // Вводим значение в Input
        // Выбираем значение из выпадающего списка
        WebElement subjectsOptions = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'subjects-auto-complete__option') and contains(text(),'Computer Science')]")));
        subjectsOptions.click();

        // Выбираем хобби
        WebElement hobbies = driver.findElement(By.cssSelector("label[for='hobbies-checkbox-1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hobbies);
        hobbies.click();

        // Загружаем файл
        File file = new File("src/test/resources/test_files/example.jpg");
        String absolutePath = file.getAbsolutePath();
        WebElement uploadPicture = driver.findElement(By.id("uploadPicture"));
        uploadPicture.sendKeys(absolutePath);

        // Заполняем адрес
        WebElement userAddress = driver.findElement(By.id("currentAddress"));
        userAddress.sendKeys("Koshachya Street");

        // Выбираем штат
        WebElement userState = driver.findElement(By.id("state"));
        userState.click();
        WebElement firstState = driver.findElement(By.xpath("//div[contains(@class, 'option') and text()='NCR']"));
        firstState.click();

        // Выбираем город
        WebElement userCity = driver.findElement(By.id("city"));
        userCity.click();
        WebElement firstCity = driver.findElement(By.xpath("//div[contains(@class, 'option') and text()='Delhi']"));
        firstCity.click();

        // Отправляем форму
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();

        // Дожидаемся появления таблицы с результатами
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.table.table-dark")));

        SoftAssertions softly = new SoftAssertions();

        // Проверяем имя и фамилию
        WebElement nameCell = driver.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td"));
        softly.assertThat(nameCell.getText()).as("Неверное имя студента").isEqualTo("Кот Котофеев");

        // Проверяем email
        WebElement emailCell = driver.findElement(By.xpath("//td[text()='Student Email']/following-sibling::td"));
        softly.assertThat(emailCell.getText()).as("Неверный email").isEqualTo("kot.kotofeev@mail.ru");

        // Проверяем пол
        WebElement genderCell = driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td"));
        softly.assertThat(genderCell.getText()).as("Неверно указан пол").isEqualTo("Male");

        // Проверяем номер телефона
        WebElement mobileCell = driver.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td"));
        softly.assertThat(mobileCell.getText()).as("Неверный номер телефона").isEqualTo("0123456789");

        // Проверяем дату рождения
        WebElement dobCell = driver.findElement(By.xpath("//td[text()='Date of Birth']/following-sibling::td"));
        softly.assertThat(dobCell.getText()).as("Неверная дата рождения").isEqualTo("13 September,2019");

        // Проверяем предметы
        WebElement subjectsCell = driver.findElement(By.xpath("//td[text()='Subjects']/following-sibling::td"));
        softly.assertThat(subjectsCell.getText()).as("Неверно указаны предметы").isEqualTo("Computer Science");

        // Проверяем хобби
        WebElement hobbiesCell = driver.findElement(By.xpath("//td[text()='Hobbies']/following-sibling::td"));
        softly.assertThat(hobbiesCell.getText()).as("Неверно указаны хобби").isEqualTo("Sports");

        // Проверяем загруженное изображение
        WebElement pictureCell = driver.findElement(By.xpath("//td[text()='Picture']/following-sibling::td"));
        softly.assertThat(pictureCell.getText()).as("Неверное имя файла изображения").isEqualTo("example.jpg");

        // Проверяем адрес
        WebElement addressCell = driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td"));
        softly.assertThat(addressCell.getText()).as("Неверный адрес").isEqualTo("Koshachya Street");

        // Проверяем штат и город
        WebElement stateCityCell = driver.findElement(By.xpath("//td[text()='State and City']/following-sibling::td"));
        softly.assertThat(stateCityCell.getText()).as("Неверно указаны штат и город").isEqualTo("NCR Delhi");

        softly.assertAll();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}