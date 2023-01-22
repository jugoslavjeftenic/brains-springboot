package com.ikt.t4.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_sequence", allocationSize=1)
    private Long id;
	@Column(nullable=false, length=64)
    private String firstName;
	@Column(nullable=false, length=64)
    private String lastName;
	@Column(nullable=false, length=64)
    private String username;
	@Column(nullable=false, length=64)
    private String password;
	@Column(nullable=false, length=128)
    private String email;
	@Column(nullable=false)
    private EUserRole userRole;
	@Version
	private Integer version;
}
