package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static myprojects.automation.assignment4.utils.Properties.*;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        driver.get(getBaseAdminUrl());                                  // open admin page
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(login);                                          // fill email
        WebElement pass = driver.findElement(By.id("passwd"));
        pass.sendKeys(password);                                        // fill password
        WebElement signIn = driver.findElement(By.name("submitLogin"));
        signIn.click();                                                 // click login button
    }

    public void createProduct(ProductData newProduct) {
        waitForContentLoad();
        Actions action = new Actions(driver);
        WebElement catalog = driver.findElement(By.linkText("Каталог"));
        action.moveToElement(catalog).perform();
        WebElement item = driver.findElement(By.linkText("товары"));
        action.moveToElement(item).click().perform();                   // enter into product catalog
        WebElement addItemButton = driver.findElement(By.id("page-header-desc-configuration-add"));
        addItemButton.click();                                          // click add button

        WebElement itemNameField = driver.findElement(By.id("form_step1_name_1"));
        itemNameField.sendKeys(newProduct.getName());                   // fill product name form

        // clear and fill quantity form
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('form_step1_qty_0_shortcut').setAttribute('value', '')");
        WebElement itemQuantityField = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        itemQuantityField.sendKeys(newProduct.getQty().toString());

        // clear and fill price form
        js.executeScript("document.getElementById('form_step1_price_shortcut').setAttribute('value', '')");
        WebElement itemPriceField = driver.findElement(By.id("form_step1_price_shortcut"));
        itemPriceField.sendKeys(newProduct.getPrice());

        // toggle visibility, save and close notification
        js.executeScript("document.getElementsByClassName('switch-input')[0].setAttribute('class', 'switch-input -checked')");
        WebElement button = driver.findElement(By.id("submit"));
        button.submit();
        WebElement growl = driver.findElement(By.className("growl-close"));
        growl.click();
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax_running")));
    }
}
