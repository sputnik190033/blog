package com.example.demo.controller;

import java.util.Map;
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
//@Slf4j
public class HomeController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
//	@GetMapping("/editor")
//	public String newBlog() {
//		
//		return "editor";
//	}
	
	@GetMapping("/editor")
	public String editor(@RequestParam("username") String username, Map<String, Object> map) { 
		map.put("username", username);
		return "editor"; 
	}
	
	@GetMapping("/reader")
	public String readBlog(@RequestParam("username") String username, Map<String, Object> map) {
		map.put("username", username);
		return "reader";
	}
			
}
