package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.entity.AppNote;

import java.util.List;

public interface AppNoteService {
 AppNote createNoteForUser(String username, String content);
 AppNote updateNoteForUser(Integer id, String content, String username);
 void deleteNoteForUser(Integer id, String username);
 List<AppNote> getNotesForUser(String username);
}
