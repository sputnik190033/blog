package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;

@Controller
public class ReaderController {
	
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
	@PostMapping("/delete")
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
