package com.ecust.touhouairline;

import com.alibaba.fastjson.JSON;
import com.ecust.touhouairline.entity.UserEntity;
import org.junit.Test;

public class TestApplication {
    @Test
    public void test(){
        // 将Json字符串反序列化为Java对象
        UserEntity user = JSON.parseObject("{username:\"test\",password:\"123456\"}", UserEntity.class);
        System.out.println(user);
    }

}
