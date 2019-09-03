package com.example.javadevelopmentframework.javadevelopmentframework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JavadevelopmentframeworkApplication.class)
@AutoConfigureMockMvc
public class JavadevelopmentframeworkApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void success() throws Exception {
		mockMvc.perform(get("/success/11"));
		mockMvc.perform(get("/error/11"));
	}

}
