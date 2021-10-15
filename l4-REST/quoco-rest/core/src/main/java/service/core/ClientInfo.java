package service.core;

/**
 * Interface to define the state to be stored in ClientInfo objects
 * 
 * @author Rem
 * @author edit by Hassan 
 *
 */
public class ClientInfo {
	private static final char MALE				= 'M';
	private static final char FEMALE				= 'F';
	private String name;
	private char gender;
	private int age;
	private int points;
	private int noClaims;
	private String licenseNumber;
	
	public ClientInfo(String name, char sex, int age, int points, int noClaims, String licenseNumber) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noClaims = noClaims;
		this.licenseNumber = licenseNumber;
	}
	
	//default constructor
	public ClientInfo() {};

	//getter and setter for name
	public String getName(){
            return name;
	}
	public void setName(String name){
		this.name = name;
	}

	//getter and setter for gender
	public char getGender(){
		return gender;
	}

	public void setGender(char gender){
		this.gender = gender;
	}

	//getter and setter for age
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}

	//getter and setter for points
	public int getPoints(){
		return points;
	}

	public void setPoints(int points){
		this.points = points;
	}

	//getter and setter for noClaims
	public int getClaims(){
		return noClaims;
	}

	public void setClaims(int noClaims){
		this.noClaims = noClaims;
	}

	//getter and setter for licenseNumber
	public String getLicenseNumber(){
		return licenseNumber;
	}

	public void getLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}
}
