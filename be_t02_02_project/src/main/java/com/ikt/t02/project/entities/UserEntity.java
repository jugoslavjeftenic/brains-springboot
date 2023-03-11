package com.ikt.t02.project.entities;

public class UserEntity {

	// 1.1
	/*
	 * U paketu com.iktpreobuka.project.entities napraviti klasu UserEntity sa sledećim atributima:
	 * • id, first name, last name, username, password, email i user role
	 * • user role može da ima sledeće vrednosti: ROLE_CUSTOMER, ROLE_ADMIN i ROLE_SELLER
	 *   (koristiti enumeraciju), dok svi ostali atributi, sem id-a treba da budu tekstualnog tipa
	 */
	private Integer userID;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private EUserRole userRole;
	
	public UserEntity(Integer userID, String firstName, String lastName, String userName, String password, String email, EUserRole userRole) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	public UserEntity() {
		super();
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}
}
