package com.example.demo.Repo;


import com.example.demo.Entity.MatchID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface IMatchIDRepo extends JpaRepository<MatchID, Integer> {

    MatchID findByMatchID(String matchID);
}
