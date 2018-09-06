package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.logging.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class CreateProductTest extends BaseTest {
    private final ProductData newProduct = ProductData.generate();

    @Test(dataProvider="login")
    public void createNewProduct(String login, String password) {
        actions.login(login, password);
        actions.createProduct(newProduct);
    }

    @Test(dependsOnMethods={"createNewProduct"})
    public void checkProductVisibility() {
        actions.checkProductAvailability(newProduct);
    }
}
