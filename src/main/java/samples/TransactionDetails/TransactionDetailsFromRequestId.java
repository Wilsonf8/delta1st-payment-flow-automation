package samples.TransactionDetails;

import Api.TransactionDetailsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsGet200Response;
import com.cybersource.authsdk.core.MerchantConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class TransactionDetailsFromRequestId {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
//        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public JsonNode getTransactionDetails(String requestId) throws Exception{

       TssV2TransactionsGet200Response result = null;
       try {
           merchantProp = Configuration.getProdMerchantDetails(); // Gets merchant details
           ApiClient apiClient = new ApiClient();
           MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
           apiClient.merchantConfig = merchantConfig;

           TransactionDetailsApi apiInstance = new TransactionDetailsApi(apiClient);
           result = apiInstance.getTransaction(requestId);
//           System.out.println(result);

           responseCode = apiClient.responseCode;
           status = apiClient.status;
           WriteLogAudit(Integer.parseInt(responseCode));

       } catch (ApiException e) {
           e.printStackTrace();
           WriteLogAudit(e.getCode());
       } catch (Exception e) {
           e.printStackTrace();
       }

        // Convert to JSON string
        TssV2TransactionsGet200Response response = result;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        JsonNode rootNode = mapper.readTree(json);

       return rootNode;

    }

}
