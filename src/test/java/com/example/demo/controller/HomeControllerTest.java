package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

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
public class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BlogInfoRepository blogInfoRepository;
	
	@Test
	@WithMockUser(username="mockUser")
	public void AccessBuilderPage() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/builder")//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("builder"));
	}
	
	@Test
	@WithMockUser(username="mockUser")
	public void AccessReaderPage_IsAuthor() throws Exception {
		
		long blogId = 1l;
		
		BlogInfo blogInfo = BlogInfo.builder()//
				.id(blogId)//
				.name("mockUser")//
				.title("")//
				.content("")//
				.date(new Date())//
				.views(0l)//
				.build();
		
		when(blogInfoRepository.findById(blogId)).thenReturn(blogInfo);
		//when(blogInfoRepository.save(any(BlogInfo.class))).thenReturn(blogInfo);
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/reader")//
				.param("blogId", ""+blogId)//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("reader"))//
				.andExpect(model().attribute("isAuthorized", true));
	}
	
	@Test
	@WithMockUser(username="mockUser")
	public void AccessReaderPage_IsNotAuthor() throws Exception {
		
		long blogId = 1l;
		
		BlogInfo blogInfo = BlogInfo.builder()//
				.id(blogId)//
				.name("")//
				.title("")//
				.content("")//
				.date(new Date())//
				.views(0l)//
				.build();
		
		when(blogInfoRepository.findById(blogId)).thenReturn(blogInfo);
		//when(blogInfoRepository.save(blogInfo)).thenReturn(blogInfo);
		
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/reader")//
				.param("blogId", ""+blogId)//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("reader"))//
				.andExpect(model().attribute("isAuthorized", false));
		
	}
	
}
