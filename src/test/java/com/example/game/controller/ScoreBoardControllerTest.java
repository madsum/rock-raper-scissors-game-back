package com.example.game.controller;

import com.example.game.model.Score;
import com.example.game.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest
@WebMvcTest({ScoreBoardController.class})
class ScoreBoardControllerTest {

    @MockBean
    private ScoreService scoreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllScore() throws Exception {
        // Arrange
        List<Score> expectedScore = List.of(new Score(), new Score());
        when(scoreService.getAll()).thenReturn(expectedScore);

        var v = mockMvc.perform(get(ScoreBoardController.REQUEST_URL+ScoreBoardController.GET_ALL_REQUEST))
                .andExpect(status().isOk())
                .andReturn();
        System.out.printf(v.getResponse().getContentAsString());


    }

    @Test
    void getById() {
    }

    @Test
    void updateNameByIdPathVar() {
    }

    @Test
    void updateNameByIdParam() {
    }

    @Test
    void updateResultByIdParam() {
    }

    @Test
    void updateResultByIdPath() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteByName() {
    }

    @Test
    void deleteByRoundPathVar() {
    }

    @Test
    void deleteByRoundParam() {
    }

    List<Score> getTestData(){
        IntStream.range(1, 5)
                .forEach(i -> new Score(i, "name", i,null ))
                .collect()
    }
}