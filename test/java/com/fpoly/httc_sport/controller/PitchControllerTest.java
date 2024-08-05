package com.fpoly.httc_sport.controller;

import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.service.PitchService;

@SpringBootTest
@AutoConfigureMockMvc
public class PitchControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PitchService pitchService;
	
	//Request
	private PitchRequest pitchRequest;
	//Response
	private PitchResponse pitResponse;
	private PitchDetailsResponse pitDetailResponse;
	
	@BeforeEach
	void initData() {
//		pitchRequest = PitchRequest.builder()
//				.pitchName("Sân 3")
//				.price(3000)
//				.street("56 Tô Ký")
//				.ward("Tân Chánh Hiệp")
//				.district("Quận 12")
//				.city("Thành phố Hồ Chí Minh")
//				.description("Sân mới, đẹp")
//				.type("Sân 5")
//				.total(2)
//				.build();
	}
	
	@Test
	// Kiểm thử tạo sân
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testCreatePitch() throws Exception {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("pitchName", "Sân 3");
		formData.add("price", "3000");
	    formData.add("street", "56 Tô Ký");
	    formData.add("ward", "Phường Tân Chánh Hiệp");
	    formData.add("district", "Quận 12");
	    formData.add("city", "Thành phố Hồ Chí Minh");
	    formData.add("description", "Sân bóng đá mini đạt chuẩn");
	    formData.add("type", "Sân 5");
	    formData.add("total", "2");
	    
//	    ObjectMapper objectMapper = new ObjectMapper();
//		String content = objectMapper.writeValueAsString(pitchRequest);
		
		Mockito.when(pitchService.createPitch(ArgumentMatchers.any(PitchRequest.class))).thenReturn(pitResponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.multipart("/api/v1/pitch")
				.params(formData)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
			);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testUpdatePitch() throws Exception {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("pitchName", "Sân 3");
		formData.add("price", "3000");
	    formData.add("street", "56 Tô Ký");
	    formData.add("ward", "Phường Tân Chánh Hiệp");
	    formData.add("district", "Quận 12");
	    formData.add("city", "Thành phố Hồ Chí Minh");
	    formData.add("description", "Sân bóng đá mini đạt chuẩn");
	    formData.add("type", "Sân 5");
	    formData.add("total", "2");
		
		Mockito.when(pitchService.updatePitch(ArgumentMatchers.anyInt(), ArgumentMatchers.any(PitchRequest.class)))
		.thenReturn(pitDetailResponse);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/api/v1/pitch/{id}", "1")
				.params(formData)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
//				.andExpect(MockMvcResultMatchers.status().is(403)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
				);
	}
	
	@Test
	// Kiểm thử xóa sân
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testDeletePitch() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/v1/pitch/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
				);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testGetPitch() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/pitch/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
				);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testGetPitchs() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/pitch")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000)
				);
	}
	
	@Test
	void CreatePitch() {
		System.out.println("Test Create Pitch");
	}
}
