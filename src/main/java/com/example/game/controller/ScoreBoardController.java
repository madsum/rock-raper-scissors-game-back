package com.example.game.controller;

import com.example.game.model.Score;
import com.example.game.service.ScoreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreBoardController {
    public static final String POST_REQUEST = "/add";
    public static final String GET_ALL_REQUEST = "/all";
    public static final String DELETE_BY_ID = "/deleteById/{id}";
    public static final String DELETE_BY_NAME = "/deleteByName/{name}";
    public static final String GET_BY_NAME_PATH_VAR = "/getByNamePath/{id}/{name}";
    public static final String GET_BY_NAME_PARAM = "/getByNameParam";
    public static final String GET_BY_ID = "/getById/{id}";

    private ScoreService scoreService;

    public ScoreBoardController(ScoreService scoreBoardService) {
        this.scoreService = scoreBoardService;
    }

    @GetMapping(path = GET_ALL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Score> getAllScore() {
        return scoreService.getAll();
    }

    @GetMapping(path = GET_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score getById(@PathVariable("id") long id) {
        return scoreService.getById(id);
    }

    // request by http://localhost:8080/score/getByNamePath/6/test
    @GetMapping(path = GET_BY_NAME_PATH_VAR)
    public Integer getByNamePath(@PathVariable(name = "id") long id, @PathVariable(name = "name") String name) {
        return scoreService.getByName(id, name);
    }


    // http://localhost:8080/score/getByNameParam?id=6&name=test
    @GetMapping(path = GET_BY_NAME_PARAM)
    public Integer getByNameParam(@RequestParam(name = "id") long id, @RequestParam(name = "name") String name) {
        return scoreService.getByName(id, name);
    }

    @PostMapping(path = POST_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score save(@RequestBody Score score) {
        return scoreService.save(score);
    }

    @DeleteMapping(path = DELETE_BY_ID)
    public void deleteById(@PathVariable(name = "id") long id) {
        scoreService.deleteById(id);
    }

    @DeleteMapping(path = DELETE_BY_NAME)
    public void deleteByName(@PathVariable(name = "name") String name) {
        scoreService.deleteByName(name);
    }
}
