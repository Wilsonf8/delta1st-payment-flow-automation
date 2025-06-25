package samples.TransactionSearch;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.util.List;
import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateSearchRequest {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String args[]) throws Exception {
//        run();
        TssV2TransactionsPost201Response response = run();

        // Convert to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        JsonNode rootNode = mapper.readTree(json);
//        System.out.println(rootNode);
        JsonNode transactionSummaries = rootNode.get("embedded").get("transactionSummaries");
        System.out.println(transactionSummaries.findValues("id"));


    }

    public static TssV2TransactionsPost201Response run() {

        Model.CreateSearchRequest requestObj = new Model.CreateSearchRequest();

        requestObj.save(false);
        requestObj.name("MRN");
        requestObj.timezone("America/Chicago");
//        requestObj.query("clientReferenceInformation.code:TC50171_3 AND submitTimeUtc:[NOW/DAY-7DAYS TO NOW/DAY+1DAY}");
//        requestObj.query("clientReferenceInformation.code:TC50171_3");
//        requestObj.query("merchantId:460100000172");
        requestObj.query("merchantId:"+Configuration.getProdMerchantDetails().getProperty("merchantID"));
        requestObj.offset(0);
        requestObj.limit(1);
        requestObj.sort("submitTimeUtc:desc,id:asc");
        TssV2TransactionsPost201Response result = null;
        try {
//			merchantProp = Configuration.getMerchantDetails();
//            merchantProp = Configuration.getMyMerchantDetails();
            merchantProp = Configuration.getProdMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            SearchTransactionsApi apiInstance = new SearchTransactionsApi(apiClient);
            result = apiInstance.createSearch(requestObj);

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
