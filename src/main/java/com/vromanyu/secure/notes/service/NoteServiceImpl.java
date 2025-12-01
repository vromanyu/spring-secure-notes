package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.entity.Note;
import com.vromanyu.secure.notes.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

 private final NoteRepository noteRepository;

 @Override
 public Note createNoteForUser(String username, String content) {
  Note note = new Note(username, content);
  return noteRepository.save(note);
 }

 @Override
 public Note updateNoteForUser(Integer id, String content, String username) {
  Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
  note.setContent(content);
  return noteRepository.save(note);
 }

 @Override
 public void deleteNoteForUser(Integer id, String username) {
  noteRepository.deleteById(id);
 }

 @Override
 public List<Note> getNotesForUser(String username) {
  return noteRepository.findByOwnerName(username);
 }

}
