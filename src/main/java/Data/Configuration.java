package Data;

import java.util.Properties;

public class Configuration {

	public static Properties getProdMerchantDetails(){
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "460100000172");
		props.setProperty("runEnvironment", "api.cybersource.com");
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "fee38334-6a78-4d08-a3de-1c4144216b81");
		props.setProperty("merchantsecretKey", "GyWFaI3oB5/hw/d5h+SJJxVqVhOdCf4NzsIYIKMh888=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
//		props.setProperty("enableClientCert", "true");
//		props.setProperty("clientCertDirectory", "src/main/resources");
//		props.setProperty("clientCertFile", "woodforest_1750207501.p12");
//		props.setProperty("clientCertPassword", "Jacksonflint8!");
//		props.setProperty("clientId", "");
//		props.setProperty("clientSecret", "");

		return props;
	}

	public static Properties getSSDetails(){
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "crest_test001");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "b44c3377-ddc9-4026-a1d5-055f932ce849");
		props.setProperty("merchantsecretKey", "CFVmAEQONsFCqnDRN3hRCJ5Mpqmzev8Fh13ICoJcY2E=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		props.setProperty("logMaximumSize", "5M");

		return props;
	}

	public static Properties getMyMerchantDetails(){
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "woodforest_1750207501");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
//		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "aabf2e8e-8f69-4a06-b761-c3977e45ac92");
		props.setProperty("merchantsecretKey", "BweHyJOEZ4o/bN4t4TaDWun0lgA6yxQgMd6D2LHuGME=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
//		props.setProperty("enableClientCert", "true");
//		props.setProperty("clientCertDirectory", "src/main/resources");
//		props.setProperty("clientCertFile", "woodforest_1750207501.p12");
//		props.setProperty("clientCertPassword", "Jacksonflint8!");
//		props.setProperty("clientId", "");
//		props.setProperty("clientSecret", "");

		return props;
	}

	public static Properties getMerchantDetails() {
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "testrest");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// MetaKey Parameters
		props.setProperty("portfolioID", "");
		props.setProperty("useMetaKey", "false");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
		props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
		props.setProperty("enableClientCert", "false");
		props.setProperty("clientCertDirectory", "src/main/resources");
		props.setProperty("clientCertFile", "");
		props.setProperty("clientCertPassword", "");
		props.setProperty("clientId", "");
		props.setProperty("clientSecret", "");

		/*
		PEM Key file path for decoding JWE Response Enter the folder path where the .pem file is located.
		It is optional property, require adding only during JWE decryption.
		*/
		props.setProperty("jwePEMFileDirectory", "src/main/resources/NetworkTokenCert.pem");
		
		//Add the property if required to override the cybs default developerId in all request body
		props.setProperty("defaultDeveloperId", "");

		return props;

	}
	
	public static Properties getAlternativeMerchantDetails() {
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "testrest_cpctv");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest_cpctv");
		props.setProperty("keyPass", "testrest_cpctv");
		props.setProperty("keyFileName", "testrest_cpctv");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "964f2ecc-96f0-4432-a742-db0b44e6a73a");
		props.setProperty("merchantsecretKey", "zXKpCqMQPmOR/JRldSlkQUtvvIzOewUVqsUP0sBHpxQ=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		return props;

	}
	
	public static Properties getMerchantDetailsForBatchUploadSample() {
		Properties props = new Properties();

		// HTTP_Signature = http_signature and JWT = jwt
		props.setProperty("authenticationType", "jwt");
		props.setProperty("merchantID", "qaebc2");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// MetaKey Parameters
		props.setProperty("portfolioID", "");
		props.setProperty("useMetaKey", "false");

		// JWT Parameters
		props.setProperty("keyAlias", "qaebc2");
		props.setProperty("keyPass", "?Test1234");
		props.setProperty("keyFileName", "qaebc2");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
		props.setProperty("enableClientCert", "false");
		props.setProperty("clientCertDirectory", "src/main/resources");
		props.setProperty("clientCertFile", "");
		props.setProperty("clientCertPassword", "");
		props.setProperty("clientId", "");
		props.setProperty("clientSecret", "");

		
		
		//Add the property if required to override the cybs default developerId in all request body
		props.setProperty("defaultDeveloperId", "");

		return props;

	}
	
}
