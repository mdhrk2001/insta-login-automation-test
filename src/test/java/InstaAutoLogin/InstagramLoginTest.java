package InstaAutoLogin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class InstagramLoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // Use WebDriverManager to manage the ChromeDriver automatically, specify version if needed
        WebDriverManager.chromedriver().driverVersion("128.0.6613.120").setup(); // Specify the correct Chrome version

        // Initialize WebDriver and WebDriverWait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open Instagram login page
        driver.get("https://www.instagram.com/accounts/login/");

        // Wait for the login form to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @Test
    public void edgeCaseLoginTest() throws InterruptedException {
        // Enter edge case inputs (e.g., empty username or password)
        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        // Pause driver for 1 second
        Thread.sleep(1000);

        username.sendKeys("the_footballera");
        password.sendKeys("");

        // Click the login button (Login button is not enable when password field is empty)
        //WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        //loginButton.click();

        // Wait for disabled login button to be visible
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/main[1]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[3]")));

        // Verify the behavior for edge case by checking if the disabled login button is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Login button is enabled for empty fields");

        // Pause driver for 2 seconds
        Thread.sleep(2000);

        // Display test case result
        System.out.println("Test ID: TC001");
        System.out.println("Inputs: Edge case (empty password field)");
        System.out.println("Expected Result: Unsuccessful login");
        System.out.println("Actual Result: Unsuccessful login");
        System.out.println("Status: Pass");
        System.out.println();
    }

    @Test
    public void invalidLoginTest() throws InterruptedException {
        // Enter invalid username and password
        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        // Pause driver for 1 second
        Thread.sleep(1000);

        username.sendKeys("Invalid_User");
        password.sendKeys("Invalid_Pass");

        // Click the login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();

        // Wait for error message to be visible
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"loginForm\"]/span/div")));

        // Verify login failed by checking if the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid credentials");

        // Pause driver for 2 seconds
        Thread.sleep(2000);

        // Display test case result
        System.out.println("Test ID: TC002");
        System.out.println("Inputs: Invalid inputs (wrong username & password)");
        System.out.println("Expected Result: Unsuccessful login");
        System.out.println("Actual Result: Unsuccessful login");
        System.out.println("Status: Pass");
        System.out.println();
    }

    @Test
    public void validLoginTest() throws InterruptedException {
        // Enter valid username and password
        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));

        // Pause driver for 1 second
        Thread.sleep(1000);

        username.sendKeys("the_footballera");
        password.sendKeys("Erafootball123#");

        // Click the login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();

        // Wait for the "Save Login Info" notification area that indicates login success
        WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='x1qjc9v5 x9f619 x78zum5 xdt5ytf x2lah0s xln7xf2 xk390pu x1s688f xk9mzb7 x1n2onr6 x11njtxf']")));

        // Verify login was successful by checking if the "Save Login Info" notification area is displayed
        Assert.assertTrue(homeElement.isDisplayed(), "Login failed for valid credentials");

        // Pause driver for 2 seconds
        Thread.sleep(2000);

        // Display test case result
        System.out.println("Test ID: TC003");
        System.out.println("Inputs: Valid inputs");
        System.out.println("Expected Result: Successful login");
        System.out.println("Actual Result: Successful login");
        System.out.println("Status: Pass");
        System.out.println();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        // Close the browser
        if (driver != null) {
            driver.quit();

            // Pause console for 3 seconds to show the output
            Thread.sleep(3000);
        }
    }
}