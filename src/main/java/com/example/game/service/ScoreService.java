package com.example.game.service;

import com.example.game.model.Score;
import com.example.game.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
