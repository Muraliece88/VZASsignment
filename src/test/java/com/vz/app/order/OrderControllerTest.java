package com.vz.app.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {
	public static final String API_V1_ORDER = "/api/v1/order";
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private OrderServiceImpl orderServiceImpl;

	@Test
	void testPostOrder() throws Exception {
		String body = "{\r\n" + "  \"productId\": \"123\",\r\n" + "\"email\": \"charles.morris@reqres.in\"\r\n" + "}";
		mockMvc.perform(post(API_V1_ORDER).content(body).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isOk());
	}

	@Test
	void testGetOrder() throws Exception {
		mockMvc.perform(get(API_V1_ORDER+"/orders").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.content[0].first_name", Matchers.is("Charles")));

	}
}
