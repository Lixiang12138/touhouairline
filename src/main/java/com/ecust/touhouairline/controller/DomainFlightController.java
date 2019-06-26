package com.ecust.touhouairline.controller;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.entity.FlightEntity;
import com.ecust.touhouairline.service.DomainFlightInfoService;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DomainFlightController {
    @Autowired
    DomainFlightInfoService domainFlightInfoService;

    /**
     * need flight:FlightEntity
     * @return message:message
     */
    @PostMapping(value = "add_flight")
    public ModelMap addFlight(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        FlightEntity flight = JSON.parseObject((String)params.get("flight"),FlightEntity.class);
        SingleMessageResult result = domainFlightInfoService.addFlight(flight);
        map.put("result",result);
        return map;
    }

    /**
     * need flight:FlightEntity
     * @return message:message
     */
    @PostMapping(value = "change_flight")
    public ModelMap changeFlight(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        FlightEntity flight = JSON.parseObject((String)params.get("flight"),FlightEntity.class);
        SingleMessageResult result = domainFlightInfoService.changeFlight(flight);
        map.put("result",result);
        return map;
    }

    /**
     * need flightNo:String
     * @return message:message
     */
    @PostMapping(value = "delete_flight")
    public ModelMap deleteFlight(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        String flightNo = (String)params.get("flightNo");
        SingleMessageResult result = domainFlightInfoService.deleteFlight(flightNo);
        map.put("result",result);
        return map;
    }

    /**
     * need flightNo:String
     *      state:String
     * @return message:message
     */
    @PostMapping(value = "change_flight_state")
    public ModelMap changeFlightState(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        String flightNo = (String)params.get("flightNo");
        SingleMessageResult result = domainFlightInfoService.changeFlightState(flightNo,(String)params.get("state"));
        map.put("result",result);
        return map;
    }
}
