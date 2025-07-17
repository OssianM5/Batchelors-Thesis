package com.example.demo.Repo;

import com.example.demo.Entity.PuuidMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface IPuuidMatchRepo extends JpaRepository <PuuidMatch,Integer> {

    PuuidMatch findByPuuidAndMatchId(String puuid, String matchId);
}
