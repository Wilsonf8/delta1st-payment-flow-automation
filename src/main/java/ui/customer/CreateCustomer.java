package ui.customer;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.elementrecognition.Text;

import java.util.List;
import java.util.Map;

public class CreateCustomer {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public CreateCustomer(AppiumDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public CreateCustomer create(String firstName, String lastName, String email, String phone){
        new FromCustomers(driver, wait).clickPlus();
        CreateCustomer customer = new CreateCustomer(driver, wait).findFirstName().enterFirstName(firstName);
        customer.findLastName().enterLastName(lastName);
        customer.findEmail().enterEmail(email);
        customer.findPhone().enterPhone(phone);
        return this;
    }
    public CreateCustomer findFirstName(){
        String firstName = "first name";
        new Text(driver, wait).click(firstName);
        return this;
    }
    public CreateCustomer enterFirstName(String firstNameToEnter){
        new Text(driver, wait).enter(firstNameToEnter);
        return this;
    }
    public CreateCustomer findLastName(){
        String lastName = "last name";
        new Text(driver, wait).click(lastName);
        return this;
    }
    public CreateCustomer enterLastName(String lastNameToEnter){
        new Text(driver, wait).enter(lastNameToEnter);
        return this;
    }
    public CreateCustomer findEmail(){
        String email = "email";
        new Text(driver, wait).click(email);
        return this;
    }
    public CreateCustomer enterEmail(String emailToEnter){
        new Text(driver, wait).enter(emailToEnter);
        return this;
    }
    public CreateCustomer findPhone(){
        String phone = "phone";
        new Text(driver, wait).click(phone);
        return this;
    }
    public CreateCustomer enterPhone(String phoneToEnter){
        new Text(driver, wait).enter(phoneToEnter);
        return this;
    }
}
