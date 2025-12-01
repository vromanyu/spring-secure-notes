package com.vromanyu.secure.notes.service;

import com.vromanyu.secure.notes.entity.Note;

import java.util.List;

public interface NoteService {
 Note createNoteForUser(String username, String content);
 Note updateNoteForUser(Integer id, String content, String username);
 void deleteNoteForUser(Integer id, String username);
 List<Note> getNotesForUser(String username);
}
