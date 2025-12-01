package com.vromanyu.secure.notes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes/admin")
public class AdminController {

 private final JdbcClient jdbcClient;

 private static final String NOTES_COUNT_SQL = "select count(*) from note";

 @PostMapping(value = "/truncate", produces = "text/plain")
 public String truncateNoteTable(){
  long count = jdbcClient.sql(NOTES_COUNT_SQL).query(Long.class).single();
  jdbcClient.sql("truncate table note").update();
  return "truncated " + count + " rows";
 }
}
