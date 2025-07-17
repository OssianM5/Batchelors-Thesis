package com.example.demo.Controller;

import com.example.demo.Dto.PuuidMatchDTO;
import com.example.demo.Service.IPuuidMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/puuidmatch")

public class PuuidMatchController {

    @Autowired
    private IPuuidMatchService iPuuidMatchService;

    @PostMapping(path = "/save")
    public String savePuuidMatch (@RequestBody PuuidMatchDTO puuidMatchDTO)
    {
        String id = iPuuidMatchService.addPuuidMatch(puuidMatchDTO);
        return id;
    }


}
