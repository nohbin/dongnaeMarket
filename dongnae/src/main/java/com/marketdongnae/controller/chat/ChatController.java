package com.marketdongnae.controller.chat;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketdongnae.security.CustomUserDetails;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class ChatController {

	@GetMapping("/chat")
	public void chat(Model model) {
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("==================================");
		log.info("@ChatController, GET Chat / Username : " + user.getUsername());
		
		model.addAttribute("userid" , user.getUsername());
	}
}
