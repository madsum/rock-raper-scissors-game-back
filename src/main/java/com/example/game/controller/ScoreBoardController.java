package com.example.game.controller;

import com.example.game.model.Result;
import com.example.game.model.Score;
import com.example.game.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ScoreBoardController.REQUEST_URL)
public class ScoreBoardController {
    public static final String REQUEST_URL = "/score";
    public static final String POST_REQUEST = "/add";
    public static final String GET_ALL_REQUEST = "/all";
    public static final String DELETE_BY_ID = "/deleteById/{id}";
    public static final String DELETE_BY_NAME = "/deleteByName/{name}";
    public static final String DELETE_BY_ROUND_PATH_VAR = "/deleteByRoundPath/{round}";
    public static final String DELETE_BY_ROUND_PARAM = "/deleteByRoundParam";
    public static final String UPDATE_NAME_BY_ID_PATH_VAR = "/updateNameByIdPath/{id}/{name}";
    public static final String UPDATE_NAME_BY_ID_PARAM = "/updateNameByIdParam";
    public static final String UPDATE_RESULT_BY_ID_PATH_VAR = "/updateResultByIdPath/{id}/{result}";
    public static final String UPDATE_RESULT_BY_ID_PARAM = "/updateResultByIdParam";
    public static final String GET_BY_ID = "/getById/{id}";

    private ScoreService scoreService;

    public ScoreBoardController(ScoreService scoreBoardService) {
        this.scoreService = scoreBoardService;
    }

    @ApiOperation(value = "Returns list of all score")
    @GetMapping(value = GET_ALL_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Score> getAllScore() {
        return scoreService.getAll();
    }

    @ApiOperation(value = "Returns score by id")
    @GetMapping(path = GET_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score getById(
            @ApiParam(value = "id of the score")
            @PathVariable("id") long id) {
        return scoreService.getById(id);
    }

    // request by http://localhost:8080/score/updateNameByIdPath/6/newName
    @ApiOperation(value = "Returns score by id example by PathVariable")
    @PutMapping(path = UPDATE_NAME_BY_ID_PATH_VAR, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score updateNameByIdPathVar(
            @ApiParam(value = "id of the score")
            @PathVariable(name = "id") long id,
            @ApiParam(value = "updated name of the score")
            @PathVariable(name = "name") String name) {
        return scoreService.updateNameById(id, name);
    }


    //put http://localhost:8080/score/getByNameParam?id=6&name=test
    @ApiOperation(value = "Returns score by id example by RequestParam")
    @PutMapping(path = UPDATE_NAME_BY_ID_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score updateNameByIdParam(
        @ApiParam(value = "id of the score")
        @RequestParam(name = "id") long id,
        @ApiParam(value = "updated name of the score")
        @RequestParam(name = "name") String name) {
    return scoreService.updateNameById(id, name);
    }

    //put http://localhost:8080/score/getByNameParam?id=6&name=test
    @ApiOperation(value = "Returns score by id example by RequestParam")
    @PutMapping(path = UPDATE_RESULT_BY_ID_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score updateResultByIdParam(
            @ApiParam(value = "id of the score")
            @RequestParam(name = "id") long id,
            @ApiParam(value = "updated result of the score")
            @RequestParam(name = "result") Result result) {
        return scoreService.updateResultById(id, result);
    }

    //put http://localhost:8080/score/getByNameParam?id=6&name=test
    @ApiOperation(value = "Returns result by id example by RequestParam")
    @PutMapping(path = UPDATE_RESULT_BY_ID_PATH_VAR, produces = MediaType.APPLICATION_JSON_VALUE)
    public Score updateResultByIdPath(
            @ApiParam(value = "id of the score")
            @PathVariable(name = "id") long id,
            @ApiParam(value = "updated result of the score")
            @PathVariable(name = "result") Result result) {
        return scoreService.updateResultById(id, result);
    }

        @ApiOperation(value = "Add a new score post socre as json body")
        @PostMapping(path = POST_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
        public Score save(
                @ApiParam(value = "A score to save into db")
                @RequestBody Score score) {
            return scoreService.save(score);
        }

    // delete http://localhost:8080/score/deleteById/7
    @ApiOperation(value = "Delete score by id")
    @DeleteMapping(path = DELETE_BY_ID)
    public void deleteById(@PathVariable(name = "id") long id) {
        scoreService.deleteById(id);
    }

    // delete http://localhost:8080/score/deleteByName/test2
    @ApiOperation(value = "Delete score by name")
    @DeleteMapping(path = DELETE_BY_NAME)
    public void deleteByName(
            @ApiParam(value = "delete score by name from db")
            @PathVariable(name = "name") String name) {
        scoreService.deleteByName(name);
    }

    // delete http://localhost:8080/score/deleteByRoundPath/12
    @ApiOperation(value = "Delete score by round a PathVariable example")
    @DeleteMapping(path = DELETE_BY_ROUND_PATH_VAR)
    public void deleteByRoundPathVar(
            @ApiParam(value = "delete score by round from db")
            @PathVariable(name = "round") int round) {
        scoreService.deleteByRound(round);
    }

    // delete http://localhost:8080/score/deleteByRoundParam?round=13
    @ApiOperation(value = "Delete score by round example of RequestParam")
    @DeleteMapping(path = DELETE_BY_ROUND_PARAM)
    public void deleteByRoundParam(
            @ApiParam(value = "delete score by round from db")
            @RequestParam(name = "round") int round) {
        scoreService.deleteByRound(round);
    }
}
