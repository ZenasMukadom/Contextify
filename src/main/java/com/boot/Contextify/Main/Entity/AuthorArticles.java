package com.boot.Contextify.Main.Entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;



@Entity
@Table(name="AuthorArticles")
public class AuthorArticles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="tags")
	private String tags;
	
	@Lob
	@Column(name="image", columnDefinition="MEDIUMBLOB")
	private String image;
	
	@Column(name="content", columnDefinition="TEXT")
	private String content;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="publishedDate",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date publishedDate;
	
	@Column(name="articleStatus")
	private String articleStatus="draft";
	
	@ManyToOne
	@JoinColumn(name = "userId_fkid", referencedColumnName = "id")
	private UserRegistration userId_fkid;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}



	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(String articleStatus) {
		this.articleStatus = articleStatus;
	}

	public UserRegistration getUserId_fkid() {
		return userId_fkid;
	}

	public void setUserId_fkid(UserRegistration userId_fkid) {
		this.userId_fkid = userId_fkid;
	}

	@Override
	public String toString() {
		return "Articles [id=" + id + ", title=" + title + ", tags=" + tags + ", image=" + image + ", content="
				+ content + ", publishedDate=" + publishedDate + ", articleStatus=" + articleStatus + ", userId_fkid="
				+ userId_fkid + "]";
	}
	
	
}
