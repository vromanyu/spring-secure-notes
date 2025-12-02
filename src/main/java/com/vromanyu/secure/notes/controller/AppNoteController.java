package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.entity.AppNote;
import com.vromanyu.secure.notes.service.AppNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notes", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class AppNoteController {

 private final AppNoteService appNoteService;

 @PostMapping
 public AppNote createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return appNoteService.createNoteForUser(username, content);
 }

 @GetMapping
 public List<AppNote> getAllNotes(@AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return appNoteService.getNotesForUser(username);
 }

 @PutMapping("/{id}")
 public AppNote updateNote(@PathVariable Integer id, @RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return appNoteService.updateNoteForUser(id, content, username);
 }

 @DeleteMapping("/{id}")
 public void deleteNote(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  appNoteService.deleteNoteForUser(id, username);
 }

}
