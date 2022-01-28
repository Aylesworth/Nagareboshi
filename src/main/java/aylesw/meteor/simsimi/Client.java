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

	private static String lc = "vn";

	public static void setLc(String newLc) {
		lc = newLc;
	}

	public static String getLc() {
		return lc;
	}

	public static String getAnswer(String text) {
		
		// Create SimSimiAPI instance.
		SimsimiAPI simsimiAPI = new SimsimiAPI();
		
		// Create RequestParam instance.
		RequestParam requestParam = new RequestParam();
		
		requestParam.setText(text);
		requestParam.setLc(lc);
		
		// A response for requesting information. Response consists of JSON format.
		String response = simsimiAPI.request(requestParam);
		
		// Exception handling
		if (response == null) {
			answer = "*Silent*";
		} else {
			answer = response.split(",")[1].split(":")[1].replaceAll("\"", "");
			StringBuilder str = new StringBuilder();
			int i = 0;
			while (i < answer.length()) {
				if (answer.charAt(i) == '\\') {
					if (answer.charAt(i+1) == 'u') {
						str.append(Character.toChars(Integer.parseInt(answer.substring(i+2, i+6), 16)));
						i += 6; continue;
					}
					if (answer.charAt(i+1) == 'n') {
						str.append('\n');
						i += 2; continue;
					}
					if (answer.charAt(i+1) == 'r') {
						str.append('\r');
						i += 2; continue;
					}
					if (answer.charAt(i+1) == 't') {
						str.append('\t');
						i += 2; continue;
					}
				} else {
					str.append(answer.charAt(i));
					i++;
				}
			}
			answer = str.toString();
			if (answer.contains("I don't know what you're saying. Please teach me")) {
				answer = "Whatever";
			} else if (answer.contains("Tôi không biết bạn đang nói gì. Hãy dạy tôi")) {
				answer = "Sao cũng được";
			} else if (answer.contains("私が知らないと言葉です。教えてください")) {
				answer = "どうでもいい";
			}
		}
		return answer;
	}

}
