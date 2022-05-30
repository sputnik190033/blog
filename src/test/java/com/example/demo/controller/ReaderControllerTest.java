package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
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
public class ReaderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BlogInfoRepository blogInfoRepository;

	@Test
	@WithMockUser(username = "mockUser")
	public void Delete_Success() throws Exception {
		
		long blogId = 1l;

		BlogInfo blogInfo = BlogInfo.builder()//
				.id(blogId)//				
				.build();
		
		List<BlogInfo> blogList = new ArrayList<BlogInfo>();
		
		when(blogInfoRepository.findById(blogId)).thenReturn(blogInfo);
		
		when(blogInfoRepository.findAll()).thenReturn(blogList);

		RequestBuilder request = MockMvcRequestBuilders//
				.post("/delete")//
				.param("blogId", ""+blogId)//
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)//
				.andExpect(view().name("home"))//
				.andExpect(model().attribute("blogList", blogList));
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void AccessEditorPage() throws Exception {

		long blogId = 1l;
		String blogTitle = "title";
		String blogContent = "content";
		Date date = new Date();

		BlogInfo blogInfo = BlogInfo.builder()//
				.id(blogId)//
				.name("mockUser")//
				.title(blogTitle)//
				.content(blogContent)//
				.date(date)//
				.views(0l)//
				.build();

		when(blogInfoRepository.findById(blogId)).thenReturn(blogInfo);
		//when(blogInfoRepository.save(any(BlogInfo.class))).thenReturn(blogInfo);

		RequestBuilder request = MockMvcRequestBuilders//
				.get("/editor")//
				.param("blogId", "" + blogId)//
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)//
				.andExpect(view().name("editor"))//
				.andExpect(model().attribute("theBlogAuthor", "mockUser"))//
				.andExpect(model().attribute("theBlogId", blogId))//
				.andExpect(model().attribute("theBlogTitle", blogTitle))//
				.andExpect(model().attribute("theBlogContent", blogContent));
	}
}
