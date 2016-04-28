package gui;



public class Customer {

	/**
	 * This class contains all the informations of a bank customer
	 * FIRSTNAME
	 * LASTNAME
	 * AGE
	 * SEXE
	 * ADRESS
	 * ACTIVITY
	 **/

	private String first_name;
	private String last_name;
	private int age;
	private String sex;
	private String activity;
	private String adress;




	public Customer(String first_name, String last_name, int age, String sex, String activity, String adress) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.sex = sex;
		this.activity = activity;
		this.adress = adress;
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
		return sex;
	}

	public void setSexe(String sexe) {
		this.sex = sexe;
	}

	public String getAddress() {
		return adress;
	}

	public void setAddress(String address) {
		this.adress = address;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String toString() {
		return "Customers [first_name=" + first_name + ", last_name="
				+ last_name + ", age=" + age + ", sexe=" + sex + ", address="
				+ adress + ", activity=" + activity + "]\n";
		
	}

}
