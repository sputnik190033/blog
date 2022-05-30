package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserInfoRepository userInfoRepository;
	
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
		
		String username = "admin";
		String password = "admin";
		String rpassword = "admin";
		UserInfo userInfo = UserInfo.builder()//
				.name(username)//
				.password(password)//
				.build();
		
		when(userInfoRepository.findByName(username)).thenReturn(null);
		when(userInfoRepository.save(userInfo)).thenReturn(userInfo);
		
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
	public void register_Fail() throws Exception {
		
		String username = "admin";
		String password = "admin";
		String rpassword = "admin";
		UserInfo userInfo = UserInfo.builder()//
				.name(username)//
				.password(password)//
				.build();
		
		when(userInfoRepository.findByName(username)).thenReturn(userInfo);
		
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
	
	
}
