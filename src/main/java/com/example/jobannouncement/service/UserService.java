package com.example.jobannouncement.service;

import com.example.jobannouncement.core.email.EmailService;
import com.example.jobannouncement.exception.UserNotFoundException;
import com.example.jobannouncement.model.User;
import com.example.jobannouncement.payload.mapper.UserMapper;
import com.example.jobannouncement.payload.request.UserRequest;
import com.example.jobannouncement.payload.response.UserRegistrationResponse;
import com.example.jobannouncement.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final EmailService service;



  //USER REGISTRATION
  public UserRegistrationResponse registration(UserRequest request) {

    User user = userMapper.requestToEntity(request);
    user.setIsActive(false);
    User save = userRepository.save(user);
    String link = "http://localhost:8000/v1/users/activate/" + user.getEmail();
    service.sendSimpleEmail(user.getEmail(),
        link,
        "Activate Link");
    return userMapper.entityToResponse(save);
  }

  public String activateProfile(String email) {
    User user = userRepository
        .findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    user.setIsActive(true);
    return "Successfully Activate Profile";
  }


  public User forgotPassword(String email) {
    User user = userRepository
        .findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    return user;
  }


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles()
        .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), getAuthority(user));
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    });
    return authorities;
  }

}
