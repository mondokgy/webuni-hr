package hu.webuni.hr.gye;

import java.time.LocalDateTime;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.gye.model.Employee;
import hu.webuni.hr.gye.repository.PositionRepository;
import hu.webuni.hr.gye.service.InitDbService;
import hu.webuni.hr.gye.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner{

	
	@Autowired
	SalaryService salaryservice;
	
	@Autowired
	InitDbService initDbService;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	Employee employee;
	
	@Value("${hr.db.init:false}")
	boolean dbInit;
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger("LOG");
		
	@Override
	public void run(String... args) throws Exception {
		log.info("Start HR application.");
		System.out.println("Start HR application.");
		
		//teszt employee adatainak megadása
		
		employee = new Employee(1L,"Teszt Elek", positionRepository.findByName("tester"), 1000, LocalDateTime.of(2011,Month.JANUARY, 15, 19, 30, 40), null);

		log.info("Teszt employee:" + employee.toString());
		
		int origSalary = employee.getSalary();
		
		log.info(employee.getName() + " kiinduló fizetése: " + origSalary);
		//új fizetést kiszámoló szolgáltatás hívása
		salaryservice.setNewSalary(employee);

		log.info(employee.getName() + " emelt fizetése: " + employee.getSalary());
		
		log.info("Start DB initialization start.");
		if(dbInit) {
			log.info("DB init property is true");
			initDbService.clearDb();
			initDbService.insertTestData();
			log.info("DB init success.");
		}else {
			log.info("DB init property is false");
		}
		log.info("Start DB initialization stop.");
		
		
		log.info("End HR application.");
		System.out.println(employee.getName() + " eredeti fizetése: " + origSalary + ", emelt fizetése: " + employee.getSalary());
		System.out.println("End HR application (more information in log).");
	}

}
