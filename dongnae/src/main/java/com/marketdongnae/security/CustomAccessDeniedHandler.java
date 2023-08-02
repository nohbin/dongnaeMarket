package com.marketdongnae.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.marketdongnae.dto.JSONLoginDto;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	
	log.error("Access Denied Handler");
	
	log.error("Redirect...");
	
	response.sendRedirect("/accessError");
	
//	// ajax security 테스트 중
//	JSONLoginDto jsonLoginDto = new JSONLoginDto() ; 
//	jsonLoginDto.setResult("success");
//	jsonLoginDto.setMessage("success");
//	AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint = new AjaxAuthenticationEntryPoint("/member/login");
	}
}