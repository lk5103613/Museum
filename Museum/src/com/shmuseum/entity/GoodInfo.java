package com.shmuseum.entity;

import java.io.Serializable;

public class GoodInfo implements Serializable {

	private int imageId;
	private String title;
	private String intro;
	private String size;
	private String price;

	public GoodInfo() {
		super();
	}

	public GoodInfo(int imageId, String title, String intro) {
		super();
		this.imageId = imageId;
		this.title = title;
		this.intro = intro;
	}

	public GoodInfo(int imageId, String title, String intro, String size,
			String price) {
		super();
		this.imageId = imageId;
		this.title = title;
		this.intro = intro;
		this.size = size;
		this.price = price;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
