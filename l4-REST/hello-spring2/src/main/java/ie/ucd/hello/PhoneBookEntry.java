package ie.ucd.hello;

public class PhoneBookEntry {
    private String firstname;
    private String surname;
    private String phone;

    public PhoneBookEntry(String firstname, String surname, String phone) {
        this.firstname = firstname;
        this.surname = surname;
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isValid(String phone_number) {
        return firstname != null && surname != null && phone_number.equals(phone);
    }

	public void update(PhoneBookEntry entry) {
        if (entry.firstname != null) firstname = entry.firstname;
        if (entry.surname != null) surname = entry.surname;
	}
}
