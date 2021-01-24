package com.example.game.service;

import com.example.game.model.Result;
import com.example.game.model.Score;
import com.example.game.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score save(Score score) {
        return scoreRepository.save(score);
    }

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public Score getById(long id) {
        Optional<Score> optionalScore = scoreRepository.getById(id);
        return optionalScore.orElse(null);
    }

    public Score updateNameById(long id, String name) {
     return  scoreRepository.findById(id)
                .map(score -> {
                    score.setName(name);
                    return scoreRepository.save(score);
                    })
                .orElseGet(() -> null);
    }

    public Score findById(long id) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        return optionalScore.orElse(null);
    }

    public Score findById2(long id) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        return optionalScore.orElse(null);
    }

    public void deleteById(long id) {
        scoreRepository.deleteById(id);
    }

    public void deleteByName(String name) {
        scoreRepository.deleteByName(name);
    }

    public void deleteByRound(int round) {
        scoreRepository.deleteByRound(round);
    }

    public Score updateResultById(long id, Result result) {
        return  scoreRepository.findById(id)
                .map(score -> {
                    score.setResult(result);
                    return scoreRepository.save(score);
                })
                .orElseGet(() -> null);

    }
}
