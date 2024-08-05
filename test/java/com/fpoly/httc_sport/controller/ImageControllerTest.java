package com.fpoly.httc_sport.controller;

import com.fpoly.httc_sport.dto.request.ImageRequest;
import com.fpoly.httc_sport.dto.response.ApiResponse;
import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    @DisplayName("Test upload images")
    void testUploadImages() throws Exception {
        // Prepare test data
        MockMultipartFile image1 = new MockMultipartFile("image", "image1.png", "image/png", "data".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image", "image2.png", "image/png", "data".getBytes());
        List<MultipartFile> images = Arrays.asList(image1, image2);

        List<ImageResponse> imageResponses = new ArrayList<>();
        imageResponses.add(new ImageResponse("publicId1", "http://example.com/image1.png"));
        imageResponses.add(new ImageResponse("publicId2", "http://example.com/image2.png"));

        // Mocking the service method
        when(imageService.save(anyList())).thenReturn(imageResponses);

        // Perform the request and verify the results
        mockMvc.perform(multipart("/api/v1/image")
                .file(image1)
                .file(image2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[0].publicId").value("publicId1"))
                .andExpect(jsonPath("$.result[1].url").value("http://example.com/image2.png"));

        // Verify that the service method was called once
        verify(imageService, times(1)).save(anyList());
        verifyNoMoreInteractions(imageService);
    }

    @Test
    @DisplayName("Test get images")
    void testGetImages() throws Exception {
        // Prepare test data
        ImageRequest request = ImageRequest.builder()
                .publicIds(new HashSet<>(Arrays.asList("1", "2")))
                .build();

        List<ImageResponse> imageResponses = new ArrayList<>();
        imageResponses.add(new ImageResponse("1", "anh_san_1_1.jpg"));
        imageResponses.add(new ImageResponse("2", "anh_san_1_2.jpg"));

        // Mocking the service method using any()
        when(imageService.getImages(any())).thenReturn(imageResponses);

        // Perform the request and verify the results
        mockMvc.perform(get("/api/v1/image")
                .contentType("application/json")
                .content("{\"publicIds\": [\"1\", \"2\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[0].url").value("anh_san_1_1.jpg"))
                .andExpect(jsonPath("$.result[1].publicId").value("2"));

        // Verify that the service method was called once with the correct argument
        verify(imageService, times(1)).getImages(any());
        verifyNoMoreInteractions(imageService);
    }


//    @Test
//    @DisplayName("Test delete images")
//    void testDeleteImages() throws Exception {
//        // Prepare test data
//        ImageRequest request = ImageRequest.builder()
//                .publicIds(new HashSet<>(Arrays.asList("1", "2")))
//                .build();
//
//        // Mocking the service method using any()
//        doNothing().when(imageService).deleteImages(any());
//
//        // Perform the request and verify the results
//        mockMvc.perform(delete("/api/v1/image")
//                .contentType("application/json")
//                .content("{\"publicIds\": [\"1\", \"2\"]}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Xóa hình ảnh thành công"));
//
//        // Verify that the service method was called once with the correct argument
//        verify(imageService, times(1)).deleteImages(any());
//        verifyNoMoreInteractions(imageService);
//    }

}
