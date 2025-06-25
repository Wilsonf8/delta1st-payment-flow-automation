import samples.TransactionSearch.latestRequestIdForMerchant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("For Auths in Cybersource...");
        // ... further code to accept client connections
        // Inside the try block from the previous example

        latestRequestIdForMerchant reqId = new latestRequestIdForMerchant();
        String requestId = reqId.getReqId();
        String prevRequestId = requestId;
        System.out.println(requestId);

        while (true) {
            reqId = new latestRequestIdForMerchant();
            requestId = reqId.getReqId();
            if (!requestId.equals(prevRequestId)){
                System.out.println(requestId);
                prevRequestId = requestId;
            }

            Thread.sleep(5000);

        }
    }

}
