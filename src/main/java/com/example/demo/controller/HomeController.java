package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;

@Controller
//@Slf4j
public class HomeController {

	@Autowired
	private BlogInfoRepository blogInfoRepository;

	@GetMapping("/builder")
	public ModelAndView editBlog(ModelAndView mv) {
		
		mv.setViewName("builder");	
		return mv;
	}

	@GetMapping("/reader")
	public ModelAndView readBlog(//
			@RequestParam("blogId") long blogId, //
			ModelAndView mv) {
		
		BlogInfo blogInfo = blogInfoRepository.findById(blogId);
		blogInfo.setViews(blogInfo.getViews()+1);
		blogInfoRepository.save(blogInfo);
		
		mv.addObject("theBlogId", blogInfo.getId());
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogDate", blogInfo.getDate());
		mv.addObject("theBlogViews", blogInfo.getViews());
		mv.addObject("theBlogTitle", blogInfo.getTitle());
		mv.addObject("theBlogContent", blogInfo.getContent()); 
		
		boolean isAuthorized = false;	
		if(blogInfo.getName().equals((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			isAuthorized = true;
		}
		mv.addObject("isAuthorized",isAuthorized);
		
		mv.setViewName("reader");
		return mv;
	}
			
}
