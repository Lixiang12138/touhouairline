package com.ecust.touhouairline;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.controller.QueryFlightController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQueryFlight {
    @Autowired
    QueryFlightController queryFlightController;

    @Test
    public void queryTwoWayFlight(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("departTime","2019/06/25");
        params.put("returnTime","2019/06/24");
        params.put("departPlace","上海");
        params.put("destination","北京");
        params.put("isOneWay",false);
        System.out.println(JSON.toJSONString(queryFlightController.queryTwoWayTicket(params)));
    }

    @Test
    public void queryOneWayFlight(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("departTime","2019/06/25");
        params.put("returnTime","2019/06/25");
        params.put("departPlace","上海");
        params.put("destination","北京");
        params.put("isOneWay",true);
        System.out.println(JSON.toJSONString(queryFlightController.queryTwoWayTicket(params)));
    }

    @Test
    public void showDetailByFlight(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("flightNo","364364");
        System.out.println(JSON.toJSONString(queryFlightController.showDetailByFlight(params)));
    }

}
