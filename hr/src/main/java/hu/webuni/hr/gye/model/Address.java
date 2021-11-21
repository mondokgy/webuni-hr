package hu.webuni.hr.gye.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="ADDRESS_SEQUENCE_GENERATOR", sequenceName="ADDRESS_SEQUENCE", initialValue=1, allocationSize=10)
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADDRESS_SEQUENCE_GENERATOR")
	private Long addressId;
	
	private String city;
	private String zip;
	private String street;
	private String houseNumber;
	private String type;
	@ManyToOne
	private Company company;
	
	public Address() {

	}
		
	public Address(Long addressId, String city, String zip, String street, String houseNumber, String type,
			Company company) {
		super();
		this.addressId = addressId;
		this.city = city;
		this.zip = zip;
		this.street = street;
		this.houseNumber = houseNumber;
		this.type = type;
		this.company = company;
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
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getFullAddress(Address adress) {
		String fullAddress = adress.getZip() + " " + adress.getCity() + ", " + adress.getStreet() + " " + adress.getHouseNumber() ;
		return fullAddress;
	}
	
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", city=" + city + ", zip=" + zip + ", street=" + street
				+ ", houseNumber=" + houseNumber + ", type=" + type + ", company=" + company + "]";
	}
	
	
}
