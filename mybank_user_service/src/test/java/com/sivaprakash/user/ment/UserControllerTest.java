package com.sivaprakash.user.ment;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivaprakash.user_service.entity.User;
import com.sivaprakash.user_service.service.UserService;

//@SpringBootTest
//@AutoConfigureMockMvc
public class UserControllerTest {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private UserService userService;
//
//	private User user;
//
//	@BeforeEach
//	public void setUp() {
//		//user = new User("ABC1", "password123", "abc1", "ABCabc", "abc1@example.com", "1234567890");
//	}
//
////    @Test
////    public void testRegisterUser() throws Exception {
////        Mockito.when(userService.registerUser(any(User.class))).thenReturn(user);
////        mockMvc.perform(post("/api/users/register")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(asJsonString(user)))
////                .andDo(print()) // Debug
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.username").value("ABC"))
////                .andExpect(jsonPath("$.email").value("abc@example.com"));
////    }
//
//	@Test
//	public void testLogin() throws Exception {
//		// Mocking the user login behavior in the service layer
//		User mockUser = new User("ABC1", "password123", "abc1", "ABCabc", "abc1@example.com", "1234567890");
//		Mockito.when(userService.login(any(String.class), any(String.class))).thenReturn(Optional.of(mockUser));
//
//		// Performing the test
//		mockMvc.perform(post("/api/users/login").param("username", "ABC1").param("password", "password123"))
//				.andDo(print()) // Debug
//				.andExpect(status().isOk()).andExpect(jsonPath("$.username").value("ABC1"))
//				.andExpect(jsonPath("$.email").value("abc1@example.com"));
//	}
//
//	@Test
//	public void testLoginInvalidCredentials() throws Exception {
//		// Mocking login to return an empty Optional, simulating invalid credentials
//		Mockito.when(userService.login(any(String.class), any(String.class))).thenReturn(Optional.empty());
//
//		mockMvc.perform(post("/api/users/login").param("username", "invalidUser").param("password", "wrongPassword"))
//				.andDo(print()) // Debug
//				.andExpect(status().isUnauthorized()).andExpect(jsonPath("$").value("Invalid credentials"));
//	}
//
//	private static String asJsonString(Object obj) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			return objectMapper.writeValueAsString(obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
}
