package com.api.utils;

import static com.api.constant.Role.ENG;
import static com.api.constant.Role.FD;
import static com.api.constant.Role.QC;
import static com.api.constant.Role.SUP;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private static Map<Role,String> tokenCache = new ConcurrentHashMap<Role, String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);

	private AuthTokenProvider() {
		//Private Constructor!!
	}

	public static String getToken(Role role) {
		
		LOGGER.info("Checking if the token for role {} is present in the cache", role);
		if(tokenCache.containsKey(role)) {
			LOGGER.info("Token found for role {}",role);
			return tokenCache.get(role);
		}
		LOGGER.info("Token not found making the login request for the role {}", role);
		
		UserCredentials userCredentials = null; 
		
		if(role == FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		}
		
		else if(role == SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		}
		
		else if(role == ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		}
		
		else if(role == QC) {
			userCredentials = new UserCredentials("iamqc", "password");
		}
		
		String token= given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.contentType(ContentType.JSON)
			.body(userCredentials)
		.when()
			.post("login")
		.then()
			.log()
			.ifValidationFails()
			.statusCode(200)
			.body("message", Matchers.equalTo("Success"))
			
			.extract()
			.body()
			.jsonPath()
			.getString("data.token");
		
		LOGGER.info("Token cached for future request");
		tokenCache.put(role, token);
		
		return token;

	}

}
