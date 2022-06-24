package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.BlogInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.BlogInfoRepository;
import com.example.demo.repository.UserInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private BlogInfoRepository blogInfoRepository;
	
	@Test
	public void AccessRegisterPage() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/register")//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("register"));
		
	}
	
	@Test
	public void Register_Success() throws Exception {
		
		String username = "ittest";
		String password = "ittest";
		String rpassword = "ittest";
//		UserInfo userInfo = UserInfo.builder()//
//				.name(username)//
//				.password(password)//
//				.build();
				
		RequestBuilder request = MockMvcRequestBuilders//
				.post("/register")//
				.param("username", username)//
				.param("password", password)//
				.param("rpassword", rpassword)//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("login"));
	}
	
	@Test
	public void Register_Fail() throws Exception {
		
		String username = "admin";
		String password = "admin";
		String rpassword = "admin";		
		
		RequestBuilder request = MockMvcRequestBuilders//
				.post("/register")//
				.param("username", username)//
				.param("password", password)//
				.param("rpassword", rpassword)//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("register"))//
				.andExpect(model().attribute("isSame", true));
		
	}
	
	@Test
	public void AccessLoginPage() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/login");
		
		mockMvc.perform(request)//
				.andExpect(view().name("login"));
		
	}
	
	@Test
	@WithMockUser(username="mockUser")
	public void AccessDefaultPage() throws Exception{
		
		List<BlogInfo> blogList = blogInfoRepository.findAll();
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/")//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("home"))//
				.andExpect(model().attribute("blogList", blogList));
	}
	
}
