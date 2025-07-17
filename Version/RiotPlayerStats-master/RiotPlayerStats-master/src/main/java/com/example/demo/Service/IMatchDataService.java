package com.example.demo.Service;

import com.example.demo.Dto.MatchDataDTO;
import com.example.demo.Dto.MatchDataFetchDTO;
import com.example.demo.Response.MatchDataFetchResponse;

import java.util.List;

public interface IMatchDataService {
    String addMatchData(MatchDataDTO matchDataDTO);


    List<MatchDataFetchResponse> getMatchDataByMatchIdAndPuuid(MatchDataFetchDTO matchDataFetchDTO);

    List<MatchDataFetchResponse> getAllMatchData();
}
