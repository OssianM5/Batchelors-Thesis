package com.example.demo.Controller;


import com.example.demo.Dto.MatchIDDTO;
import com.example.demo.Service.IMatchIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/matchid")

public class MatchIDController {

    @Autowired
    private IMatchIDService iMatchIDService;

    @PostMapping(path = "/save")
    public String saveUser (@RequestBody MatchIDDTO matchIDDTO)
    {
        String id = iMatchIDService.addMatchID(matchIDDTO);
        return id;
    }


}
