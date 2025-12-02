package com.vromanyu.secure.notes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppRole {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

 @Enumerated(EnumType.STRING)
 private AppRoleEnum role;

 @OneToMany(mappedBy = "appRole", cascade = CascadeType.MERGE)
 private Set<AppUser> users = new HashSet<>();

 public AppRole(AppRoleEnum role) {
  this.role = role;
 }

}
