package hu.webuni.hr.gye.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gye.config.HrListConfigProperties;
import hu.webuni.hr.gye.model.Employee;

@Service("smart")
public class SmartEmployeeService extends AbstractEmployeeService {
	
//	@Autowired
//	HrConfigProperties config;
	
	@Autowired
	HrListConfigProperties configList;

	@Value("${hr.pay.percent.def:0}")
	int defPercent;
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		log.debug("Start getPayRaisePercent");
		log.info("időarányos fizetés emelést kiszámító algoritmus használata");
		int raisePercent=defPercent;		
		
		LocalDateTime startDate = employee.getStartWork();
		LocalDateTime endDate = LocalDateTime.now();

		log.debug("belépés dátuma: " + endDate.toString());
		log.debug("jelenlegi dátuma: " + startDate.toString());
		
		long diffMonths = ChronoUnit.MONTHS.between(startDate, endDate);
		double diffYear = diffMonths/12.0;

		log.debug("eltelt hónapok: " + diffMonths);
		log.debug("eltelt évek: " + diffYear);	

//  Beégetett logika:		
		
//		if(diffYear>=config.getPay().getYear().getLimit().getHigh()) {
//			raisePercent = config.getPay().getPercent().getHigh();
//			log.debug("magas emelés jár");
//		}else if(diffYear>=config.getPay().getYear().getLimit().getMedium() && diffYear<config.getPay().getYear().getLimit().getHigh()) {
//			raisePercent = config.getPay().getPercent().getMedium();
//			log.debug("közepes emelés jár");
//		}else if(diffYear>=config.getPay().getYear().getLimit().getLow() && diffYear<config.getPay().getYear().getLimit().getMedium()) {
//			raisePercent = config.getPay().getPercent().getLow();
//			log.debug("alacsony emelés jár");
//		}else if(diffYear<config.getPay().getYear().getLimit().getLow()) {
//			raisePercent = config.getPay().getPercent().getDef();
//			log.debug("nem jár emelés");
//		}

// Beégetett logika setup:
//		
//				hr.pay.year.limit.low = 2.5
//				hr.pay.year.limit.medium = 5
//				hr.pay.year.limit.high = 10
//
//				hr.pay.percent.low = 2
//				hr.pay.percent.medium = 5
//				hr.pay.percent.high = 10
		
//Setup-olható logika:
// végigmegyünk a properties-ből kiolvasott year/percent párosok listáján. 
//Ha a ledolgozott év nagyobb mint egy párosnál az év akkor az lesz az emelés értéke, ha nem volt már nagyobb évhez találat.
//Ha egyáltalán nincs találat, akkor az emelés százaléka a default-nak beállított százalék lesz
		double candidateYear = 0;
		
		for (int i = 0; i < configList.getPayCategory().size(); i++) {
			int percent = configList.getPayCategory().get(i).getPercent();
			double year = configList.getPayCategory().get(i).getYear();
			
			log.debug("percent:" + percent +", year:" + year);
			
			if(diffYear>year && year>=candidateYear) {
				log.debug("talalat, raisePercent:" + percent + ", candidate year:" + year);
				raisePercent = percent;
				candidateYear = year;
			}
		}
		
		log.debug("End getPayRaisePercent");
		
		return raisePercent;

	}

	
}

