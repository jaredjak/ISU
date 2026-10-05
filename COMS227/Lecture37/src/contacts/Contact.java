package contacts;

public class Contact implements Comparable <Contact>{
	private String firstName;
	private String lastName;

	@Override
	public String toString() {
		return "Contact: " + lastName + ", " + firstName;
	}
	
	@Override
	public int compareTo(Contact contact) {
		int result = lastName.compareTo(contact.getLastName());
		if (result == 0) {
			return firstName.compareTo(contact.getFirstName());
		}
		return result;
	}
	
	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}