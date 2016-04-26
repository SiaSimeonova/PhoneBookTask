package PhoneBook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.TreeMap;

public class PhoneBook {

	private Map<String, String> phonebook;
	private Map<String, Integer> calls;
	
	PhoneBook() {
		this.phonebook = new TreeMap<String, String>();
		this.calls = new HashMap<String, Integer>();
	}

	public void addNewNumber(String name, String number) {
		if (valudateNumber(number) && name != null && name != "") {
			phonebook.put(name, number);
		}
	}

	public void deleteNumber(String name, String number) {
		phonebook.remove(name);
		calls.remove(number);
	}

	public void findNumber(String name) {
		System.out.println(phonebook.get(name));
	}

	public void makeACool(String number) {
		int value = calls.get(number) + 1;
		calls.replace(number, value);
	}

	public void printPhoneBook() {
		System.out.println("My numbers:");
		for (Entry<String, String> entry : phonebook.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void mostCalledNumbers() {
		sortByValue(calls);
		for (int i = 0; i <= 5; i++) {
			System.out.println(calls.get(i));
		}
	}

	private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Map.Entry<K, V>> st = map.entrySet().stream();
		st.sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
		return result;
	}

	public void loadNumbersFromFile(File file) throws IOException {
		if (file != null) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				for (String line; (line = br.readLine()) != null;) {
					line.replaceAll(" ", "");
					String[] entry = line.split(", ");
					if (entry.length == 2) {
						if (valudateNumber(entry[1])) {
							phonebook.put(entry[0], normalizeNumber(entry[1]));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IOException("The file cann't be found!");
		}
	}

	private String normalizeNumber(String number){
		if(number.length()==10){
			String temp=number.substring(1);
			number="+359";
			number=number.concat(temp);
		}
		if(number.length()==14){
			String temp=number.substring(4);
			number="+359";
			number=number.concat(temp);
		}
		return number;
	}

	private boolean valudateNumber(String numberToCheck) {
		// check if the number contains letters or other symbols
		for (int i = 0; i < numberToCheck.length(); i++) {
			if (numberToCheck.charAt(i) > 9 && numberToCheck.charAt(i) < 0) {
				return false;
			}
		}
		numberToCheck = new StringBuilder(numberToCheck).reverse().toString().substring(6);
		// check if the length of the number is correct
		if (numberToCheck.length() != 4 ^ numberToCheck.length() != 7 ^ numberToCheck.length() != 8) {
			return false;
		}
		// check the third digit

		if (numberToCheck.charAt(0) < '2') {
			return false;
		}

		// check the mobile operator code and the code of the country

		numberToCheck = numberToCheck.substring(1);
		if (numberToCheck.equals("780") || numberToCheck.equals("880") || numberToCheck.equals("980")) {
			return true;
		}
		if (numberToCheck.equals("78953+") || numberToCheck.equals("880953+") || numberToCheck.equals("980953+")) {
			return true;
		}
		if (numberToCheck.equals("7895300") || numberToCheck.equals("8895300") || numberToCheck.equals("9895300")) {
			return true;
		}
		return false;
	}
}
