package com.recipe.vo;

public class Postal {
	private String zipcode; //우편번호
	private String buildingno; //건물관리번호 pk
	private String city; //시군구
	private String doro; //도로명,건물본번-건분부번
	private String building; //빌딩명
	
	public Postal() {}
	public Postal(String zipcode, String buildingno, String city, String doro, String building) {
		super();
		this.zipcode = zipcode;
		this.buildingno = buildingno;
		this.city = city;
		this.doro = doro;
		this.building = building;
	}

	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getBuildingno() {
		return buildingno;
	}
	public void setBuildingno(String buildingno) {
		this.buildingno = buildingno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDoro() {
		return doro;
	}
	public void setDoro(String doro) {
		this.doro = doro;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	@Override
	public String toString() {
		return "Postal [zipcode=" + zipcode + ", buildingno=" + buildingno + ", city=" + city + ", doro=" + doro
				+ ", building=" + building + "]";
	}
	
	
}
