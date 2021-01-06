package com.wurigo.socialService.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordXY {
	private String LocX;
	private String LocY;
	
	
	@Override
	public String toString() {
		return "CoordXY [getLocX()=" + getLocX() + ", getLocY()=" + getLocY() + "]";
	}
	public String getLocX() {
		return LocX;
	}
	public void setLocX(String locX) {
		LocX = locX;
	}
	public String getLocY() {
		return LocY;
	}
	public void setLocY(String locY) {
		LocY = locY;
	}
	
}
