package com.example.demo.Repo;

import com.example.demo.Entity.MatchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@EnableJpaRepositories
@Repository
public interface IMatchDataRepo extends JpaRepository<MatchData, Integer> {

    List<MatchData> findByMatchIdAndPuuid(String matchId, String puuid);
}
