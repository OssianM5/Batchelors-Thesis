package com.example.demo.Service.Impl;

import com.example.demo.Dto.PuuidMatchDTO;
import com.example.demo.Entity.PuuidMatch;
import com.example.demo.Repo.IPuuidMatchRepo;
import com.example.demo.RiotApi.MatchHistoryFetcher;
import com.example.demo.Service.IPuuidMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PuuidMatchIMPL implements IPuuidMatchService {

    @Autowired
    private IPuuidMatchRepo iPuuidMatchRepo;

    @Override
    public String addPuuidMatch(PuuidMatchDTO puuidMatchDTO) {

        PuuidMatch puuidMatch = new PuuidMatch(
                puuidMatchDTO.getId(),
                puuidMatchDTO.getPuuid(),
                puuidMatchDTO.getMatchId()
        );
        iPuuidMatchRepo.save(puuidMatch);
        return "Saved puuid " + puuidMatch.getPuuid();
    }
}
