package com.ikt.t06.example_validation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "users_generator")
	@SequenceGenerator(name="users_generator", sequenceName = "users_sequence", allocationSize=1)
	private Integer id;
	@NotNull(message = "First name must be specified.")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} character long.")
	private String firstName;
	@NotNull(message = "Last name must be specified.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} character long.")
	private String lastName;
	@NotNull(message = "Email name must be specified.")
	@Email(message = "Email is not valid")
	private String email;
	@NotNull(message = "User name must be specified.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} character long.")
	private String username;
	@NotNull(message = "Password name must be specified.")
	@Size(min = 5, max = 15, message = "Password must be between {min} and {max} character long.")
	private String password;
	@NotNull(message = "Age must be specified.")
	@Min(value = 18, message = "You have to be at least 18 years old.")
	private Integer age;
	@Version
	private Integer version;

	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, String firstName, String lastName, String email, String username, String password,
			Integer age, Integer version) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.age = age;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
