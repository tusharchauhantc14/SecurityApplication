package com.tushar.SecurityApp.SecurityApplication.controller;

import com.tushar.SecurityApp.SecurityApplication.dto.LoginDto;
import com.tushar.SecurityApp.SecurityApplication.dto.LoginResponseDto;
import com.tushar.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.tushar.SecurityApp.SecurityApplication.dto.UserDto;
import com.tushar.SecurityApp.SecurityApplication.services.AuthService;
import com.tushar.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto=  userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);
//        String accessToken= loginResponseDto.getAccessToken();
//        String refreshToken=loginResponseDto.getRefreshToken();

        Cookie cookie= new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(deployEnv.equals("production"));
        response.addCookie(cookie);
        
        return ResponseEntity.ok(loginResponseDto);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh Token not found inside the cookie"));
        LoginResponseDto loginResponseDto= authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDto);
    }

}
