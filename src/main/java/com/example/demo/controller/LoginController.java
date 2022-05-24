package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
	@GetMapping("/login")
	public String login() {
		
		//log.info("login method has been executed");
		return "login";
	}
	
<<<<<<< Updated upstream
	@PostMapping("/home")
	public ModelAndView login(@RequestParam("username") String userName,
		@RequestParam("password") String password, ModelAndView mv) {
		
		mv.addObject("username", userName);
=======
	@GetMapping("/")
	public ModelAndView login(ModelAndView mv) {
		
		//mv.addObject("username", userName);
>>>>>>> Stashed changes
		mv.addObject("blogList", blogInfoRepository.findAll());
		//System.err.println(userName);
		
		UserInfo userInfo = userInfoRepository.findByName(userName);
			
		if(userInfo != null && password.equals(userInfo.getPassword())) {
			//return "Wblog";
			mv.setViewName("home");
		}else {
			//return "redirect:/login";
			mv.setViewName("fail");
		}
		
		return mv;
	}	
}
