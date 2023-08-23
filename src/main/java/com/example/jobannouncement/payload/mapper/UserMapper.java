package com.example.jobannouncement.payload.mapper;

import com.example.jobannouncement.model.User;
import com.example.jobannouncement.payload.request.UserRequest;
import com.example.jobannouncement.payload.response.UserRegistrationResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User requestToEntity(UserRequest request){
    User user = User.builder()
        .name(request.getName())
        .surname(request.getSurname())
        .email(request.getEmail())
        .password(request.getPassword())
        .build();
    return user;
  }
  public UserRegistrationResponse entityToResponse(User user){
    UserRegistrationResponse response = UserRegistrationResponse.builder()
        .name(user.getName())
        .surname(user.getSurname())
        .build();
    return response;
  }

}
