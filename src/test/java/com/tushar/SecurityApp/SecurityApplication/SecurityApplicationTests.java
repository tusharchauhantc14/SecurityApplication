package com.tushar.SecurityApp.SecurityApplication;

import com.tushar.SecurityApp.SecurityApplication.entities.User;
import com.tushar.SecurityApp.SecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user= new User(4L, "tushar@gmail.com","1234","Tushar");
		String token= jwtService.generateAccessToken(user);
		System.out.println(token);
		Long id= jwtService.getUserIdFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJ0dXNoYXJAZ21haWwuY29tIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImlhdCI6MTczOTA5NjgzMSwiZXhwIjoxNzM5MDk2ODkxfQ.FvulXPMRPXdkjn-5iSNrx-Xp_AabZa0nD_57XQgXIzxCamoPIcX9cFn2smhqmHhJvW5M5ejFqV3UtQx_fKNa6A");
		System.out.println(id);

	}

}
