package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EditorController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
	@GetMapping("/home")
	public String backHome() { // return html page
		return "home";
	}
	
	@PostMapping("/publish")
	public ModelAndView publish(//
			@RequestParam("username") String username,//
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {

			BlogInfo blogInfo = BlogInfo.builder()// builder is a static method
					.name(username)//
					.title(blogTitle)//
					.content(blogContent)//
					.build();//

			blogInfoRepository.save(blogInfo);
			mv.addObject("theBlogTitle", blogTitle);
			mv.addObject("theBlogContent", blogContent);
			mv.addObject("username", username);
			mv.setViewName("reader");
			
		return mv;
	}
}
