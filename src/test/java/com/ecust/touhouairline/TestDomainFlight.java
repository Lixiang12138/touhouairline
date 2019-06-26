package com.ecust.touhouairline;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.controller.DomainFlightController;
import com.ecust.touhouairline.repository.FlightRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDomainFlight {

    @Autowired
    DomainFlightController domainFlightController;

    @Test
    public void testAddFlight(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("flight","{\"arrivedTime\":1561639721000," +
                "\"departPlace\":\"上海\"," +
                "\"departTime\":1561639721000," +
                "\"destination\":\"北京\"," +
                "\"economyPrice\":114," +
                "\"premiumPrice\":514," +
                "\"firstPrice\":1919," +
                "\"flightNo\":\"364364\"," +
                "\"planeNo\":\"1\"," +
                "\"mileage\":810}");
        System.out.println(JSON.toJSONString(domainFlightController.addFlight(params)));
    }

    @Test
    public void testChangeFlight(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("flight","{\"arrivedTime\":1561553321000," +
                "\"departPlace\":\"上海\"," +
                "\"departTime\":1561466906000," +
                "\"destination\":\"北京\"," +
                "\"economyPrice\":1000," +
                "\"premiumPrice\":2000," +
                "\"firstPrice\":3000," +
                "\"flightNo\":\"1\"," +
                "\"planeNo\":\"1\"," +
                "\"mileage\":101}");
        System.out.println(JSON.toJSONString(domainFlightController.changeFlight(params)));
    }

    @Test
    public void changeFlightState(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("flightNo","364364");
        params.put("state","值机中");
        System.out.println(JSON.toJSONString(domainFlightController.changeFlightState(params)));
    }

}
