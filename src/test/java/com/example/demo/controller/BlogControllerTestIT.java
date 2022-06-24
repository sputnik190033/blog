package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.BlogInfo;
import com.example.demo.repository.BlogInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTestIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
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
				.name("admin")//
				.title("")//
				.content("")//
				.date(new Date())//
				.views(0l)//
				.build();
		
	
		RequestBuilder request = MockMvcRequestBuilders//
				.get("/reader")//
				.param("blogId", ""+blogId)//
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)//
				.andExpect(view().name("reader"))//
				.andExpect(model().attribute("isAuthorized", false));
		
	}
	
	@Test
	@WithMockUser(username = "mockUser")
	public void Delete_Success() throws Exception {
		
		long blogId = 1l;

		BlogInfo blogInfo = BlogInfo.builder()//
				.id(blogId)//				
				.build();
		
		List<BlogInfo> blogList = blogInfoRepository.findAll();

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
	public void Delete_Fail() throws Exception {
		
		long blogId = 1000l;
		
		List<BlogInfo> blogList = blogInfoRepository.findAll();

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
	
	@Test
	@WithMockUser(username = "mockUser")
	public void AccessHomePage() throws Exception {

		List<BlogInfo> blogList = blogInfoRepository.findAll();

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
