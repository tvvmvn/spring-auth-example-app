package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.dto.UserDto;
import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  // Save an user
  public void saveUser(UserDto userDto) {

    User user = new User();

    user.setName(userDto.getFirstName() + " " + userDto.getLastName());
    user.setEmail(userDto.getEmail());
    // encrypt the password using spring security
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    userRepository.save(user);
  }

  // find an user by email
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  // find all users
  public List<UserDto> findAllUsers() {

    List<User> users = userRepository.findAll();
    
    return users.stream().map((user) -> convertEntityToDto(user))
        .collect(Collectors.toList());
  }

  // Conver Entity to DTO
  private UserDto convertEntityToDto(User user) {
    
    UserDto userDto = new UserDto();
    
    String[] name = user.getName().split(" ");

    userDto.setFirstName(name[0]);
    userDto.setLastName(name[1]);
    userDto.setEmail(user.getEmail());

    return userDto;
  }
}
