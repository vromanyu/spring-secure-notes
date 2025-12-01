package com.vromanyu.secure.notes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Note {

 @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

 @Lob
 private String content;

 private String ownerName;

 @CreationTimestamp
 private LocalDateTime creationDate;

 @UpdateTimestamp
 private LocalDateTime updateDate;

 public Note(String username, String content){
  this.ownerName = username;
  this.content = content;
 }
}
