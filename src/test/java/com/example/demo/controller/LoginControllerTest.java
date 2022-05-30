package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.repository.BlogInfoRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BlogInfoRepository blogInfoRepository;
	
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
		
		List<BlogInfo> blogList = new ArrayList<BlogInfo>();
		
		when(blogInfoRepository.findAll()).thenReturn(blogList);
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/")//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("home"))//
				.andExpect(model().attribute("blogList", blogList));
	}
	
}
