package hu.webuni.hr.gye.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Entity
@Component
@SequenceGenerator(name="EMPLOYEE_SEQUENCE_GENERATOR", sequenceName="EMPLOYEE_SEQUENCE", initialValue=1, allocationSize=10)
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPLOYEE_SEQUENCE_GENERATOR")
	private Long employeeID;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name ="POSITION_ID")
	private Position position;
	private Integer salary;
	private LocalDateTime startWork;	
	
	@ManyToOne
	private Company company;
	
	@OneToMany(mappedBy = "createdBy")
	private List<Holidays> holidays;

	private String username;
	private String password;
	
	@ManyToOne
	private Employee manager;	
	
	public Employee() {

	}
	
	public Employee(Long employeeID, String name, Position position, Integer salary, LocalDateTime startWork, Company company) {
			this.employeeID = employeeID;
			this.name = name;
			this.position = position;
			this.salary = salary;
			this.startWork = startWork;
			this.company = company;
		}
	
	public Employee(String name, Position position, Integer salary, LocalDateTime startWork) {
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.startWork = startWork;
	}
	
	public Long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public LocalDateTime getStartWork() {
		return startWork;
	}
	public void setStartWork(LocalDateTime startWork) {
		this.startWork = startWork;
	}	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public List<Holidays> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<Holidays> holidays) {
		this.holidays = holidays;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", name=" + name + ", position=" + position + ", salary=" + salary
				+ ", startWork=" + startWork + ", company=" + company + ", holidays=" + holidays + ", username="
				+ username + ", password=" + password + ", manager=" + manager + "]";
	} 
}
