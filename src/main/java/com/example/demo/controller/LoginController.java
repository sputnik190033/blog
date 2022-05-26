package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.BlogInfoRepository;

@Controller
public class LoginController {

	@Autowired
	private BlogInfoRepository blogInfoRepository;

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/")
	public ModelAndView login(ModelAndView mv) {

		mv.addObject("blogList", blogInfoRepository.findAll());
		mv.setViewName("home");
		return mv;
	}
}
