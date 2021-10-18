package hu.webuni.hr.gye.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="listhr")
@Component
public class HrListConfigProperties {

	private List<PayCategory> paycategory = new ArrayList<PayCategory>();
	
	public List<PayCategory> getPayCategory() {
		return paycategory;
	}

	public void setPayCategory(List<PayCategory> paycategory) {
		this.paycategory = paycategory;
	}

	public static class PayCategory{
		private double year;
		private int percent;
		
		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		public double getYear() {
			return year;
		}

		public void setYear(double year) {
			this.year = year;
		}
		
		
	}
	
	
	
}

