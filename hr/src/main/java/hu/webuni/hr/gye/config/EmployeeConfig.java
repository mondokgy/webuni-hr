package hu.webuni.hr.gye.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.gye.service.DefaultEmployeeService;
import hu.webuni.hr.gye.service.EmployeeService;

@Configuration
@Profile("!smart")
public class EmployeeConfig {

	@Bean
	public EmployeeService employeeService(){
		return new DefaultEmployeeService();
	}
}
