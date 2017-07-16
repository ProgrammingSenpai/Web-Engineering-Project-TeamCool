package qa;

import java.util.HashMap;

public class JsonParser {

	public JsonParser() {
	}

	public HashMap<String, String> jsonStringToArray(String string) {
		System.out.println(string);
		String[] s = string.substring(1, string.length() - 1).split(",");
		for(String stringz : s){
			System.out.println(stringz);		System.out.println();
}

		String[] s1;
		HashMap<String, String> result = new HashMap<String, String>();
		for (int x = 0; x < s.length; x++) {
			s1 = s[x].split(":");
			for(String stringz : s1){
				System.out.println(stringz);
				System.out.println();

				System.out.println(s1[0].substring(1, s1[0].length() - 1));
				System.out.println(s1[1].substring(1, s1[1].length() - 1));
				System.out.println();		System.out.println();


			}
			result.put(
					s1[0].substring(1, s1[0].length() - 1), 
					s1[1].substring(1, s1[1].length() - 1)
					);
		}
		return result;
	}
}
