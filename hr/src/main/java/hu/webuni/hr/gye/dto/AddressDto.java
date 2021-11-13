package hu.webuni.hr.gye.dto;

public class AddressDto {
	private Long addressId;	
	private String city;
	private String zip;
	private String street;
	private String houseNumber;
	private String type;

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
		this.type = type;
	}



	@Override
	public String toString() {
		return "AddressDto [addressId=" + addressId + ", city=" + city + ", zip=" + zip + ", street=" + street
				+ ", houseNumber=" + houseNumber + ", type=" + type + "]";
	} 
}
