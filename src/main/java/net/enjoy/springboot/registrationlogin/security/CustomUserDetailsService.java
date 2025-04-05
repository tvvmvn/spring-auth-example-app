package net.enjoy.springboot.registrationlogin.security;

import net.enjoy.springboot.registrationlogin.entity.User;
import net.enjoy.springboot.registrationlogin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
    User user = userRepository.findByEmail(email);

    // return user with email from client
    if (user != null) {
      // UserDetails
      return user;

    } else { // email not found, throw exception (auth failed)
      throw new UsernameNotFoundException("Invalid username or password.");
    }
  }
}