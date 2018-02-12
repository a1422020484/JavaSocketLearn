package cn.saturn.web.center.dao;

import java.util.Date;

public class GamesModel {
	private int id;
	private String games;
	private Date times;
	private double rate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGames() {
		return games;
	}
	public void setGames(String games) {
		this.games = games;
	}
	public Date getTimes() {
		return times;
	}
	public void setTimes(Date times) {
		this.times = times;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public GamesModel() {
		super();
	}
	public GamesModel(int id, String games, Date times, double rate) {
		super();
		this.id = id;
		this.games = games;
		this.times = times;
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "GamesModel [id=" + id + ", games=" + games + ", times=" + times + ", rate=" + rate + "]";
	}
	
	
	
}
