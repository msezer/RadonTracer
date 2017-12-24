package main;

import java.util.Date;

public class Soil {
	
	public Soil(Date sDate, Double sObserv) {
		super();
		this.sDate = sDate;
		this.sObserv = sObserv;
	}
	public Date sDate;
	public Double sObserv;
	
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public Double getsObserv() {
		return sObserv;
	}
	public void setsObserv(Double sObserv) {
		this.sObserv = sObserv;
	}
}
