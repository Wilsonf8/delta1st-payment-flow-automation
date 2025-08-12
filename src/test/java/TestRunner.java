import db.dbmanager.DatabaseManager;
import db.tunnel.SSHTunnel;
import db.wait.Wait;
import logger.Logger;
import logger.ResultLoggerExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.base.BaseUi;
import ui.customer.CreateCustomer;
import ui.customer.FromCustomers;
import ui.elementrecognition.Text;
import ui.elementrecognition.googleelementrecognition.GoogleText;
import ui.fromhome.FromHome;
import ui.login.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(ResultLoggerExtension.class)
public class TestRunner extends BaseUi { // driver and wait comes from BaseUi

    @BeforeAll
    public static void setUpLoggerOnce() {
        Logger.init();
        Logger.logHeading("=== Test Suite Started ===");
        SSHTunnel.openTunnel();
        DatabaseManager.initializeDataSource();

    }

    @Test
    void canLogin(){
        Login login = new Login(driver, wait, "ss-service-qa", "210335");
        login.enterSubdomain().clickGetStarted().clickLoginWithPin().enterPin();
    }
    @Test
    void goToPOS(){
        Login login = new Login(driver, wait, "ss-service-qa", "210335");
        login.enterSubdomain().clickGetStarted().clickLoginWithPin().enterPin();
        new FromHome(driver, wait).goTo("pos");
    }
    @Test
    void createNewCustomer() {

        Map<String, String> expected = new HashMap<>();
        expected.put("firstName", "Wilson");
        expected.put("lastName", "Flores");
        expected.put("email", "test1234@gmail.com");
        expected.put("phoneNumber", "(098) 765-4321");


        Login login = new Login(driver, wait, "ss-service-qa", "210335");
        login.enterSubdomain().clickGetStarted().clickLoginWithPin().enterPin();
        GoogleText text = new GoogleText(driver, wait).click("customers");
        new CreateCustomer(driver, wait).create(expected.get("firstName"), expected.get("lastName"), expected.get("email"), expected.get("phoneNumber"));

        Connection conn = null;
        try {conn = DatabaseManager.getConnection();}
        catch (SQLException e) {throw new RuntimeException(e);}

        int last = 0;
        try {last = Wait.getLastRowId(conn, "customers");}
        catch (SQLException e) {throw new RuntimeException(e);}
        System.out.println(last);

        text.click("save");

        Map<String, Object> actual = null;
        try {actual = new Wait().waitForNewRow(conn, "customers", last, 100, 10);}
        catch (Exception e) {throw new RuntimeException(e);}

        System.out.println(actual.keySet());

        System.out.println(actual.get("first_name"));


        new FromCustomers(driver, wait).deleteCustomer("Wilson Flores");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }



}
