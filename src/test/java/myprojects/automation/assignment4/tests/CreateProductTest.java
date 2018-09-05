package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {

    @Test(dataProvider="login")
    public void createNewProduct(String login, String password) {
        actions.login(login, password);
        actions.createProduct(ProductData.generate());
    }

    // TODO implement logic to check product visibility on website
}
