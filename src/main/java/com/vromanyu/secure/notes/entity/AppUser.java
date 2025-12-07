package com.vromanyu.secure.notes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(schema = "spring_security")
public class AppUser {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

 @NotBlank
 @Column(unique = true)
 private String username;

 @NotBlank
 @Email
 @Column(unique = true)
 private String email;

 @JsonIgnore
 private String password;

 private boolean nonLocked = true;
 private boolean nonExpired = true;
 private boolean credentialsNonExpired = true;
 private boolean enabled = true;

 private LocalDate credentialsExpiryDate;
 private LocalDate accountExpiryDate;

 private String twoFactorSecret;
 private boolean isTwoFactorEnabled = false;
 private String signUpMethod;

 @ManyToOne(cascade = CascadeType.MERGE)
 @JoinColumn
 @ToString.Exclude
 private AppRole appRole;

 @CreationTimestamp
 private LocalDateTime creationDate;

 @UpdateTimestamp
 private LocalDateTime updateDate;
}
