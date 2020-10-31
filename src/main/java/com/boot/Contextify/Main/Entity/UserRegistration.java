package com.boot.Contextify.Main.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name="UserRegistration")
public class UserRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	@Size(max=10,min=3)
	@NotEmpty(message="Please Enter name")
	private String username;
	
	@Column(name="firstname")
	@NotEmpty(message="Please Enter First name")
	private String firstname;
	
	@Column(name="lastname")
	@NotEmpty(message="Please Enter Last name")
	private String lastname;
	
	@Column(name="email")
	@NotEmpty(message="Please Enter Email Id")
    @Email(message = "Please enter a valid e-mail address")
	private String email;
	
	@Column(name="password")
	@NotEmpty(message = "Please enter password")
	private String password;

	
	@Column(name="role")
	private String role;
	
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getFirstname() {
		return firstname;
	}




	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}




	public String getLastname() {
		return lastname;
	}




	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getRole() {
		return role;
	}




	public void setRole(String role) {
		this.role = role;
	}




	@Override
	public String toString() {
		return "UserRegistration [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}
	
	
	
}
