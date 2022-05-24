package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Arrays;

import com.example.demo.model.BlogInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EditorController {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;

	@GetMapping("/home")
	public ModelAndView backHome(//
			@RequestParam("username") String username,//
			Map<String, Object> map, //
			ModelAndView mv) { 
																													// page
		map.put("username", username);
		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.setViewName("home");
		return mv;
	}

	@PostMapping("/publish")
	public ModelAndView publish(//
			@RequestParam("username") String username, //
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {

		BlogInfo blogInfo = BlogInfo.builder()// builder is a static method
				.name(username)//
				.title(blogTitle)//
				.content(blogContent)//
				.build();//

		blogInfoRepository.save(blogInfo);
		
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogId", blogInfo.getId());
		mv.addObject("theBlogTitle", blogTitle);
		mv.addObject("theBlogContent", blogContent); // replaceAll("\n", "<br />")
		mv.addObject("username", username);
		mv.setViewName("reader");

		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update(//
			@RequestParam("username") String username, //
			@RequestParam("blogId") long blogId,//
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {

		BlogInfo blogInfo = blogInfoRepository.findById(blogId);
		blogInfo.setTitle(blogTitle);
		blogInfo.setContent(blogContent);
		blogInfoRepository.save(blogInfo);

		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogId", blogId);
		mv.addObject("theBlogTitle", blogTitle);
		mv.addObject("theBlogContent", blogContent); // replaceAll("\n", "<br />")
		mv.addObject("username", username);
		mv.setViewName("reader");

		return mv;
	}
}
