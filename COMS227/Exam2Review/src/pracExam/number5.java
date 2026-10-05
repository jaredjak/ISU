//package pracExam;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class number5 {
//	
//	// First part
//	private ArrayList<Contact> directory;
//	
//	
//	public contactDirectory() {
//		directory = new ArrayList<>();
//	}
//	
//	
//	void addContact(Contact c) {
//		directory.add(c);
//	}
//	
//	void addFromFile(String filename) throws FileNotFoundException {
//		File file = new Fle(filename);
//		Scanner scnr = new Scanner(file);
//		
//		while (scnr.hasNextLine()) {
//			String contact = scnr.nextLine();
//			String[] split = contact.split(", ");
//			
//			if (split.length == 2) {
//				Contact contact1 = new Contact(split[0], split[1]);
//				addContact(contact1);
//			}
//		}
//	}
//
//	
//	String lookUpPhoneNumber(String name) {
//		for (Contact contact : directory) {
//			if (contact.getName().equals(name)) {
//				return contact.getPhoneNumber;
//			}
//		}
//		return "";
//	}
//}