package com.ecust.touhouairline.controller;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.service.LoginService;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * need username:String
     *      password:String
     * @return object:UserEntity message:message
     */
    @PostMapping(value = "login")
    public ModelMap login(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        ResultWithSingleMessage result = loginService.Login(params.get("username").toString(),params.get("password").toString(),true);
        map.put("result",result);
        return map;
    }

    /**
     * need user:UserEntity
     * @return message:message
     */
    @PostMapping(value = "register")
    public ModelMap register(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        UserEntity userEntity = JSON.parseObject(JSON.toJSONString(params.get("user")),UserEntity.class);
        SingleMessageResult result = loginService.register(userEntity);
        map.put("result",result);
        return map;
    }
}
