package com.boot.Contextify.Main.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.Contextify.Main.Entity.AuthorArticles;
import com.boot.Contextify.Main.Repository.AuthorArticlesRepository;

@Service
public class AuthorArticlesServiceImpl implements AuthorArticlesService {
	
	@Autowired
	private AuthorArticlesRepository articlesRepo;

	@Override
	public void SaveAuthorArticles(AuthorArticles articles) {

		this.articlesRepo.save(articles);
	}

	@Override
	public List<AuthorArticles> getAllArticles() {
		
		return articlesRepo.findAll();
	}

	@Override
	public void deleteArticleById(int id) {
		
		this.articlesRepo.deleteById(id);
	}

	@Override
	public void updatePublishedStatus(String status, int id) {
	
		this.articlesRepo.setStatusForPublishedArticle(status, id);
	}

	@Override
	public AuthorArticles getArticleContent(int id) {
		
		Optional<AuthorArticles> articles= articlesRepo.findById(id);
		AuthorArticles content = null;
		content = articles.get();
		return content;
	}

	@Override
	public List<AuthorArticles> getAllPublishedArticlesList() {
		
		return articlesRepo.getPublishedArticlesOnly();
	}

	@Override
	public int getCountofPostPublished(int id) {
		
		int a = articlesRepo.getNumberofPostPublished(id); 
		return a;
	}

	@Override
	public int getCountofPostDrafted(int id) {
		
		int a = articlesRepo.getNumberofPostDrafted(id);
		return a;
	}

	@Override
	public int getTotalCountofPostPublished() {
		
		return articlesRepo.getTotalNumberofPostPublished();
	}

	@Override
	public int getTotalCountofPostDrafted() {
		
		return articlesRepo.getTotalNumberofPostDrafted();
	}

	@Override
	public List<AuthorArticles> getListofArticlesforEditor(int id) {
		
		return articlesRepo.getListofArticlesforEditor(id);
	}

	@Override
	public Map<String,Integer> getPublishedDateDetails() {
		
		List<Date> timeanalytics = new ArrayList<Date>(articlesRepo.getListofDatesPublished());
		Map<String,Integer> dateMap = new HashMap<String,Integer>();
		
		
		int pre_month = 0;
		int pre_year = 0;
		int n = timeanalytics.size();
		int articleCount = 0;
		
		for(int i=0;i<n;i++) {

			LocalDate localDate = LocalDate.fromDateFields(timeanalytics.get(i));
			int day = localDate.getDayOfMonth();
			int month = localDate.getMonthOfYear();
			int year = localDate.getYear();
			String strMonth = Month.of(month).name();
			
			
			
			if(pre_month == month && pre_year == year) {
				dateMap.replace(strMonth, articleCount, articleCount +=1);
			}
			else {
				articleCount = 1;
				dateMap.put(strMonth, articleCount);
				pre_month = month;
				pre_year = year;
				
			}
			
		}
		
		
		return dateMap;
	}

	@Override
	public Map<String,Integer> getArticlesPerAuthorStats() {
		
		List<String> articlesStats = new ArrayList<String>(articlesRepo.getArticlesPerAuthor());
		
		Map<String,Integer> articledataMap = new HashMap<String,Integer>();
		
		int n = articlesStats.size();

		for(int i=0;i<n;i++) {
			String[] str = articlesStats.get(i).split("\\,");
			String username = str[0];
			Integer number = Integer.parseInt(str[1]);
			articledataMap.put(username, number);
		}
		
		
		return articledataMap;
		
	}

	




}
