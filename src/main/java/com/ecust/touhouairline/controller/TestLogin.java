package com.ecust.touhouairline.controller;

import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin2.message.Serializer;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ecust.touhouairline.service.LoginService;

@RestController
public class TestLogin {
    @Autowired
    LoginService loginService;

    @RequestMapping(value="test",method = RequestMethod.GET)
    public ModelMap showCharacter(String id){
        System.out.println(id);
        ModelMap map = new ModelMap();
        if(id!=null && id.equals("12345")) {
            map.put("result", (new TestClass(10,"1919810")));
            System.out.println("24岁，是学生");
        }
        else map.put("result", (new TestClass(110,"110")));
        return map;
    }

    @PostMapping(value="home")
    public ModelMap testFabric(@RequestBody Map<String,Object> params){
        System.out.println(params);
        ModelMap map = new ModelMap();
        String str = (String) params.get("result");
        System.out.println(str);
        map.put("result",loginService.Login((String) params.get("username"),(String)params.get("password"),true));
        return map;
    }


    class TestClass implements Serializable {
        int x;
        String y;
        TestClass(int xx, String yy){x=xx;y=yy;}

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}