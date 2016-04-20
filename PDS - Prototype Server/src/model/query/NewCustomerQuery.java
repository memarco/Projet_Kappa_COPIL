package model.query;

import util.JsonImpl;

/**
 * Communication class. See the protocol's documentation for more details.
 * @version R3 sprint 1 - 18/04/2016
 * @author Kappa-V
 * @Changes
 * 		R1 sprint 4 -> R3 sprint 1 : </br>
 * 			-Moved from server.model.query to model.query
 * 			-Implemented toString
 */
public class NewCustomerQuery {
	public enum Gender {
		M,
		F;
	}
	
	private String first_name;
	private String last_name;
	private int age;
	private Gender sex;
	private String activity;
	private String adress;
	
	public NewCustomerQuery(String first_name, String last_name, int age, Gender sex, String activity, String adress) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.sex = sex;
		this.activity = activity;
		this.adress = adress;
	}
	
	@Override
	public String toString() {
		return "NEWCUSTOMER " + JsonImpl.toJson(this);
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

	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
}
