package vues;



public class Customer {

	private String first_name;
	private String last_name;
	private int age;
	private String sexe;
	private String address;
	private String activity;

	public Customer(String first_name, String last_name, int age, String sexe,
			String address, String activity) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.sexe = sexe;
		this.address = address;
		this.activity = activity;

	}

	public Customer() {
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String toString() {
		return "Customers [first_name=" + first_name + ", last_name="
				+ last_name + ", age=" + age + ", sexe=" + sexe + ", address="
				+ address + ", activity=" + activity + "]\n";
		
	}

}
