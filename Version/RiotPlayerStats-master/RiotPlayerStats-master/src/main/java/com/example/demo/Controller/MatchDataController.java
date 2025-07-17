package com.example.demo.Controller;

import com.example.demo.Dto.MatchDataDTO;
import com.example.demo.Dto.MatchDataFetchDTO;
import com.example.demo.Response.MatchDataFetchResponse;
import com.example.demo.Service.IMatchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/matchdata")
public class MatchDataController {

    @Autowired
    private IMatchDataService iMatchDataService;

    @PostMapping(path = "/save")
    public ResponseEntity<String> saveMatchData(@RequestBody MatchDataDTO matchDataDTO) {
        String id = iMatchDataService.addMatchData(matchDataDTO);
        return ResponseEntity.ok(id); // Wrap in ResponseEntity for better HTTP response handling
    }

//    @GetMapping(path = "/fetch/{matchId}/{puuid}")// Changed to @GetMapping for fetching data
//    public ResponseEntity<List<MatchDataFetchResponse>> getMatchData(
//            @RequestParam String matchId,
//            @RequestParam String puuid
//    ) {
//        System.out.println("Fetching data for MatchId: " + matchId + " and Puuid: " + puuid); // Debugging log
//        // Construct DTO
//        MatchDataFetchDTO matchDataFetchDTO = new MatchDataFetchDTO(puuid, matchId);
//
//        // Fetch data using service
//        List<MatchDataFetchResponse> matchDataList = iMatchDataService.getMatchDataByMatchIdAndPuuid(matchDataFetchDTO);
//
//        // Return the list wrapped in ResponseEntity
//        return ResponseEntity.ok(matchDataList);
//    }
}
