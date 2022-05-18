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
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {

			BlogInfo blogInfo = BlogInfo.builder()// builder is a static method
					.name("admin")//
					.blog_title(blogTitle)//
					.blog_content(blogContent)//
					.build();//

			blogInfoRepository.save(blogInfo);

			//mv.addObject("username", username);
			mv.setViewName("reader");
			
		return mv;
	}
}
