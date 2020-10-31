package com.boot.Contextify.Main.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boot.Contextify.Main.Entity.AuthorArticles;

public interface AuthorArticlesService {

	public void SaveAuthorArticles(AuthorArticles articles);
	
	List<AuthorArticles> getAllArticles();
	
	void deleteArticleById(int id);
	
	void updatePublishedStatus(String status,int id);
	
	public AuthorArticles getArticleContent(int id);
	
	List<AuthorArticles> getAllPublishedArticlesList();
	
	public int getCountofPostPublished(int id);
	
	public int getCountofPostDrafted(int id);
	
	public int getTotalCountofPostPublished();
	
	public int getTotalCountofPostDrafted();
	
	List<AuthorArticles> getListofArticlesforEditor(int id);
	
	Map<String,Integer> getPublishedDateDetails();
	
	Map<String,Integer> getArticlesPerAuthorStats();
	
}
