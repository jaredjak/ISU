package contacts;

import java.util.ArrayList;
import java.util.Collections;

public class TestContacts {

	public static void main(String[] args) {
		ArrayList<Contact> contacts = new ArrayList<>();
		
		contacts.add(new Contact("Bob", "Smith"));
		contacts.add(new Contact("Sue", "Jones"));
		contacts.add(new Contact("Kim", "Jones"));
		
		Collections.sort(contacts);
		System.out.println(contacts);
	}

}
