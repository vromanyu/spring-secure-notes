package com.vromanyu.secure.notes.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes/admin")
public class AdminController {

 private final JdbcTemplate jdbcTemplate;

 private static final String NOTES_COUNT_SQL = "select count(*) from note";
 private static final String NOTES_TRUNCATE = "truncate table note";

 @PostMapping(value = "/truncate", produces = "text/plain")
 @Transactional
 public String truncateNoteTable() {
  Long count = jdbcTemplate.query(NOTES_COUNT_SQL, res -> {
   if (res.next()) {
    return res.getLong(1);
   } else {
    return null;
   }
  });
  jdbcTemplate.update(NOTES_TRUNCATE);
  return "truncated " + count + " rows";
 }
}
