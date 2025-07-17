package com.example.demo.Service.Impl;

import com.example.demo.Dto.MatchIDDTO;
import com.example.demo.Entity.MatchID;
import com.example.demo.Repo.IMatchIDRepo;
import com.example.demo.Service.IMatchIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MatchIDIMPL implements IMatchIDService {

    @Autowired
    private IMatchIDRepo iMatchIDRepo;
    @Override
    public String addMatchID(MatchIDDTO matchIDDTO) {
        MatchID matchID = new MatchID(
            matchIDDTO.getID(),
            matchIDDTO.getMatchID(),
            matchIDDTO.getPuuid0(),
            matchIDDTO.getPuuid1(),
            matchIDDTO.getPuuid2(),
            matchIDDTO.getPuuid3(),
            matchIDDTO.getPuuid4(),
            matchIDDTO.getPuuid5(),
            matchIDDTO.getPuuid6(),
            matchIDDTO.getPuuid7(),
            matchIDDTO.getPuuid8(),
            matchIDDTO.getPuuid9()
        );
        iMatchIDRepo.save(matchID);
        return "Saved ID " + matchID.getMatchID();
    }
}
