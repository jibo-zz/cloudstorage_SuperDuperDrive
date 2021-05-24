package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {

        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait (driver, 1000);

        this.signupAndLogin();

        this.insertNewCredential();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void createCredential() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
        });
    }

    @Test
    public void updateCredential() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
        });

        driver.get("http://localhost:" + this.port + "/home");
        JavascriptExecutor jse =(JavascriptExecutor) driver;
        String newCredUsername = "test user 2";

        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credTab);
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (int i = 0; i < credsList.size(); i++) {
            WebElement element = credsList.get(i);
            editElement = element.findElement(By.name("editCred"));
            if (editElement != null){
                break;
            }
        }
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement credUsername = driver.findElement(By.id("credential-username"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credUsername));
        credUsername.clear();
        credUsername.sendKeys(newCredUsername);
        WebElement savechanges = driver.findElement(By.id("save-credential"));
        savechanges.click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

    @Test
    public void deleteCredential() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
        });

        driver.get("http://localhost:" + this.port + "/home");
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        Assertions.assertEquals("Home", driver.getTitle());

        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credTab);
        WebElement credsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (int i = 0; i < credsList.size(); i++) {
            WebElement element = credsList.get(i);
            deleteElement = element.findElement(By.name("delete"));
            if (deleteElement != null){
                break;
            }
        }
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

    /**
     * Private functions
     */

    private void insertNewCredential() {

        this.driver.get("http://localhost:" + this.port + "/home");
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        WebElement credentialsTab = this.driver.findElement(By.id("nav-credentials-tab"));

        jse.executeScript("arguments[0].click()", credentialsTab);
        this.webDriverWait.withTimeout(Duration.ofSeconds(30));

        WebElement newCred = driver.findElement(By.id("newcred"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(newCred)).click();
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("www.google.com");
        WebElement credUsername = driver.findElement(By.id("credential-username"));
        credUsername.sendKeys("test user");
        WebElement credPassword = driver.findElement(By.id("credential-password"));
        credPassword.sendKeys("testPassword");

        WebElement submit = driver.findElement(By.id("save-credential"));
        submit.click();
    }

    private void login() {

        driver.get("http://localhost:" + this.port + "/login");

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("jibo");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("jibo");

        WebElement submitButton = driver.findElement(By.id("login"));

        Assertions.assertEquals("Login", submitButton.getText());

        submitButton.click();
    }

    private void signup() {

        this.driver.get("http://localhost:" + this.port + "/signup");

        WebElement fNameField = this.driver.findElement(By.id("inputFirstName"));

        fNameField.sendKeys("Mohamed");

        WebElement lNameField = this.driver.findElement(By.id("inputLastName"));

        lNameField.sendKeys("Cheikh");

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("jibo");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("jibo");

        WebElement submitButton = driver.findElement(By.id("signup"));

        submitButton.click();
    }

    private void signupAndLogin() {

        this.signup();

        this.login();
    }
}

