package com.scm.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	private String userId;
	@Column(name="user_name",nullable=false)
	private String name;
	@Column(unique = true,nullable=false)
	private String email;
	private String password;
	@Column(columnDefinition = "TEXT")
	private String about;
	@Column(columnDefinition = "TEXT")
	private String profileLink;
	private String phoneNumber;
	
	//information
	private boolean enabled=true;
	private boolean emailVerified=false;
	private boolean phoneVerified=false;
	
	//provider
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider=Provider.SELF;
	private String providerId;
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL ,fetch = FetchType.LAZY , orphanRemoval = true)
	private List<Contact> contacts=new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	List<String> roles=new ArrayList();
	
	public User() {
		super();
	}

	

	public User(String userId, String name, String email, String password, String about, String profileLink,
			String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Provider provider,
			String providerId, List<Contact> contacts, List<String> roles) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.profileLink = profileLink;
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.provider = provider;
		this.providerId = providerId;
		this.contacts = contacts;
		this.roles = roles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", profileLink=" + profileLink + ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerId=" + providerId + ", contacts=" + contacts + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
	
}
