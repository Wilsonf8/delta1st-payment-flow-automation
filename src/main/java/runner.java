import Data.Configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import samples.TransactionDetails.TransactionDetailsFromRequestId;
import samples.TransactionSearch.latestRequestIdForMerchant;

public class runner {

    public static void main(String[] args) throws Exception{

//        String merchantId = Configuration.getProdMerchantDetails().getProperty("merchantID");

        latestRequestIdForMerchant reqId = new latestRequestIdForMerchant();

        String requestId = reqId.getReqId();
//        String requestId = "7503797321226560804884"; // Auth
//        String requestId = "7503797388626361304887"; // Settle
        System.out.println(requestId);

        TransactionDetailsFromRequestId transactionDetails = new TransactionDetailsFromRequestId();
        JsonNode jsonDetails = transactionDetails.getTransactionDetails(requestId);
        System.out.println(jsonDetails);

//        String id = jsonDetails.get("id").asText();
//        System.out.println("id: " + id);
//
//        JsonNode appInfo = jsonDetails.get("applicationInformation");
//        System.out.println(appInfo);


    }

}
