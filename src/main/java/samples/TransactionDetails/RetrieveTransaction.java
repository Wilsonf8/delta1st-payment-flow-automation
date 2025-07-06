package samples.TransactionDetails;

import java.lang.invoke.MethodHandles;
import java.util.*;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import samples.Payments.Payments.SimpleAuthorizationInternet;

public class RetrieveTransaction {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
        TssV2TransactionsGet200Response response = run();

        // Convert to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        JsonNode rootNode = mapper.readTree(json);
        System.out.println(rootNode);
    }

    public static TssV2TransactionsGet200Response run() throws InterruptedException {
//        String id = SimpleAuthorizationInternet.run().getId();
        String id = "7513168692316211403813";

//        Thread.sleep(15000);

        TssV2TransactionsGet200Response result = null;
        try {
            System.out.println(id.getClass());
//            merchantProp = Configuration.getMerchantDetails();
//			merchantProp = Configuration.getMyMerchantDetails();
            merchantProp = Configuration.getSSDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            TransactionDetailsApi apiInstance = new TransactionDetailsApi(apiClient);
            result = apiInstance.getTransaction(id);

            responseCode = apiClient.responseCode;
            status = apiClient.status;
//            System.out.println("ResponseCode :" + responseCode);
//            System.out.println("ResponseMessage :" + status);
//            System.out.println(result);
            WriteLogAudit(Integer.parseInt(responseCode));

        } catch (ApiException e) {
            e.printStackTrace();
            WriteLogAudit(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
