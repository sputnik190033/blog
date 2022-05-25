package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReaderController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
	@GetMapping("/delete")
	public ModelAndView delete(//
			@RequestParam("blogId") long blogId,//			
			ModelAndView mv
			) {
		BlogInfo blogInfo = blogInfoRepository.findById(blogId);
		blogInfoRepository.delete(blogInfo);
		
		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.setViewName("home");	
		return mv;
	}	
	
	@GetMapping("/editor")
	public ModelAndView editBlog(//
			@RequestParam("blogId") long blogId,//
			ModelAndView mv
			) {		
		BlogInfo blogInfo = blogInfoRepository.findById(blogId);		
		mv.addObject("theBlogId", blogId);
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogTitle", blogInfo.getTitle());
		mv.addObject("theBlogContent", blogInfo.getContent()); 
			
		mv.setViewName("editor");	
		return mv;
	}		
}
