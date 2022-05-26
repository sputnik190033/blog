package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;

@Controller
public class EditorController {

	@Autowired
	private BlogInfoRepository blogInfoRepository;

	@GetMapping("/home")
	public ModelAndView backHome(//
			ModelAndView mv) { 

		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.setViewName("home");
		return mv;
	}

	@PostMapping("/publish")
	public ModelAndView publish(//
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {
		
		Date date = new Date();
		BlogInfo blogInfo = BlogInfo.builder()// builder is a static method
				.name((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())//
				.title(blogTitle)//
				.content(blogContent)//
				.date(date)//
				.views((long)0)//
				.build();//

		blogInfoRepository.save(blogInfo);
		
		mv.addObject("isAuthorized",true);
		
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogId", blogInfo.getId());
		mv.addObject("theBlogDate", blogInfo.getDate());
		mv.addObject("theBlogViews", blogInfo.getViews());
		mv.addObject("theBlogTitle", blogTitle);
		mv.addObject("theBlogContent", blogContent); 
		mv.setViewName("reader");

		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update(//
			@RequestParam("blogId") long blogId,//
			@RequestParam("blog_title") String blogTitle, //
			@RequestParam("blog_content") String blogContent, //
			ModelAndView mv) {

		BlogInfo blogInfo = blogInfoRepository.findById(blogId);
		blogInfo.setTitle(blogTitle);
		blogInfo.setContent(blogContent);
		blogInfoRepository.save(blogInfo);
		
		boolean isAuthorized = false;
		if(blogInfo.getName().equals((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			isAuthorized = true;
		}
		mv.addObject("isAuthorized",isAuthorized);	
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogDate", blogInfo.getDate());
		mv.addObject("theBlogViews", blogInfo.getViews());
		mv.addObject("theBlogId", blogId);
		mv.addObject("theBlogTitle", blogTitle);
		mv.addObject("theBlogContent", blogContent);
		mv.setViewName("reader");

		return mv;
	}
}
