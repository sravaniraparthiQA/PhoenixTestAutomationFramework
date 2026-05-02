package com.api.utils;

import java.time.Duration;

import com.api.constant.Role;

public class AuthTokenDemoRunner {

	public static void main(String[] args) throws InterruptedException {
		
		for(int i=0;i<=5;i++) {
		String token = AuthTokenProvider.getToken(Role.FD);
//		Thread.sleep(2000);
		System.out.println(token);
		}
		

	}

}
