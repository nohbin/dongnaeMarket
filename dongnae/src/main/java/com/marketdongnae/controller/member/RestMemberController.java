package com.marketdongnae.controller.member;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.marketdongnae.domain.goods.GoodsDTO;
import com.marketdongnae.domain.member.KeywordDTO;
import com.marketdongnae.domain.member.KeywordVO;
import com.marketdongnae.security.CustomUserDetails;
import com.marketdongnae.service.member.MemberService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/keywordapi/")
@AllArgsConstructor
public class RestMemberController {
	
	private MemberService memberService;
	
	@PostMapping(value = "insertKeyword")
	public String insert (@RequestBody String keyword){
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext () .getAuthentication ().getPrincipal ();
		memberService.insertKeyword(customUserDetails, keyword); 
		return "success";
	}
	
	@GetMapping(value = "keywordList")
	public List<KeywordVO>  getKeywordList(){
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext () .getAuthentication ().getPrincipal ();
		List<KeywordVO> keywordList = memberService.getListKeyword(customUserDetails);
		return keywordList;
	}
	
	@DeleteMapping(value = "delete_{key_id}")
	public void delete(@PathVariable("key_id") int key_id){
		memberService.deleteKeyword(key_id);
	}
	
	
	@GetMapping(value = "keywordGoodsList")
	public List<GoodsDTO> getGoodsList(){
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext () .getAuthentication ().getPrincipal ();
		List<GoodsDTO> listKeywordGoods = memberService.getListKeywordGoods(customUserDetails);
		return listKeywordGoods;
	}
	
	
}
