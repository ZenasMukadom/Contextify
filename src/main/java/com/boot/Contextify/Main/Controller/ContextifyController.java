package com.boot.Contextify.Main.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boot.Contextify.Main.Entity.AuthorArticles;
import com.boot.Contextify.Main.Entity.DataAnalytics;
import com.boot.Contextify.Main.Entity.UserRegistration;
import com.boot.Contextify.Main.Security.UserPrinciple;
import com.boot.Contextify.Main.Service.AuthorArticlesService;
import com.boot.Contextify.Main.Service.UserRegistrationService;

@Controller
public class ContextifyController {
	
	
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRegistrationService userRegistService;
	
	@Autowired
	private AuthorArticlesService articlesService;
	
	
	
	/*  <===============================  Index Page  ============================>*/
	
	@GetMapping("/")
	public String HomePage(Model model) {
		
		model.addAttribute("articlesPublished", articlesService.getAllPublishedArticlesList());
		return "index";
	}
	
	@GetMapping("/ArticleView/{id}")
	public String HomePageArticleView(@PathVariable (value="id") int id,Model model) {
		
		AuthorArticles content = new AuthorArticles();
		content = articlesService.getArticleContent(id);
		model.addAttribute("IndexArticleContent", content);	
		return "indexArticleView";
		
	}
	


	/*   <========================= Index Page with Login =========================>    */
	
	
	@RequestMapping("/login/Test")
	public String Test() {
		return "LoginErrorAlert";
	}
	

	@RequestMapping("/login1")
	public String Login() {
		return "login1";
	}
		
	
	@RequestMapping("/login1/default")
	public String LoginChanger() {
		
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
			 return "redirect:/login1/AdminPage";
		}
		return "redirect:/login1/EditorPage";
	}
	
	
	
	
	@GetMapping("/Registration")
	public String RegisterUser(Model model) {

		model.addAttribute("userRegister",new UserRegistration());
		return "UserRegister";
	}
	
	
	@PostMapping("/SubmittedUserRegistration")
	public String UserRegister(@Valid @ModelAttribute("userRegister") UserRegistration userRegist,BindingResult bindingResult) {
		
		
		if(bindingResult.hasErrors()) {
			return "UserRegister";
		}
		else {
			userRegist.setPassword(passwordEncoder.encode(userRegist.getPassword()));
			userRegistService.SaveUserRegistration(userRegist);
			return "redirect:/login1";
		}
		
	}
	
	
	
	/*   <========================================================= Editor Page with Login =================================================>    */
	
	@RequestMapping("/login1/EditorPage")
	public String EditorPage(Model model) {
		
		//To Get User Id and details
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		int userId = userRegistService.userfindByUsername(userName);
		UserRegistration updateUser = userRegistService.userfindById(userId);
		
		//Article Published & Drafted Count for each Editor
		DataAnalytics datalytics = new DataAnalytics();
		int publishedCount = articlesService.getCountofPostPublished(userId);
		int draftCount = articlesService.getCountofPostDrafted(userId);
		datalytics.setPublishedPostCount(publishedCount);
		datalytics.setDraftedPostCount(draftCount);
		model.addAttribute("countPublished", datalytics);  //pre-populate
		
		
		//List of Article Published and Drafted for each Editor
		model.addAttribute("EditorlistofArticles", articlesService.getListofArticlesforEditor(userId));
		
		
		//Editor Details
		model.addAttribute("userRegister",updateUser);  //pre-populate
		return "EditorDashboard";
	}
	
	
	//=>>>>>>>>>>>> To View the Article Content of Current Author
	@GetMapping("/login1/EditorPage/ArticleContentView/{id}")
	public String EditorArticleViewById(@PathVariable (value="id") int id,Model model) {
		AuthorArticles content = new AuthorArticles();
		
		content = articlesService.getArticleContent(id);
		model.addAttribute("ArticleContent", content);
		return "EditorArticleView";
	}
	
	
	@GetMapping("/login1/EditorPage/ProfileUpdate")
	public String showFormUpdate(Model model) {
		
		//To Get User Id and details
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		int userId = userRegistService.userfindByUsername(userName);
		UserRegistration updateUser = userRegistService.userfindById(userId);
		
		//pre-populate
		model.addAttribute("userRegister",updateUser);
		return "EditorProfile";
	}
	
	@PostMapping("/login1/EditorPage/ProfileUpdatedDB")
	public String UserDetailsUpdate(@ModelAttribute("userRegister") UserRegistration userRegist) {
		
			userRegist.setPassword(passwordEncoder.encode(userRegist.getPassword()));
			userRegistService.SaveUserRegistration(userRegist);
			return "EditorDashboard";
		
	}
	
	@RequestMapping("/login1/EditorPage/ArticleCreator")
	public String Article(@ModelAttribute("authorArticles") AuthorArticles articles,Model model) {
		
		//To Get User Id and details
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		int userId = userRegistService.userfindByUsername(userName);
		UserRegistration displayUser = userRegistService.userfindById(userId);
		
		//pre-populate
		model.addAttribute("userRegister",displayUser);
	
		return "EditorArticleCreator";
	}
	
	
	//=>>>>>>>>>>>> Editor ArticleSubmission Page
	@PostMapping("/login1/EditorPage/ArticleSubmission")
	public String submitArticle(@RequestParam("images") MultipartFile multipartFile,
			@ModelAttribute("authorArticles") AuthorArticles articles, Model model) throws IOException {
		
		//To Get User Id and details
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		int userId = userRegistService.userfindByUsername(userName);
		UserRegistration displayUser = userRegistService.userfindById(userId);
		
		//pre-populate
		model.addAttribute("userRegister",displayUser);
		
		//Converting the image and setting
		articles.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));

		//Save Articles
		articlesService.SaveAuthorArticles(articles);
		return "redirect:/login1/EditorPage/ArticleCreator";
	}
	
	
	
	/*   <======================================================= Admin Page with Login ================================================>    */
	
	
	//=>>>>>>>>>>>> Admin DashBoard
	@GetMapping("/login1/AdminPage")
	public String AdminPage(Model model) {
		
		//Total Post Published & Drafted Count 
		DataAnalytics datalytics = new DataAnalytics();
		int totalpubCount = articlesService.getTotalCountofPostPublished();
		int totaldraftCount = articlesService.getTotalCountofPostDrafted();
		
		datalytics.setTotalpublishedPostCount(totalpubCount);
		datalytics.setTotaldraftedPostCount(totaldraftCount);
		
		//Pre-Populate Bar Graph Data
		model.addAttribute("dateAnalyticsBarGraph", articlesService.getPublishedDateDetails());
		
		//Pre-Populate Doughnut Chart Data
		model.addAttribute("dateAnalyticsDoughnutChart",articlesService.getArticlesPerAuthorStats());
		
		model.addAttribute("ArticletotalCount", datalytics);
		return "AdminDashboard";
	}
	
	
	//=>>>>>>>>>>>> List of User/Author profiles in the db
	@GetMapping("/login1/AdminPage/UsersList")
	public String ListofUsers(Model model) {
		
		model.addAttribute("listofUsers",userRegistService.getAllUsers());
		return "AdminUsersList";
	}
	
	
	//=>>>>>>>>>>>> Delete the User/Author profiles from the db
	@GetMapping("/login1/AdminPage/deleteUser/{id}")
	public String deleteUserById(@PathVariable (value="id") int id) {
		this.userRegistService.deleteUserById(id);
		return "redirect:/login1/AdminPage/UsersList";
	}
	
	
	//=>>>>>>>>>>>> List of Articles of all the Authors in the db
	@GetMapping("/login1/AdminPage/ArticlesList")
	public String ListofArticles(Model model) {
		
		model.addAttribute("listofArticles", articlesService.getAllArticles());
		return "AdminArticlesList";
	}
	
	
	//=>>>>>>>>>>>> To Update the Status of draft or published of the Article of Author according to id
	@GetMapping("/login1/AdminPage/UpdatePublishStatusArticle/{id}")
	public String PublishAuthorArticleById(@PathVariable (value="id") int id) {
		this.articlesService.updatePublishedStatus("published", id);
		return "redirect:/login1/AdminPage/ArticlesList";
	}
	
	
	//=>>>>>>>>>>>> To View the Article Content of Each Author
	@GetMapping("/login1/AdminPage/ArticleContentView/{id}")
	public String PublishAuthorArticleById(@PathVariable (value="id") int id,Model model) {
		AuthorArticles content = new AuthorArticles();
		
		content = articlesService.getArticleContent(id);
	
		model.addAttribute("ArticleContent", content);
		return "AdminArticleView";
	}
	
	
	
	//=>>>>>>>>>>>> To Delete the Article of Author according to id
	@GetMapping("/login1/AdminPage/deleteArticle/{id}")
	public String deleteAuthorArticleById(@PathVariable (value="id") int id) {
		this.articlesService.deleteArticleById(id);
		return "redirect:/login1/AdminPage/ArticlesList";
	}
	
	
}
