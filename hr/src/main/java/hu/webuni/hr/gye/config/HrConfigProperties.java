package hu.webuni.hr.gye.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="hr")
@Component
public class HrConfigProperties {

	private Pay pay = new Pay();
	
	public Pay getPay() {
		return pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
	}

	public static class Pay {
		
		private Year year = new Year();
		private Percent percent = new Percent();
		
		public Year getYear() {
			return year;
		}
		public void setYear(Year year) {
			this.year = year;
		}
		public Percent getPercent() {
			return percent;
		}
		public void setPercent(Percent percent) {
			this.percent = percent;
		}
		
	}
	
	public static class Year {
		private Limit limit = new Limit();

		public Limit getLimit() {
			return limit;
		}

		public void setLimit(Limit limit) {
			this.limit = limit;
		}
	}
	
	public static class Limit {
		double low;
		double medium;
		double high;
		
		public double getLow() {
			return low;
		}
		public void setLow(double low) {
			this.low = low;
		}
		public double getMedium() {
			return medium;
		}
		public void setMedium(double medium) {
			this.medium = medium;
		}
		public double getHigh() {
			return high;
		}
		public void setHigh(double high) {
			this.high = high;
		}
		
	}
	
	public static class Percent {
		int low;
		int medium;
		int high;
		int def;
		
		public int getLow() {
			return low;
		}
		public void setLow(int low) {
			this.low = low;
		}
		public int getMedium() {
			return medium;
		}
		public void setMedium(int medium) {
			this.medium = medium;
		}
		public int getHigh() {
			return high;
		}
		public void setHigh(int high) {
			this.high = high;
		}
		public int getDef() {
			return def;
		}
		public void setDef(int def) {
			this.def = def;
		}
		
		
	}
	
    private List<String> position = new ArrayList<String>();
	
	public List<String> getPosition() {
		return position;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}

	private JwtData jwt = new JwtData();
	
	public static class JwtData {
		private String issuer;
		private String secret;
		private String alg;
		private Duration duration;
		public String getIssuer() {
			return issuer;
		}
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}
		public String getSecret() {
			return secret;
		}
		public void setSecret(String secret) {
			this.secret = secret;
		}
		public String getAlg() {
			return alg;
		}
		public void setAlg(String alg) {
			this.alg = alg;
		}
		public Duration getDuration() {
			return duration;
		}
		public void setDuration(Duration duration) {
			this.duration = duration;
		}
	}

	public JwtData getJwt() {
		return jwt;
	}

	public void setJwt(JwtData jwt) {
		this.jwt = jwt;
	}

}

