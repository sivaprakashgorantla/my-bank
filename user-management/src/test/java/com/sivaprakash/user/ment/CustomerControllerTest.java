package com.sivaprakash.user.ment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class CustomerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testUpdateProfile() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{customerId}/updateProfile", 1L)
//                .param("newCustomerId", "C123456")
//                .param("phoneNumber", "1234567890")
//                .param("email", "test@example.com")
//                .param("otpEntered", "123456"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Profile updated successfully."));
//    }
//
//    @Test
//    public void testUpdateProfileWithInvalidOtp() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{customerId}/updateProfile", 1L)
//                .param("newCustomerId", "C123456")
//                .param("phoneNumber", "1234567890")
//                .param("email", "test@example.com")
//                .param("otpEntered", "wrongOtp"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Invalid OTP entered."));
//    }
}
