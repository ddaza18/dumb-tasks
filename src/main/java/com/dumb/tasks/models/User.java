package com.dumb.tasks.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@JsonSerialize(as = User.class)
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String loginUser;	
	private String fullName;
	private String mail;
	private Date creationDate;
	private Date updateDate;
	private String password;
	private String phone;
	
	@ElementCollection
	private Set<String> roles; //Almacena los roles alojados dentro de la aplicación

}
