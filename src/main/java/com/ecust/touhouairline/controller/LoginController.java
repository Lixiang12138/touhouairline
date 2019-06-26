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

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ModelMap login(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        ResultWithSingleMessage result = loginService.Login(params.get("username").toString(),params.get("password").toString(),true);
        map.put("result",result);
        return map;
    }

    @PostMapping(value = "register")
    public ModelMap register(@RequestBody Map<String,Object> params){
        ModelMap map = new ModelMap();
        System.out.println(params);
        String out = JSON.toJSONString(params);
        System.out.println(out);
        UserEntity userEntity = JSON.parseObject(out,UserEntity.class);
        SingleMessageResult result = loginService.register(userEntity);
        map.put("result",result);
        return map;
    }
}
