package com.vromanyu.secure.notes.repository;

import com.vromanyu.secure.notes.entity.AppNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppNoteRepository extends JpaRepository<AppNote, Integer> {
 List<AppNote> findByOwnerName(String ownerName);
}
