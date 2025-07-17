package com.example.demo.Service.Impl;


import com.example.demo.Dto.MatchDataDTO;
import com.example.demo.Dto.MatchDataFetchDTO;
import com.example.demo.Entity.MatchData;
import com.example.demo.Repo.IMatchDataRepo;
import com.example.demo.Response.MatchDataFetchResponse;
import com.example.demo.Service.IMatchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchDataIMPL implements IMatchDataService {

    @Autowired
    private IMatchDataRepo iMatchDataRepo;

    @Override
    public String addMatchData(MatchDataDTO matchDataDTO) {
        MatchData matchData = new MatchData(
                matchDataDTO.getId(),
                matchDataDTO.getMatchId(),
                matchDataDTO.getPuuid(),
                matchDataDTO.getGoldEarned(),
                matchDataDTO.getDamageDealt(),
                matchDataDTO.getDamageTaken(),
                matchDataDTO.getKills(),
                matchDataDTO.getDeaths(),
                matchDataDTO.getAssists(),
                matchDataDTO.getVisionScore()
        );
        iMatchDataRepo.save(matchData);
        return "Saved ID " + matchData.getMatchId();
    }

    @Override
    public List<MatchDataFetchResponse> getMatchDataByMatchIdAndPuuid(MatchDataFetchDTO matchDataFetchDTO) {
        List<MatchData> matchDataList = iMatchDataRepo.findByMatchIdAndPuuid(
                matchDataFetchDTO.getMatchId(),
                matchDataFetchDTO.getPuuid()
        );
        return matchDataList.stream()
                .map(matchData -> new MatchDataFetchResponse(
                        matchData.getId(),
                        matchData.getMatchId(),
                        matchData.getPuuid(),
                        matchData.getGoldEarned(),
                        matchData.getDamageDealt(),
                        matchData.getDamageTaken(),
                        matchData.getKills(),
                        matchData.getDeaths(),
                        matchData.getAssists(),
                        matchData.getVisionScore()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchDataFetchResponse> getAllMatchData() {
        List<MatchData> allMatchData = iMatchDataRepo.findAll();
        return allMatchData.stream()
                .map(matchData -> new MatchDataFetchResponse(
                        matchData.getId(),
                        matchData.getMatchId(),
                        matchData.getPuuid(),
                        matchData.getGoldEarned(),
                        matchData.getDamageDealt(),
                        matchData.getDamageTaken(),
                        matchData.getKills(),
                        matchData.getDeaths(),
                        matchData.getAssists(),
                        matchData.getVisionScore()
                ))
                .collect(Collectors.toList());
    }


}
