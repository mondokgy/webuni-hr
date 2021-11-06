package hu.webuni.hr.gye.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.model.Employee;

@Service("default")
public class DefaultEmployeeService extends AbstractEmployeeService {

	@Value("${hr.pay.percent.def:0}")
	int defPercent;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		log.info("alap, fix fizetés emelést kiszámító algoritmus használata");
		return defPercent;
	}

}
