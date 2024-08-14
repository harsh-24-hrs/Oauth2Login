package com.scm.config;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entity.Provider;
import com.scm.entity.User;
import com.scm.repo.UserRepo;
import com.scm.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private UserRepo repo;
	Logger log=LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		log.info("Authentication Successful");
		//fetch user data
		OAuth2User oauthUser=(OAuth2User) authentication.getPrincipal();
		OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
		String provider=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		
//		setting common properties of user
		User user1=new User();
		user1.setEmailVerified(true);
		user1.setEnabled(true);
		user1.setRoles(List.of("ROLE_USER"));
		
		
		oauthUser.getAttributes().forEach((k,v)->
		log.info("{} -> {}",k,v));

		if(provider.equalsIgnoreCase("Google")) {
			
			log.info(oauthUser.getName());
			user1.setAbout("this user is from google");
			log.info(oauthUser.getAuthorities().toString());
			oauthUser.getAttributes().forEach((k,v)->
			log.info("{} -> {}",k,v));
			
			//save user to database
			
			user1.setProviderId(oauthUser.getName());
			user1.setEmail(oauthUser.getAttribute("email"));
			user1.setName(oauthUser.getAttribute("name"));
			user1.setProvider(Provider.GOOGLE);
			user1.setProfileLink(oauthUser.getAttribute("picture"));
			
			
		}
		else if(provider.equalsIgnoreCase("github")) {
			String email=oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email"):oauthUser.getAttribute("login")+"@gmail.com";
			user1.setEmail(email);
			user1.setAbout("this user is from github");
			user1.setName(oauthUser.getAttribute("login"));
			user1.setProfileLink(oauthUser.getAttribute("avatar_url"));
			user1.setProviderId(oauthUser.getAttribute("id").toString());
			user1.setProvider(Provider.GITHUB);
			
			
		}
		else {
			log.info("Invalid user : no provider");
			new DefaultRedirectStrategy().sendRedirect(request,response,"/login");
			return;
		}
		//save user
		User user2=repo.findByEmail(user1.getEmail());
		if(user2==null) {
			repo.save(user1);
		}
		new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
		
	}

}
