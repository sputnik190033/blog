package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

@Controller
//@Slf4j
public class HomeController {

	@Autowired
	private UserInfoRepository userInfoRepository;
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
		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.addObject("theBlogId", blogInfo.getId());
		mv.addObject("theBlogAuthor", blogInfo.getName());
		mv.addObject("theBlogTitle", blogInfo.getTitle());
		mv.addObject("theBlogContent", blogInfo.getContent()); 
		mv.setViewName("reader");
		return mv;
	}
			
}
