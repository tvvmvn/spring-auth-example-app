package net.enjoy.springboot.registrationlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;
  
  public User() {};

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override // 권한 반환
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("user"));
  }

  // 계정 만료 여부 반환
  @Override
  public boolean isAccountNonExpired() {
    return true; 
  }

  // 계정 잠금 여부 반환
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // 패스워드 만료 여부 반환
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // 계정 사용 가능 여부 변환
  @Override
  public boolean isEnabled() {
    return true;
  }
}