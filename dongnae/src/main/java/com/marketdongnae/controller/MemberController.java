package com.marketdongnae.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.marketdongnae.security.CustomAuthenticationProvider;
import com.marketdongnae.security.CustomUserDetails;
import com.marketdongnae.service.MemberService;


@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	@Autowired 
	CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/member/detail")
	public ModelAndView detail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		Map<String, Object> member =  memberService.getMember(m_id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("isFaile", false);
		mv.addObject("member", member);
		mv.setViewName("/member/detail");
		return mv;		
	}
	
	@PostMapping("/member/update")
	public ModelAndView modify(@RequestParam Map<String, Object> map) {		
		ModelAndView mv = new ModelAndView();
		memberService.updateMember(map);
		mv.setViewName("/home");
		return mv;		
	}
	
	@GetMapping("/member/changePassword")
	public ModelAndView changePassword(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		mv.addObject("m_id", m_id);
		return mv;	
	}
	
	@PostMapping("/member/changePassword")
	public ModelAndView changePassword_post(@RequestParam Map<String, Object> map
			, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		Map<String, Object> member =  memberService.getMember(m_id);
		
		// 입력한 현재 비밀번호가 맞는지 확인
		String rawPwd = (String) map.get("current_password"); 
		String encodedPwd = (String) member.get("m_pwd");
		if( !passwordEncoder.matches(rawPwd , encodedPwd) ) {
			mv.addObject("password", "wrongCurrent");
			mv.setViewName("/member/changePassword");
			return mv;
		};
		
		// 새 비밀번호와 확인이 일치하는지 확인
		String newPwd = (String) map.get("new_password");
		String confirmPwd = (String) map.get("new_password_confirm");
		if( !newPwd.equals(confirmPwd) ) {
			mv.addObject("password", "WrongConfirm");
			mv.setViewName("/member/changePassword");
			return mv;
		};
		
		// 새 비밀번호로 변경
		String newEncodePwd = passwordEncoder.encode(newPwd);
		map.put("new_password", newEncodePwd);
		memberService.changePassword(map);
		
		mv.setViewName("redirect:/");
		return mv;		
	}
	
	@GetMapping("/member/point")
	public ModelAndView review(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		// 기능구현 전이라 일단 고정 값으로 테스트 중 : b_id
		String b_id = "1";
		Map<String, Object> member =  memberService.getMember(m_id);
		Map<String, Object> buy =  memberService.getBuy(b_id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("member", member);
		mv.addObject("buy", buy);
		mv.setViewName("/member/point");
		return mv;		
	}
	
	@GetMapping("/member/buyList")
	public ModelAndView buyList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		Map<String, Object> member =  memberService.getMember(m_id);
		int m_number = (int) member.get("m_number");
		List<Map<String, Object>> buyList =  memberService.getBuyList(m_number);
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyList", buyList);
		mv.setViewName("/member/buyList");
		return mv;		
	}
	
	
	@GetMapping("/member/soldList")
	public ModelAndView soldList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		Map<String, Object> member =  memberService.getMember(m_id);
		int m_number = (int) member.get("m_number");
		List<Map<String, Object>> soldList =  memberService.getSoldList(m_number);
		ModelAndView mv = new ModelAndView();
		mv.addObject("isFaile", false);
		mv.addObject("soldList", soldList);
		mv.setViewName("/member/soldList");
		return mv;		
	}
	
	@GetMapping("/member/review")
	public ModelAndView getReviewList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id =  (String) session.getAttribute("m_id");
		Map<String, Object> member =  memberService.getMember(m_id);
		int m_number = (int) member.get("m_number");
		List<Map<String, Object>> soldList =  memberService.getSoldList(m_number);
		int sum = 0 ;
		for (Map<String, Object> sold : soldList) {
			sum += (int) sold.get("s_score");
		}
		int avgScore = (int) Math.ceil(sum/ soldList.size()) ;
		ModelAndView mv = new ModelAndView();
		mv.addObject("soldList", soldList);
		mv.addObject("avgScore", avgScore);
		mv.setViewName("/member/review");
		return mv;		
	}
	
	@GetMapping("/member/login")
	public ModelAndView login() {
		return new ModelAndView("member/login");
	}
	
	@GetMapping("/member/loginFail")
	public ModelAndView loginFail() { 
		// 나중에 ajax로 바꿀 예정 
		ModelAndView mv = new ModelAndView();
		mv.addObject("login", "fail");
		mv.setViewName("/member/login");
		return mv;
	}
	
	// /member/loginSuccess는 CustomLoginSuccessHandler에서 이동함
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mv.setViewName("redirect:/"); // 왜 홈화면이 아니라 로그인창으로 연결되지?
		return mv;
	}
	
	@GetMapping("/member/regist")
	public ModelAndView regist() {
		return new ModelAndView("member/regist");
	}
	
	@PostMapping("/member/regist")
	public ModelAndView regist_post(@RequestParam Map<String, Object> map) {
		System.out.println("가입하기 POST");
		ModelAndView mv = new ModelAndView();
		String pwd1 = (String) map.get("m_pwd");
		String pwd2 = passwordEncoder.encode(pwd1);
		map.put("m_pwd", pwd2);
		memberService.regist(map);
		System.out.println(map.toString());
		mv.setViewName("/member/login");
		return mv;		
	}
	
	
	
	
}