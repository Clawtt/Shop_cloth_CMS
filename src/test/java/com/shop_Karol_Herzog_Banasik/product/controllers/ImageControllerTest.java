package com.shop_Karol_Herzog_Banasik.product.controllers;

import com.shop_Karol_Herzog_Banasik.product.services.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ImageService imageService;

    @Test
    public void files_shouldBeSaveIntoFileSystem_whenAreValid() throws Exception {

        Long productId = 1L;

        MockMultipartFile file = new MockMultipartFile(
                "files",
                "test.jpg",
                "multipart/form-data",
                "some picture with anything".getBytes()
        );

        mockMvc.perform(multipart("/product/" + productId + "/add/images")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("files", "test.jpg"))
                .andExpect(status().isOk());

        verify(imageService, times(1)).addNewImageToFileSystem(any(MultipartFile[].class), eq(productId));


    }
}

