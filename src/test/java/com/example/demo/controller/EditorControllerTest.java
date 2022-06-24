package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matcher;
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
import com.example.demo.repository.BlogInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EditorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BlogInfoRepository blogInfoRepository;

	@Test
	@WithMockUser(username = "mockUser")
	public void AccessHomePage() throws Exception {

		List<BlogInfo> blogList = new ArrayList<BlogInfo>();

		when(blogInfoRepository.findAll()).thenReturn(blogList);

		RequestBuilder request = MockMvcRequestBuilders//
				.get("/home")//
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)//
				.andExpect(view().name("home"))//
				.andExpect(model().attribute("blogList", blogList));
	}

	@SuppressWarnings("deprecation")
	@Test
	@WithMockUser(username = "mockUser")
	public void Publish() throws Exception {

		String blogTitle = "title";
		String blogContent = "content";
		Date date = new Date();

		RequestBuilder request = MockMvcRequestBuilders//
				.post("/publish")//
				.param("blog_title", blogTitle)//
				.param("blog_content", blogContent)//
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)//
				.andExpect(view().name("reader"))//
				.andExpect(model().attribute("isAuthorized", true))//
				.andExpect(model().attribute("theBlogAuthor", "mockUser"))//
				.andExpect(model().attribute("theBlogTitle", blogTitle))//
				.andExpect(model().attribute("theBlogContent", blogContent))//
				.andExpect(model().attribute("theBlogDate", 
						Matchers.allOf(
								Matchers.hasProperty("year", Matchers.is(date.getYear())),
								Matchers.hasProperty("month", Matchers.is(date.getMonth())),
								Matchers.hasProperty("date", Matchers.is(date.getDate()))
				)));
	}

	@Test
	@WithMockUser(username = "mockUser")
	public void Update() throws Exception {

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
				.post("/update")//
				.param("blogId", "" + blogId)//
				.param("blog_title", blogTitle)//
				.param("blog_content", blogContent)//
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)//
				.andExpect(view().name("reader"))//
				.andExpect(model().attribute("isAuthorized", true))//
				.andExpect(model().attribute("theBlogAuthor", "mockUser"))//
				.andExpect(model().attribute("theBlogId", blogId))//
				//.andExpect(model().attribute("theBlogDate", date))//
				.andExpect(model().attribute("theBlogTitle", blogTitle))//
				.andExpect(model().attribute("theBlogContent", blogContent));
	}

}
