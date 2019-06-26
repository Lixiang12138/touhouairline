package com.ecust.touhouairline.controller;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.entity.OrderDetailEntity;
import com.ecust.touhouairline.entity.OrderMasterEntity;
import com.ecust.touhouairline.service.DomainOrderService;
import com.ecust.touhouairline.utils.Result;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DomainOrderController {
    @Autowired
    DomainOrderService domainOrderService;

    /**
     * need orderNo:String
     * @return object:Collection<orderDetail>
     */
    @PostMapping(value = "show_order_detail")
    public ModelMap showOrderDetail(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        Integer orderNo = Integer.valueOf((String)params.get("orderNo"));
        Result result = domainOrderService.showOrderDetail(orderNo);
        map.put("result",result);
        return map;
    }

    /**
     * need orderNo:String
     * @return message:message
     */
    @PostMapping(value = "pay_order")
    public ModelMap payOrder(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        Integer orderNo = Integer.valueOf((String)params.get("orderNo"));
        ResultWithSingleMessage result = domainOrderService.payOrder(orderNo);
        map.put("result",result);
        return map;
    }

    /**
     * need orderNo:String
     * @return message:message
     */
    @PostMapping(value = "cancel_order")
    public ModelMap cancelOrder(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        Integer orderNo = Integer.valueOf((String)params.get("orderNo"));
        ResultWithSingleMessage result = domainOrderService.cancelOrder(orderNo);
        map.put("result",result);
        return map;
    }

    /**
     * need orderMaster:OrderMasterEntity
     *      orderDetail:OrderDetailEntity[]
     * @return message:message
     */
    @PostMapping(value = "create_order")
    public ModelMap createOrder(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        OrderMasterEntity orderMaster = JSON.parseObject(JSON.toJSONString((params.get("orderMaster"))),OrderMasterEntity.class);
        List<OrderDetailEntity> orderDetail = JSON.parseArray(JSON.toJSONString((params.get("orderDetail"))),OrderDetailEntity.class);
        SingleMessageResult result = domainOrderService.createOrder(orderMaster,orderDetail);
        map.put("result",result);
        return map;
    }
}
