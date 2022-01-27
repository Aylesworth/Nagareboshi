package aylesw.meteor.simsimi;

/**
 * Client.java - a client class for requesting service to Simsimi Server.
 * 
 * @Project SimSimi API Example
 * @Date 2014.08.01
 * 
 * 
 */
public class Client {
	private static String answer = "";

	public static String getAnswer(String text, String languageCode) {
		
		// Create SimSimiAPI instance.
		SimsimiAPI simsimiAPI = new SimsimiAPI();
		
		// Create RequestParam instance.
		RequestParam requestParam = new RequestParam();
		
		requestParam.setText(text);
		requestParam.setLc(languageCode);
		
		// A response for requesting information. Response consists of JSON format.
		String response = simsimiAPI.request(requestParam);
		
		// Exception handling
		if (response == null) {
			answer = "I can't understand what you are saying.";
		} else {
			answer = response.split(",")[1].split(":")[1].replaceAll("\"", "");
		}
		return answer;
	}

}
