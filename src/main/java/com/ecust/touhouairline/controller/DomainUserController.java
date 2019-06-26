package com.ecust.touhouairline.controller;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.entity.PassengerEntity;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.service.DomainUserInfoService;
import com.ecust.touhouairline.utils.MultiMessageResult;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DomainUserController {
    @Autowired
    DomainUserInfoService domainUserInfoService;

    public ModelMap changeUserInfo(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(params.get("user").toString(),UserEntity.class);
        UserEntity target = JSON.parseObject(params.get("target").toString(),UserEntity.class);
        SingleMessageResult result = domainUserInfoService.changeUserInfo(userEntity,target);
        map.put("result",result);
        return map;
    }

    public ModelMap addPassenger(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(params.get("user").toString(),UserEntity.class);
        PassengerEntity target = JSON.parseObject(params.get("target").toString(),PassengerEntity.class);
        MultiMessageResult result = domainUserInfoService.addPassenger(userEntity,target);
        map.put("result",result);
        return map;
    }

    public ModelMap changePassenger(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(params.get("user").toString(),UserEntity.class);
        PassengerEntity target = JSON.parseObject(params.get("target").toString(),PassengerEntity.class);
        MultiMessageResult result = domainUserInfoService.changePassenger(userEntity,target);
        map.put("result",result);
        return map;
    }

    public ModelMap removePassenger(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(params.get("user").toString(),UserEntity.class);
        PassengerEntity target = JSON.parseObject(params.get("target").toString(),PassengerEntity.class);
        SingleMessageResult result = domainUserInfoService.deletePassenger(userEntity,target);
        map.put("result",result);
        return map;
    }

    public ModelMap showPassengers(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(params.get("user").toString(),UserEntity.class);
        ResultWithSingleMessage result = domainUserInfoService.showPassages(userEntity);
        map.put("result",result);
        return map;
    }
}
