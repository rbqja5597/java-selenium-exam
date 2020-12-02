package com.sbs.selenium.exam;

public class DCInsideArticle {
	
	private String title;
	private String writing;
	private String lede;


	public DCInsideArticle(String title, String writing, String lede) {

		this.title = title;
		this.writing = writing;
		this.lede = lede;

	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getWriting() {
		return writing;
	}


	public void setWriting(String writing) {
		this.writing = writing;
	}


	public String getLede() {
		return lede;
	}


	public void setLede(String lede) {
		this.lede = lede;
	}






}
