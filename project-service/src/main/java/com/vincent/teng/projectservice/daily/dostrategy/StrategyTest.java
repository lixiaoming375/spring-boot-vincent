package com.vincent.teng.projectservice.daily.dostrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1419:18
 */
@RestController
public class StrategyTest {
    @Autowired
    private  List<IExtract>  extractList;

    @GetMapping("/doStrategyTest")
    public void doStrategyTest(@RequestParam("channelNo") String channelNo){
        Optional<IExtract>  optionalIExtract=extractList.stream().filter(extract -> extract.support(channelNo)).findFirst();
        if(optionalIExtract.isPresent()){
            optionalIExtract.get().extract(channelNo);
        }
    }


}
