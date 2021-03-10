package com.example.game.controller;

import com.example.game.model.Result;
import com.example.game.model.Score;
import com.example.game.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mockMvc.perform(get(ScoreBoardController.REQUEST_URL + ScoreBoardController.GET_ALL_REQUEST))
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
        Score expectedScore = new Score(1l, "updateName", 1, Result.WIN, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);

        mockMvc.perform(put(ScoreBoardController.REQUEST_URL + "/updateNameByIdPath/1/updateName"))
                .andDo(print()) // this is for debugging
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));
    }

    @Test
    void updateNameByIdParam() throws Exception {

        Score expectedScore = new Score(1l, "updateName", 1, Result.LOSS, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);

        mockMvc.perform(put(ScoreBoardController.REQUEST_URL + "/updateNameByIdPath/1/updateName"))
                .andDo(print()) // this is for debugging
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));
    }

    @Test
    void updateResultByIdParam() throws Exception {

        Score expectedScore = new Score(1l, "updateName", 1, Result.WIN, null);
        when(scoreService.updateNameById(1, "updateName")).thenReturn(expectedScore);

        mockMvc.perform(put(ScoreBoardController.REQUEST_URL + "/updateNameByIdParam?id=1&name=updateName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateName"));
    }

    @Test
    void updateResultByIdPath() throws Exception {
        Score expectedScore = new Score(1l, "name", 1, Result.LOSS, null);
        when(scoreService.updateResultById(1, Result.LOSS)).thenReturn(expectedScore);

        mockMvc.perform(put(ScoreBoardController.REQUEST_URL + "/updateResultByIdParam?id=1&result=LOSS"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("LOSS"));
    }

    @Test
    void save() throws Exception {
        Score expectedScore = new Score(1l, "name", 1, Result.WIN, null);
        // for this we must implement correct equals method
        when(scoreService.save(expectedScore)).thenReturn(expectedScore);

        mockMvc.perform(post(ScoreBoardController.REQUEST_URL + ScoreBoardController.POST_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(asJsonString(expectedScore).getBytes()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("WIN"));
    }

    @Test
    void save2() throws Exception {
        Score expectedScore = new Score(1l, "name", 1, Result.WIN, null);
        when(scoreService.save(any())).thenReturn(expectedScore);

        mockMvc.perform(post(ScoreBoardController.REQUEST_URL + ScoreBoardController.POST_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(asJsonString(expectedScore).getBytes()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("WIN"));
    }

    @Test
    void deleteById() {
        // this is how void method is unit tested
        scoreService.deleteById(1l);
        ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);
        verify(scoreService, Mockito.times(1)).deleteById(1l);
        verify(scoreService).deleteById(arg.capture());
        Assertions.assertEquals(1l, arg.getValue());
    }

    @Test
    void deleteByName() {
        // this is how void method is unit tested
        String name = "name";
        scoreService.deleteByName(name);
        ArgumentCaptor<String > arg = ArgumentCaptor.forClass(String.class);
        verify(scoreService, Mockito.times(1)).deleteByName(name);
        verify(scoreService).deleteByName(arg.capture());
        Assertions.assertEquals(name, arg.getValue());
    }

    @Test
    void deleteByRoundPathVar() {
        // this is how void method is unit tested
        int round = 1;
        scoreService.deleteByRound(round);
        ArgumentCaptor<Integer> arg = ArgumentCaptor.forClass(Integer.class);
        verify(scoreService, Mockito.times(1)).deleteByRound(round);
        verify(scoreService).deleteByRound(arg.capture());
        Assertions.assertEquals(round, arg.getValue());
    }

    @Test
    void deleteByRoundParam() {
        // this is how void method is unit tested
        int round = 1;
        scoreService.deleteByRound(round);
        ArgumentCaptor<Integer> arg = ArgumentCaptor.forClass(Integer.class);
        verify(scoreService, Mockito.times(1)).deleteByRound(round);
        verify(scoreService).deleteByRound(arg.capture());
        Assertions.assertEquals(round, arg.getValue());
    }

    // An example of how to test private method by reflection
    @Test
    void testPrivateSum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ScoreService.class.getDeclaredMethod("privateSum", Integer.class, Integer.class);
        method.setAccessible(true);
        ScoreService scoreService = new ScoreService();
        int result = (int) method.invoke(scoreService, 10, 40);
        Assertions.assertEquals(50, result);
    }

    // helper methods.

    public static String asJsonString(final Score score) {
        try {
            return new ObjectMapper().writeValueAsString(score);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Score asJsonStringToObject(final String score) {
        try {
            return new ObjectMapper().readValue(score, Score.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    List<Score> getTestData() {
        Score score = new Score(1l, "ss", 1, Result.WIN, null);
        List<Score> scoreList = List.of(score, score);
        return scoreList;
    }
}
