package com.example.myrestapi.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getReviews() throws Exception {

        /**
         * sirasiyla:
         * username pass'la authentication'a request at tokeni al
         * tokenle post request at olustur
         * olusturdugunun userIdsini vs. kontrol et
         */

        ReviewDto reviewToPost = new ReviewDto();
        reviewToPost.setUserId(2L);
        reviewToPost.setText("review post by mockmvc");
        reviewToPost.setRating(4);

        mvc.perform(MockMvcRequestBuilders
                        .post("/reviews")
                        .content("{\"text\": \"review post by mockmvc\", \"rating\": \"4\"} ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",
                                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaXNzIjoiYmFyLXJldmlldy1hcGkifQ.fru0ViG0Z7d1RhVZPs96uIiBi7hpFJZGeoSF9cA5lZM"))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/reviews")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value("44"));
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void getReview() {
    }

    @Test
    void addReview() {
    }

    @Test
    void updateReview() {
    }

    @Test
    void deleteReview() {
    }
}