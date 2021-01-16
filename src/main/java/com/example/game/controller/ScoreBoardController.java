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

    private ScoreService scoreService;

    public ScoreBoardController(ScoreService scoreBoardService) {
        this.scoreService = scoreBoardService;
    }

    @GetMapping(path = GET_ALL_REQUEST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Score> getAllScore(){
        return scoreService.getAll();
    }

    @PostMapping(path = POST_REQUEST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public Score save(@RequestBody Score score) {
        return scoreService.save(score);
    }
}