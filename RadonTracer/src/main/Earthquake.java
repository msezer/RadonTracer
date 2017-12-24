package main;

import java.util.Date;

public class Earthquake {
	
	public Date eDate;
	public Double eMe;
	
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	public Double geteMe() {
		return eMe;
	}
	public void seteMe(Double eMe) {
		this.eMe = eMe;
	}

}
