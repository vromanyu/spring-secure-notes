package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.entity.AppNote;
import com.vromanyu.secure.notes.repository.AppNoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppNoteServiceImpl implements AppNoteService {

 private final AppNoteRepository appNoteRepository;

 @Override
 public AppNote createNoteForUser(String username, String content) {
  AppNote appNote = new AppNote(username, content);
  return appNoteRepository.save(appNote);
 }

 @Override
 public AppNote updateNoteForUser(Integer id, String content, String username) {
  AppNote appNote = appNoteRepository.findById(id).orElseThrow(() -> new RuntimeException("AppNote not found"));
  appNote.setContent(content);
  return appNoteRepository.save(appNote);
 }

 @Override
 public void deleteNoteForUser(Integer id, String username) {
  appNoteRepository.deleteById(id);
 }

 @Override
 public List<AppNote> getNotesForUser(String username) {
  return appNoteRepository.findByOwnerName(username);
 }

}
