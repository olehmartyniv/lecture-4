package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

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

    /**
     * Creates new product.
     */
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
        WebElement toggle = driver.findElement(By.className("switch-input"));
        action.moveToElement(toggle).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement growl = driver.findElement(By.className("growl-close"));
        action.moveToElement(growl).click().perform();
        WebElement button = driver.findElement(By.id("submit"));
        button.submit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement growl2 = driver.findElement(By.className("growl-close"));
        action.moveToElement(growl2).click().perform();
    }

    /**
     * Checks if product available on site.
     */
    public void checkProductAvailability(ProductData newProduct) {
        driver.get(getBaseUrl());                                       // open site
        WebElement allProducts = driver.findElement(By.className("all-product-link"));
        allProducts.click();                                            // select all items

        // finds all products links on page
        WebElement next;
        List<WebElement> products;
        WebElement element = null;

        do {
            wait.until(wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
            next = driver.findElement(By.xpath("//a[@rel='next']"));
            products = driver.findElements(By.xpath("//h1[@class='h3 product-title']/a"));

            for (WebElement product : products) {
                if (product.getText().equals(newProduct.getName())) {
                    element = product;
                    break;
                }
            }

            if (element == null) next.click();
            else break;
        } while (!next.getAttribute("class").contains("disabled"));

        // enters into the product page if exist
        Assert.assertNotNull(element);
        element.click();

        // confirms product name, quantity and price
        Assert.assertEquals(driver.findElement(By.xpath("//h1[@itemprop='name']")).getText(), newProduct.getName().toUpperCase());
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='product-quantities']/span")).getText().replaceAll("\\D", ""), newProduct.getQty().toString());
        Assert.assertEquals(driver.findElement(By.xpath("//span[@itemprop='price']")).getAttribute("content").replaceAll("\\.", ","), newProduct.getPrice());
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax_running")));
    }
}
