package com.example.game.controller;

import com.example.game.model.Result;
import com.example.game.model.Score;
import com.example.game.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


    @WebMvcTest({ScoreBoardController.class})
    class ScoreBoardControllerTest {

        @MockBean
        private ScoreService scoreService;

        @Autowired
        private MockMvc mockMvc;

    @Test
    void getAllScore() throws Exception {
        // Arrange
        Score score = new Score(1l, "name", 1, Result.WIN, null);
        List<Score> expectedScore = List.of(score);
        when(scoreService.getAll()).thenReturn(expectedScore);

        // act and assert
        mockMvc.perform(get(ScoreBoardController.REQUEST_URL+ScoreBoardController.GET_ALL_REQUEST))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(expectedScore.size()));

    }

    @Test
    void getById() throws Exception {
        // Arrange
        Score score = new Score(1l, "name", 1, Result.WIN, null);
        when(scoreService.getById(1)).thenReturn(score);

        // act and assert
        mockMvc.perform(get(ScoreBoardController.REQUEST_URL + "/getById/1"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("WIN"));
    }


    @Test
    void updateNameByIdPathVar() throws Exception {
        Score score = new Score(1l, "name", 1, Result.WIN, null);
        Score expectedScore = new Score(1l, "updateName", 1, Result.WIN, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);
        var score1 = scoreService.updateNameById(1, "updateName"); // score1 is correctly mocked
        mockMvc.perform(put(ScoreBoardController.REQUEST_URL+"/updateNameByIdPath/1/newName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));
    }

    @Test
    void updateNameByIdParam() throws Exception {
        Score expectedScore = new Score(1l, "updateName", 1, Result.LOSS, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);
        var score1 = scoreService.updateNameById(1, "LOSS"); // score1 is correctly mocked
        mockMvc.perform(put(ScoreBoardController.REQUEST_URL+"/updateNameByIdPath/1/updateName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));

    }

    @Test
    void updateResultByIdParam() throws Exception {
        Score expectedScore = new Score(1l, "updateName", 1, Result.WIN, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);
        mockMvc.perform(put(ScoreBoardController.REQUEST_URL+"/updateNameByIdParam?id=1&name=updateName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));
    }

    @Test
    void updateResultByIdPath() throws Exception {
        Score expectedScore = new Score(1l, "name", 1, Result.LOSS, null);
        when(scoreService.updateResultById(1, Result.LOSS)).thenReturn(expectedScore);
        mockMvc.perform(put(ScoreBoardController.REQUEST_URL+"/updateResultByIdParam?id=1&result=LOSS"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("LOSS"));
    }

        @Test
        void save() throws Exception {
            Score expectedScore = new Score(1l, "name", 1, Result.WIN, null);
            String jsonScore = "{ \"id\": 1, \"name\": \"name\", \"round\" : 1, \"result\" : \"WIN\", \"timestamp\": null  }";
            when(scoreService.save(expectedScore)).thenReturn(expectedScore);
            var ret = scoreService.save(expectedScore); //  mock return correctly

            mockMvc.perform( post(ScoreBoardController.REQUEST_URL+ScoreBoardController.POST_REQUEST)
                    .content(jsonScore)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("WIN"));
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

    public static String asJsonString(final Score score) {
        try {
            return new ObjectMapper().writeValueAsString(score);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    List<Score> getTestData(){
        Score score = new Score(1l, "ss", 1, Result.WIN, null);
        List<Score> scoreList = List.of(score, score);
        return scoreList;
       }
}
