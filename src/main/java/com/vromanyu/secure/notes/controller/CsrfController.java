package com.vromanyu.secure.notes.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/api/csrf")
public class CsrfController {

 @GetMapping
 public CsrfToken getToken(HttpServletRequest request){
  return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
 }
}
