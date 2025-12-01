package com.vromanyu.secure.notes.controller;

import com.vromanyu.secure.notes.entity.Note;
import com.vromanyu.secure.notes.service.NoteService;
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
public class NoteController {

 private final NoteService noteService;

 @PostMapping
 public Note createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return noteService.createNoteForUser(username, content);
 }

 @GetMapping
 public List<Note> getAllNotes(@AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return noteService.getNotesForUser(username);
 }

 @PutMapping("/{id}")
 public Note updateNote(@PathVariable Integer id, @RequestBody String content, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  return noteService.updateNoteForUser(id, content, username);
 }

 @DeleteMapping("/{id}")
 public void deleteNote(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
  String username = userDetails.getUsername();
  noteService.deleteNoteForUser(id, username);
 }

}
