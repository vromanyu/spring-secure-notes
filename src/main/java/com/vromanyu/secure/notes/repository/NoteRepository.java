package com.vromanyu.secure.notes.repository;

import com.vromanyu.secure.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
 List<Note> findByOwnerName(String ownerName);
}
