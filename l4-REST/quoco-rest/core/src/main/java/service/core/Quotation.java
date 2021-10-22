package service.core;

import java.io.Serializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 * @author edited by Hassan
 *
 */

public class Quotation implements Serializable{

	private String company;
	private String reference;
	private double price;

	public Quotation(){

	}
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
		
	}
	public String getCompany(){
		return company;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getReference(){
		return reference;
	}

	public void setReference(String reference){
		this.reference = reference;
	}
	public double getprice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

}
