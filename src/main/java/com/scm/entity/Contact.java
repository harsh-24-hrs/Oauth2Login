package com.scm.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	
	@Id
	private String contactId;
	
	private String name;
	private String email;
	private String description;
	private String address;
	private boolean favourite=false;
	private String linkedInLink;
	private String phoneNumber;
	private String picture;
	
	@ManyToOne
	private User user;
	

}
