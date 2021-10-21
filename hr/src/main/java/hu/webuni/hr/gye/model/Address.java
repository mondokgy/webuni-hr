package hu.webuni.hr.gye.model;

public class Address {
	Long addressId;
	String city;
	String zip;
	String street;
	String houseNumber;
	String type;
	
	public Address(Long addressId, String city, String zip, String street, String houseNumber, String type) {
		super();
		this.addressId= addressId;
		this.city = city;
		this.zip = zip;
		this.street = street;
		this.houseNumber = houseNumber;
		this.type = type;
	}
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.city = type;
	}
	
	public String getFullAddress(Address adress) {
		String fullAddress = adress.getZip() + " " + adress.getCity() + ", " + adress.getStreet() + " " + adress.getHouseNumber() ;
		return fullAddress;
	}
	
	@Override
	public String toString() {
		return "Address [city=" + city + ", zip=" + zip + ", street=" + street + ", houseNumber=" + houseNumber + ", type=" + type + "]";
	}
	
	
}
