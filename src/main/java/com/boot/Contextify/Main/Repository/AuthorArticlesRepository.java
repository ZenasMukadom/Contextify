package com.boot.Contextify.Main.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.boot.Contextify.Main.Entity.AuthorArticles;

public interface AuthorArticlesRepository extends JpaRepository<AuthorArticles, Integer> {

	@Transactional
	@Modifying
	@Query("update AuthorArticles art set art.articleStatus = :status where art.id = :id")
	public void setStatusForPublishedArticle(@Param("status") String articleStatus, @Param("id") int id);
	
	
	//Home Page 
	@Transactional
	@Query("Select a from AuthorArticles a where articleStatus='published'")
	public List<AuthorArticles> getPublishedArticlesOnly();
	
	
	//Editor-Side Data Analytics 
	@Transactional
	@Query("Select COUNT(at.userId_fkid) from AuthorArticles at where at.articleStatus='published'  AND at.userId_fkid.id = :id")
	public int getNumberofPostPublished(@Param("id") int id);
	
	//Editor-Side Data Analytics 
	@Transactional
	@Query("Select COUNT(at.userId_fkid) from AuthorArticles at where at.articleStatus='draft'  AND at.userId_fkid.id = :id")
	public int getNumberofPostDrafted(@Param("id") int id);
	
	
	//Editor-Side List of Articles per Editor (Published and Drafted)
	@Transactional
	@Query("Select art from AuthorArticles art where art.userId_fkid.id = :id")
	public List<AuthorArticles> getListofArticlesforEditor(@Param("id") int id);
	
	
	//Admin-Side Data Analytics 
	@Transactional
	@Query("Select COUNT(at.id) from AuthorArticles at where at.articleStatus='published'")
	public int getTotalNumberofPostPublished();
	
	
	//Admin-Side Data Analytics
	@Transactional
	@Query("Select COUNT(at.id) from AuthorArticles at where at.articleStatus='draft'")
	public int getTotalNumberofPostDrafted();
	
	//Admin-Side Bar Graph Analytics
	@Transactional
	@Query("Select at.publishedDate from AuthorArticles at where at.articleStatus='published' ORDER BY at.publishedDate asc")
	public List<Date> getListofDatesPublished();
	
	//Admin-Side Doughnut Chart Analytics
	@Transactional
	@Query("Select at.userId_fkid.username, Count(at) from AuthorArticles at where at.articleStatus='published' GROUP BY at.userId_fkid.username")
	public List<String> getArticlesPerAuthor();
	
}
