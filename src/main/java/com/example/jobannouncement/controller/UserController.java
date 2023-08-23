package com.example.jobannouncement.controller;


import com.example.jobannouncement.core.configs.JwtResponse;
import com.example.jobannouncement.core.configs.JwtUtil;
import com.example.jobannouncement.payload.request.LoginRequest;
import com.example.jobannouncement.payload.request.UserRequest;
import com.example.jobannouncement.payload.response.UserRegistrationResponse;
import com.example.jobannouncement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;


  //USER REGISTRATION
  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> register(
      @RequestBody UserRequest userRequest) {
    return ResponseEntity.ok(userService.registration(userRequest));
  }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
    try {
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
              loginRequest.getPassword()));

    } catch (UsernameNotFoundException usernameNotFoundException) {
      usernameNotFoundException.printStackTrace();
      throw new Exception("Bad Credential");
    }

    UserDetails userDetails = this.userService.loadUserByUsername(loginRequest.getEmail());

    String token = this.jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }


}

