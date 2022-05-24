package com.example.demo.controller;

import java.util.Map;
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
//@Slf4j
public class HomeController {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;

	@GetMapping("/builder")
	public ModelAndView editBlog(//
			@RequestParam("username") String username,//
			Map<String, Object> map, //
			ModelAndView mv
			) {
		map.put("username", username);	
		mv.setViewName("builder");	
		return mv;
	}

	@GetMapping("/reader")
	public ModelAndView readBlog(//
			@RequestParam("username") String username,//
			@RequestParam("blogId") long blogId, //
			 Map<String, Object> map, //
			 ModelAndView mv) {
		map.put("username", username);
		
		BlogInfo blogInfo = blogInfoRepository.findById(blogId);
		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.addObject("theBlogId", blogInfo.getId());
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogTitle", blogInfo.getTitle());
		mv.addObject("theBlogContent", blogInfo.getContent()); 
		mv.setViewName("reader");
		return mv;
	}
			
}
