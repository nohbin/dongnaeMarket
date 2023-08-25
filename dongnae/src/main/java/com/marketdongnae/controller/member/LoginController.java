package com.marketdongnae.controller.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/")
public class LoginController {
	
	@GetMapping("login")
	public String login(HttpServletRequest request) {
		System.out.println(222);
		String uri = request.getHeader("Referer");
		if(uri != null && uri.contains("/login")) {
			request.getSession().setAttribute("prevPage", uri);
		}
		return "member/login";
	}
	
	@GetMapping("loginFail")
	public String loginFail(Model model) { 
		model.addAttribute("message", "아이디와 비밀번호를 확인해주세요.");
		return "member/login";
	}
	
	@GetMapping("logout")
	public String logout( ) {
		return "redirect:/login";
	}

}