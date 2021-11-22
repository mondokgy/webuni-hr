package hu.webuni.hr.gye.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gye.service.SalaryService;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	SalaryService salaryService;
	
	@PutMapping("/{positionName}/raisebadeffort/{salary}")
	public void updateSalaryByPosition_BadEffort(@PathVariable String positionName, @PathVariable Integer salary, @RequestParam(required = false) Long companyId) {
		
		log.debug("restapi SalaryController, /{positionName}/raise/{salary}, put, updateSalaryByPosition start");
		log.debug("restapi SalaryController, positionName: *" + positionName + "*");
		log.debug("restapi SalaryController, salary: *" + salary + "*");
		log.debug("restapi SalaryController, companyId: *" + companyId + "*");
		salaryService.updateSalaryByPosition_BadEffort(positionName,salary,companyId);
		log.debug("restapi SalaryController, /{positionName}/raise/{salary}, put, updateSalaryByPosition end");
	}
	
	@PutMapping("/{positionName}/raise/{salary}")
	public void updateSalaryByPosition_GoodEffort(@PathVariable String positionName, @PathVariable Integer salary, @RequestParam(required = false) Long companyId) {
		
		log.debug("restapi SalaryController, /{positionName}/raise/{salary}, put, updateSalaryByPosition start");
		log.debug("restapi SalaryController, positionName: *" + positionName + "*");
		log.debug("restapi SalaryController, salary: *" + salary + "*");
		log.debug("restapi SalaryController, companyId: *" + companyId + "*");
		salaryService.updateSalaryByPosition_GoodEffort(positionName,salary,companyId);
		log.debug("restapi SalaryController, /{positionName}/raise/{salary}, put, updateSalaryByPosition end");
	}
}
