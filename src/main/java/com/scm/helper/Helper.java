package com.scm.helper;

import java.security.Principal;

import org.aspectj.weaver.ast.Instanceof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
	public static String getEmailOfLoggedUser(Authentication authentication) {
		
		Logger log=LoggerFactory.getLogger(Helper.class);
		
		String username="";
		if(authentication instanceof OAuth2AuthenticatedPrincipal) {
			OAuth2AuthenticationToken auth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
			String provider=auth2AuthenticationToken.getAuthorizedClientRegistrationId();
			OAuth2User oauthUser=(OAuth2User)authentication.getPrincipal();
			if(provider.equalsIgnoreCase("google")) {
				username= oauthUser.getAttribute("email");
				log.info("from google username{}",username);
			}
			else if(provider.equalsIgnoreCase("github")) {
				username= oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email"):oauthUser.getAttribute("login")+"@gmail.com";
				log.info("from github username is {}",username);
				
			}
		}
		else {
			username= authentication.getName();
			log.info("from database {}",username);
		}
		return username;
	}
}
