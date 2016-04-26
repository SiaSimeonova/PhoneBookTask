package PhoneBook;

import java.io.File;
import java.io.IOException;

public class Demo {
	public static void main(String[] args) throws IOException {

		File file = new File("lib/numbers.txt");
		PhoneBook myPhB = new PhoneBook();
		myPhB.loadNumbersFromFile(file);
		myPhB.addNewNumber("Mitko", "0876555555");
		myPhB.deleteNumber("Anna", "+359898756668");
		myPhB.printPhoneBook();
		
	
	}
}
