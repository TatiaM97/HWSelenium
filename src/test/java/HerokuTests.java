import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HerokuTests {
    @Test
    public void loginTest() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        Assertions.assertEquals("https://the-internet.herokuapp.com/",
                webDriver.getCurrentUrl());
        webDriver.quit();
    }

    // Практика 1
    @Test
    public void checkTitle() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        Assertions.assertEquals("The Internet",
                webDriver.getTitle());
        webDriver.quit();
    }

    // Практика 2
    @Test
    public void dropdownTest() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://the-internet.herokuapp.com/");
        webDriver.findElement(By.linkText("Dropdown")).click();
        WebElement selectDropdown = webDriver.findElement(By.cssSelector("[id='dropdown']"));
        Select select = new Select(selectDropdown);
        select.selectByVisibleText("Option 2");
        Assertions.assertEquals("2", selectDropdown.getAttribute("value"));
        webDriver.quit();
    }
}
