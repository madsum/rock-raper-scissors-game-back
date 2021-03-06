package com.example.game.repository;

import com.example.game.model.Result;
import com.example.game.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Modifying
    @Transactional
    @Query("update Score sr set sr.name = ?2 where sr.id = ?1")
    Integer updateNameById(long id, String name);

    @Modifying
    @Transactional
    Optional<Score> getById(long id);

    @Modifying
    @Transactional
    void deleteById(long id);

    @Modifying
    @Transactional
    void deleteByName(String name);

    @Modifying
    @Transactional
    void deleteByRound(int round);

    @Modifying
    @Transactional
    @Query("update Score sr set sr.result = ?2 where sr.id = ?1")
    int updateResultById(long id, Result result);
}
