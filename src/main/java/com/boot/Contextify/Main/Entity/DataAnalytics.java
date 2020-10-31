package com.boot.Contextify.Main.Entity;

import javax.persistence.Entity;


public class DataAnalytics {
	
	private int publishedPostCount;
	
	private int draftedPostCount;
	
	private int totalpublishedPostCount;
	
	private int totaldraftedPostCount;

	public int getPublishedPostCount() {
		return publishedPostCount;
	}

	public void setPublishedPostCount(int publishedPostCount) {
		this.publishedPostCount = publishedPostCount;
	}

	public int getDraftedPostCount() {
		return draftedPostCount;
	}

	public void setDraftedPostCount(int draftedPostCount) {
		this.draftedPostCount = draftedPostCount;
	}

	
	
	
	public int getTotalpublishedPostCount() {
		return totalpublishedPostCount;
	}

	public void setTotalpublishedPostCount(int totalpublishedPostCount) {
		this.totalpublishedPostCount = totalpublishedPostCount;
	}

	public int getTotaldraftedPostCount() {
		return totaldraftedPostCount;
	}

	public void setTotaldraftedPostCount(int totaldraftedPostCount) {
		this.totaldraftedPostCount = totaldraftedPostCount;
	}

	@Override
	public String toString() {
		return "DataAnalytics [publishedPostCount=" + publishedPostCount + ", draftedPostCount=" + draftedPostCount
				+ ", totalpublishedPostCount=" + totalpublishedPostCount + ", totaldraftedPostCount="
				+ totaldraftedPostCount + "]";
	}



}
