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
public class NoteTests {

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

        this.signupAndLoginUser();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
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

    private void signupAndLoginUser() {

        this.signup();

        this.login();
    }

    private void insertNewNote() {
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("test title");

        WebElement notedescription = driver.findElement(By.id("note-description"));

        notedescription.sendKeys("test description");

        WebElement savechanges = driver.findElement(By.id("save-changes"));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(savechanges));

        savechanges.click();
    }

    @Test
    public void createNote() {
        Assertions.assertEquals("Home", driver.getTitle());
        WebDriverWait wait = new WebDriverWait (driver, 30);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        WebElement notesTab = this.driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));
        WebElement newNote = driver.findElement(By.id("newnote"));
        wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();

        this.insertNewNote();

        Assertions.assertEquals("Result", driver.getTitle());
    }

    @Test
    public void updateNote() {

        Assertions.assertEquals("Home", driver.getTitle());
        WebDriverWait wait = new WebDriverWait (driver, 30);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        WebElement notesTab = this.driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));
        WebElement newNote = driver.findElement(By.id("newnote"));
        wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();

        this.insertNewNote();

        this.driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            editElement = element.findElement(By.name("edit"));
            if (editElement != null){
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement notetitle = driver.findElement(By.id("note-title"));
        wait.until(ExpectedConditions.elementToBeClickable(notetitle));
        notetitle.clear();
        notetitle.sendKeys("note-title");
        WebElement savechanges = driver.findElement(By.id("save-changes"));
        savechanges.click();
        Assertions.assertEquals("Result", driver.getTitle());



    }

    @Test
    public void deleteNote() {

        Assertions.assertEquals("Home", driver.getTitle());
        WebDriverWait wait = new WebDriverWait (driver, 30);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        WebElement notesTab = this.driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));
        WebElement newNote = driver.findElement(By.id("newnote"));
        wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();

        this.insertNewNote();

        this.driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            deleteElement = element.findElement(By.name("delete"));
            if (deleteElement != null){
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }


}
