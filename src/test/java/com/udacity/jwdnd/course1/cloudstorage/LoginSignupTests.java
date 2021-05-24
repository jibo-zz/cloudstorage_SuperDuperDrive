package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginSignupTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait (driver, 1000);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void UnauthorizedAccessRestrictions() {

        this.driver.get("http://localhost:" + this.port + "/home");

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void loginWithInvalidCredentials() throws InterruptedException {

        driver.get("http://localhost:" + this.port + "/login");

        Assertions.assertEquals("Login", driver.getTitle());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.id("invalid-credentials"));
        });

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("jibo");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("jibo");

        WebElement submitButton = driver.findElement(By.id("login"));

        Assertions.assertEquals("Login", submitButton.getText());

        submitButton.click();

        Assertions.assertDoesNotThrow(() -> {
            this.driver.findElement(By.id("invalid-credentials"));
        });
    }

    private void signup() {

        this.driver.get("http://localhost:" + this.port + "/signup");

        Assertions.assertEquals("Sign Up", driver.getTitle());

        Assertions.assertDoesNotThrow(() -> {

            WebElement fNameField = this.driver.findElement(By.id("inputFirstName"));

            fNameField.sendKeys("Moahmed");

            WebElement lNameField = this.driver.findElement(By.id("inputLastName"));

            lNameField.sendKeys("Cheikh");

            WebElement usrnameField = driver.findElement(By.id("inputUsername"));

            usrnameField.sendKeys("jibo");

            WebElement pwdField = driver.findElement(By.id("inputPassword"));

            pwdField.sendKeys("jibo");

            WebElement submitButton = driver.findElement(By.id("signup"));

            Assertions.assertEquals("Sign Up", submitButton.getText());

            submitButton.click();
        });

        this.driver.get("http://localhost:" + this.port + "/login");
    }

    private void login() {

        Assertions.assertEquals("Login", driver.getTitle());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.id("invalid-credentials"));
        });

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("jibo");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("jibo");

        WebElement submitButton = driver.findElement(By.id("login"));

        Assertions.assertEquals("Login", submitButton.getText());

        submitButton.click();

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.id("invalid-credentials"));
        });
    }

    @Test
    public void signupAndLogin() {

        this.signup();

        this.login();

        this.driver.get("http://localhost:" + this.port + "/home");

        WebElement logoutButton = driver.findElement(By.id("logout"));

        Assertions.assertEquals("Logout", logoutButton.getText());

        logoutButton.click();

        this.webDriverWait.until(ExpectedConditions.titleContains("Login"));

        Assertions.assertEquals("Login", driver.getTitle());
    }
}
