package com.ecust.touhouairline;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.controller.DomainOrderController;
import com.ecust.touhouairline.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDomainOrder {
    @Autowired
    DomainOrderController domainOrderController;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testPayOrder(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("orderNo","1");
        System.out.println(JSON.toJSONString(domainOrderController.payOrder(params)));
    }

    @Test
    public void testCancelOrder(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("orderNo","1");
        System.out.println(JSON.toJSONString(domainOrderController.cancelOrder(params)));
    }

    @Test
    public void testCreateOrder(){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("orderMaster","{" +
                "\"userByUserNo\":" + JSON.toJSONString(userRepository.getOne("test")) + ","+
                "\"ticketClass\":\"商务舱\","+
                "\"usedCredit\":\"0\"," +
                "\"flightByFlightNo\":{" +
                "\"flightNo\":\"364364\"," +
                "\"economyPrice\":114," +
                "\"premiumPrice\":514," +
                "\"firstPrice\":1919}," +
                "\"sum\":771" +
                "}");
        params.put("orderDetail","[" +
                "{\"passengerName\":\"田所浩二\","+
                "\"passengerType\":\"成人\","+
                "\"passport\":\"REDTEA114514\"," +
                "\"phone\":\"1145141919810\""+
                "},"+
                "{\"passengerName\":\"test\","+
                "\"passengerType\":\"儿童\","+
                "\"identity\":\"123456789012345678\"," +
                "\"phone\":\"12345678901\""+
                "}"+
                "]");
        System.out.println(JSON.toJSONString(domainOrderController.createOrder(params)));
    }

    @Test
    public void testShowOrderDetail() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("orderNo","23");
        System.out.println(JSON.toJSONString(domainOrderController.showOrderDetail(params)));
    }
}
