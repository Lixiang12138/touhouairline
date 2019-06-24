package com.ecust.touhouairline.service;

import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 姚迟亮
 * 创建日期：2019-6-19
 **/
@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 客户或管理员登录
     * @param AccountName 账户名
     * @param Password 密码
     * @param isCustomer 如果是客户登录则为true
     *                   如果是管理员登陆则为false
     * @return 成功返回Success，失败返回"用户名或密码错误"
     */
    String Login(String AccountName,String Password,boolean isCustomer){
        UserEntity user = userRepository.findByUserNameAndPassword(AccountName,Password);
        if(user == null){
            return "用户名或密码错误";
        }
        else{
            //如果是客户登录且账户类型确实是客户则返回登录成功
            if(isCustomer && user.getCharacterAssociationsByUserNo().getCharactertableByCharacterNo().
                    getCharacterName().equals("客户")){
                return "success";
            }
            //如果管理端登录且账户类型不为客户也返回成功
            else if(!isCustomer && !user.getCharacterAssociationsByUserNo().
                    getCharactertableByCharacterNo().getCharacterName().equals("客户")){
                return "success";
            }
            //否则是客户登录管理员账户或管理员登陆客户账户，返回登录失败
            else{
                return "用户名或密码错误";
            }
        }
    }


}
