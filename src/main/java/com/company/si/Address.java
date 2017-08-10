package com.company.si;

/**
 * Address
 * 
 * Defines a basic address object. Address line 2 is optional.
 * 
 * @author Brian Hession
 *
 */
public class Address {

	private String address1;
	private String address2;
	private String city;
	private String state;
	private int zipcode;

	public Address(String address1, String address2, String city, String state, int zipcode) {
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	public Address(String address1, String city, String state, int zipcode) {
		this(address1, null, city, state, zipcode);
	}

	/**
	 * Spring kept yelling about a lack of default constructor. I messed around for
	 * about 30 minutes trying to figure out a way to avoid putting in this
	 * constructor that makes no logical sense in this context. Sometimes Reflection
	 * upsets me.
	 */
	public Address() {
		this(null, null, null, null, 0);
	}

	/**
	 * Deep copy of Address
	 */
	public Address clone() {
		return new Address(address1, address2, city, state, zipcode);
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(255);
		sb.append("Address [address1=").append(address1);
		if (address2 != null)
			sb.append(", address2=").append(address2);
		sb.append(", city=").append(city).append(", state=").append(state).append(", zipcode=").append(zipcode)
				.append("]");
		return sb.toString();
	}

	private boolean checkNulls(Address other) {
		if (this.getAddress1() == null || other.getAddress1() == null)
			if (this.getAddress1() != other.getAddress1())
				return false;
		if (this.getAddress2() == null || other.getAddress2() == null)
			if (this.getAddress2() != other.getAddress2())
				return false;
		if (this.getCity() == null || other.getCity() == null)
			if (this.getCity() != other.getCity())
				return false;
		if (this.getState() == null || other.getState() == null)
			if (this.getState() != other.getState())
				return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Address) {
			Address other = (Address) obj;
			if (!this.checkNulls(other))
				return false;
			return (this.getAddress1() == null || this.getAddress1().equals(other.getAddress1()))
					&& (this.getAddress2() == null || this.getAddress2().equals(other.getAddress2()))
					&& (this.getCity() == null || this.getCity().equals(other.getCity()))
					&& (this.getState() == null || this.getState().equals(other.getState()))
					&& this.getZipcode() == other.getZipcode();
		}
		return false;
	}
}
