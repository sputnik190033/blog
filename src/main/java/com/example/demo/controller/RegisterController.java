package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

//import lombok.extern.slf4j.Slf4j;

@Controller
//@Slf4j
public class RegisterController {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@GetMapping("/register")
	public String getRegisterView() { 
		
		return "register";
	}

	@PostMapping("/register")
	public ModelAndView register(//
			@RequestParam("username") String username, //
			@RequestParam("password") String password, //
			@RequestParam("rpassword") String rpassword, //
			ModelAndView mv) {
		
		boolean isSame = true;
		if (!password.equals(rpassword)) {
			mv.setViewName("register");
			isSame = false;
			
		}else {

			UserInfo userInfo = UserInfo.builder()// builder is a static method
					.name(username)//
					.password(password)//
					.build();//

			userInfoRepository.save(userInfo);

			mv.addObject("username", username);
			mv.setViewName("login");		
		}
		mv.addObject("isSame", isSame);
		return mv;
	}
}
